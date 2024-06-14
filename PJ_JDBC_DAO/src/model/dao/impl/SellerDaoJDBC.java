package model.dao.impl;

import db.DB;
import db.DbException;
import model.entities.Departments;
import model.entities.Seller;
import model.dao.SellerDao;

import java.sql.*;
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
    PreparedStatement st = null;
    try {
      st = conn.prepareStatement(
          "INSERT INTO seller " +
              "(Name, Email, BirthDate, BaseSalary, DepartmentId) " +
              "VALUES (?, ?, ?, ?, ?)",  // Correção na posição do VALUES
          Statement.RETURN_GENERATED_KEYS
      );
      st.setString(1, obj.getName());
      st.setString(2, obj.getEmail());
      st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
      st.setDouble(4, obj.getSalary());
      st.setInt(5, obj.getDepartment().getId());
      
      int rowsAffected = st.executeUpdate();
      if (rowsAffected > 0) {
        ResultSet rs = st.getGeneratedKeys();
        if (rs.next()) {
          int id = rs.getInt(1);
          obj.setId(id);
        }
        DB.closeResultSet(rs);
      } else {
        throw new DbException("Erro inesperado, nenhuma linha afetada");
      }
      
    }
    catch(SQLException e){
      throw new DbException(e.getMessage());
    }
    finally {
      DB.closeStatement(st);
    }
  }
  
  @Override
  public void update(Seller obj) {
    PreparedStatement ps = null;
    try{
      ps = conn.prepareStatement(
          "UPDATE seller "+
               "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ? , DepartmentId = ? "+
               "WHERE Id = ?");
      ps.setString(1,obj.getName());
      ps.setString(2,obj.getEmail());
      ps.setDate(3, new Date(obj.getBirthDate().getTime()));
      ps.setDouble(4,obj.getSalary());
      ps.setInt(5,obj.getDepartment().getId());
      ps.setInt(6,obj.getId());
      
      ps.executeUpdate();
    }
    catch(SQLException e){
      throw new DbException(e.getMessage());
    }
    finally {
      DB.closeStatement(ps);
    }
  }
  
  @Override
  public void deleteById(Integer id) {
    PreparedStatement st = null;
    try{
      st = conn.prepareStatement("DELETE FROM seller WHERE id = ?");
      st.setInt(1,id);
      st.executeUpdate();
    }
    catch(SQLException e){
      throw new DbException(e.getMessage());
    }
    finally {
      DB.closeStatement(st);
    }
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
    PreparedStatement st = null;
    ResultSet rs = null;
    try{
      st = conn.prepareStatement(
          "SELECT seller.*,department.Name as DepName " +
              "FROM seller INNER JOIN department " +
              "ON seller.DepartmentId = department.Id " +
              "ORDER BY Name"
      );
      rs = st.executeQuery();
      
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
