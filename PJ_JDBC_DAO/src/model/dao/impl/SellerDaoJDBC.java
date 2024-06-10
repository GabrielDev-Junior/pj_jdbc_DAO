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
import java.util.List;

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
        Departments dep = new Departments();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        Seller obj = new Seller();
        obj.setId(rs.getInt("Id"));
        obj.setName(rs.getString("Name"));
        obj.setEmail(rs.getString("Email"));
        obj.setSalary(rs.getDouble("BaseSalary"));
        obj.setBirthDate(rs.getDate("BirthDate"));
        obj.setDepartment(dep);
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
  
  @Override
  public List<Seller> findAll() {
    return List.of();
  }
}
