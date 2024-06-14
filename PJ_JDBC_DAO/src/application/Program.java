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
    
    System.out.print("Teste 1 Informe o id para busca ");
    
    SellerDao sellerDao = DaoFactory.createSellerDao();
    
    Seller seller = sellerDao.findById(2);
    
    System.out.println("Resultado: ");
    System.out.println(seller);
    
    System.out.println("Teste 2 Id para busca por Department: ");
    Departments dep = new Departments(2, null);
    
    List<Seller> list = sellerDao.findByDepartment(dep);
    
    for (Seller obj : list) {
      if (list.isEmpty()) {
        System.out.println("Lista está vazia");
      }
      System.out.println(obj);
    }
    
    System.out.println(" Teste 3 findAll: ");
    
    list = sellerDao.findAll();
    for (Seller obj : list) {
      if (list.isEmpty()) {
        System.out.println("Lista está vazia");
      }
      System.out.println(obj);
    }
    
    System.out.println(" Teste 4 Insert ");
    Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, dep);
    sellerDao.insert(newSeller);
    System.out.println("Inserted!, new Id = " + newSeller.getId());
    
    System.out.println(" Teste 5 Update ");
    //System.out.print("Informe o Id a ser Alterado: ");
    Seller seller1 = sellerDao.findById(1);
    seller1.setName("Martha Waine");
    sellerDao.update(seller1);
    System.out.println("Update Confirmado");
    
    System.out.println(" Teste 6 Delete ");
    
    System.out.print("Informe Id do funcionaria a ser deletado: ");
    int idDelete = sc.nextInt();
    sellerDao.deleteById(idDelete);
    System.out.println("Delete completado");
    
  }
}

