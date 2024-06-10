package model.dao;

import model.entities.Departments;

import java.util.List;

public interface DepartmentDao {
  
  void insert(Departments obj);
  void update(Departments obj);
  void deleteById(Integer id);
  Departments findById(Integer id);
  List<Departments> findAll();
}
