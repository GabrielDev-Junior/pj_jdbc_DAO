package application;

import entities.Departments;
import entities.Seller;

import java.util.Date;

public class Program {
  public static void main(String[] args) {
    Departments obj = new Departments(1,"Books");
    System.out.println(obj);
    Seller fun = new Seller(1,"Robert","robert@gmail.com",new Date(),3000.0,obj);
    System.out.println(fun);
  
  }
}

