package application;

import model.entities.Departments;
import model.entities.Seller;
import model.dao.DaoFactory;
import model.dao.SellerDao;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Program {
  public static void main(String[] args) {
    Locale.setDefault(Locale.US);
    Scanner sc = new Scanner(System.in);
    
    System.out.print("Teste 1 Informe o id para busca: ");
    int id = sc.nextInt();
    
    SellerDao sellerDao = DaoFactory.createSellerDao();
    
    Seller seller = sellerDao.findById(id);
    
    System.out.println("Resultado: ");
    System.out.println(seller);
    
    System.out.println("Teste 2 Id para busca por Department: ");
    Departments dep = new Departments(2,null);
    
    List<Seller> list = sellerDao.findByDepartment(dep);
    
    for(Seller obj : list){
      if (list.isEmpty()){
        System.out.println("Lista está vazia");
      }
      System.out.println(obj);
    }
    
    System.out.println(" Teste 3 findAll: ");
    
    list = sellerDao.findAll();
    for(Seller obj : list){
      if (list.isEmpty()){
        System.out.println("Lista está vazia");
      }
      System.out.println(obj);
    }
  }
}

