package model.dao;

import entities.Departments;
import entities.Seller;

import java.util.List;

public interface SellerDao {
  
  void insertSeller(Seller obj);
  void updateSeller(Seller obj);
  void deleteById(Integer id);
  Seller findById(Integer id);
  List<Seller> findAll();
  
}