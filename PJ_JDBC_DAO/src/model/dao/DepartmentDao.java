package model.dao;

import entities.Departments;

import java.util.List;

public interface DepartmentDao {
  
  void insertDepartment(Departments obj);
  void updateDepartment(Departments obj);
  void deleteById(Integer id);
  Departments findById(Integer id);
  List<Departments> findAll();
}
