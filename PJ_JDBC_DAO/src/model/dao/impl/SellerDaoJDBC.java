package model.dao.impl;

import db.DB;
import db.DbException;
import model.entities.Departments;
import model.entities.Seller;
import model.dao.SellerDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {
  
  // criando uma injeção de dependence da conexão como o banco de dados
  private Connection conn;
  
  // Criando um construtor para forçar a injeção de dependência do banco
  public SellerDaoJDBC(Connection conn){
    this.conn = conn;
  }
  
  @Override
  public void insert(Seller obj) {
  }
  
  @Override
  public void update(Seller obj) {
  }
  
  @Override
  public void deleteById(Integer id) {
  }
  
  @Override
  public Seller findById(Integer id) {
    PreparedStatement st = null;
    ResultSet rs = null;
    try{
      st = conn.prepareStatement(
          "SELECT seller.*, department.Name as DepName " +
              "FROM seller INNER JOIN department " +
              "ON seller.DepartmentId = department.Id " +
              "WHERE seller.Id = ?"
      );
      st.setInt(1,id);
      rs = st.executeQuery(); // gera um resultado e guarda na variável ResultSet rs que trará o resultado
      if(rs.next()){
        Departments dep = instantiateDepartment(rs);
        Seller obj = instatiateSeller(rs, dep);
        return obj;
      }
      return null;
    }
    catch (SQLException e){
      throw new DbException(e.getMessage());
    }
    finally {
      DB.closeStatement(st);
      DB.closeResultSet(rs);
    }
    
  }
  
  private Departments instantiateDepartment(ResultSet rs) throws SQLException{
    Departments dep = new Departments();
    dep.setId(rs.getInt("DepartmentId"));
    dep.setName(rs.getString("DepName"));
    return dep;
  }
  
  private Seller instatiateSeller(ResultSet rs, Departments dep)  throws SQLException{
    Seller obj = new Seller();
    obj.setId(rs.getInt("Id"));
    obj.setName(rs.getString("Name"));
    obj.setEmail(rs.getString("Email"));
    obj.setSalary(rs.getDouble("BaseSalary"));
    obj.setBirthDate(rs.getDate("BirthDate"));
    obj.setDepartment(dep);
    return obj;
  }
  
  @Override
  public List<Seller> findAll() {
    return List.of();
  }
  
  @Override
  public List<Seller> findByDepartment(Departments department) {
    PreparedStatement st = null;
    ResultSet rs = null;
    try{
      st = conn.prepareStatement(
          "SELECT seller.*,department.Name as DepName " +
              "FROM seller INNER JOIN department " +
              "ON seller.DepartmentId = department.Id " +
              "WHERE DepartmentId = ? " +
              "ORDER BY Name"
      );
      st.setInt(1,department.getId());
      rs = st.executeQuery(); // gera um resultado e guarda na variável ResultSet rs que trará o resultado
      
      List<Seller> list = new ArrayList<>();
      Map<Integer,Departments> map = new HashMap<>();
      
      while(rs.next()){
        Departments dep = map.get(rs.getInt("DepartmentId"));
        if(dep == null) {
          dep = instantiateDepartment(rs);
          map.put(rs.getInt("DepartmentId"), dep);
        }
        Seller obj = instatiateSeller(rs, dep);
        list.add(obj);
      }
      return list;
    }
    catch (SQLException e){
      throw new DbException(e.getMessage());
    }
    finally {
      DB.closeStatement(st);
      DB.closeResultSet(rs);
    }
  }
}
