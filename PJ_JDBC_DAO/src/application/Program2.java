package application;

import db.DB;
import db.DbException;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Departments;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Program2 {
  
  public static void main(String[] args) {
    Locale.setDefault(Locale.US);
    Scanner sc = new Scanner(System.in);
    
    DepartmentDao  departmentDao = DaoFactory.createDepartmentDao();
    
    Departments department = null;
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= INSERINDO DEPARTAMENTO =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\\
    System.out.println("TESTE 1 INSERINDO UM NOVO DEPARTMENT \n");
    System.out.println("Qual o nome do department? ");
    String nome = sc.nextLine();
    department = new Departments(null,nome);
    departmentDao.insert(department);
    
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= ATUALIZANDO DEPARTAMENTO =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\\
    System.out.println("TESTE 2 ATUALIZANDO UM DEPARTMENT \n");
      departmentDao.findAll();
      System.out.println("Informe o Id do departamento que deseja atualizar: \n ID = : ");
      int id = sc.nextInt();
      sc.nextLine();
      System.out.print("Informe o novo nome do department: ");
      String newName = sc.nextLine();
      Departments dep1 = new Departments(id,null);
      dep1.setName(newName);
      departmentDao.update(dep1);
      departmentDao.findAll();
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= REMOVENDO DEPARTAMENTO =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\\
    System.out.println("TESTE 3 DELETANDO UM DEPARTMENT \n");
    System.out.println("Quantos deseja excluir?: ");
    int qtd = sc.nextInt();
    for(int i = 0; i < qtd; i++) {
      departmentDao.findAll();
      System.out.print("Informe o id do Department a ser deletado: ");
      int deleteId = sc.nextInt();
      departmentDao.deleteById(deleteId);
      System.out.println("Deletado! ");
      departmentDao.findAll();
    }
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- BUSCANDO DEPARTAMENTO POR ID =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\\
    System.out.println("TESTE 4 BUSCANDO UM DEPARTMENT POR ID  \n");
    System.out.print("Informe um id: ");
    int idBusca = sc.nextInt();
    Departments busca = departmentDao.findById(idBusca);
    System.out.println("Result = "+ busca);
    
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- BUSCANDO TODOS OS DEPARTAMENTOS =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\\
    char resp;
    do {
      System.out.println("Deseja ver todos os Departments? [ s ] ou [ n ] : ");
      resp = sc.next().toLowerCase().charAt(0);
    
      if(resp == 's'){
        departmentDao.findAll();
      }
      else if(resp == 'n'){
        System.out.println("Obrigado!!");
      }
      else{
        System.out.println("Informe [ s ] ou [ n ]");
      }
    }while (resp != 's' && resp != 'n');
    
    sc.close();
  }
  //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- FIM =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\\
}
