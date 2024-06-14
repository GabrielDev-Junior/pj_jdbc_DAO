package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Departments;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {
  
  private Connection conn;
  
  public DepartmentDaoJDBC(Connection conn) {
    this.conn = conn;
  }
  
  @Override
  public void insert(Departments obj) {
    PreparedStatement st = null;
    try{
      st = conn.prepareStatement(
          "INSERT INTO department ( Name ) VALUES ( ? )" , Statement.RETURN_GENERATED_KEYS
      );
      st.setString(1,obj.getName());
      int rowsAffected = st.executeUpdate();
      if (rowsAffected > 0){
        ResultSet rs = st.getGeneratedKeys();
        if(rs.next()){
          int id = rs.getInt(1);
          obj.setId(id);
        }
        DB.closeResultSet(rs);
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
  public void update(Departments obj) {
    PreparedStatement st = null;
    try{
      st = conn.prepareStatement(
          "UPDATE department SET Name = ? WHERE Id = ?" , Statement.RETURN_GENERATED_KEYS
      );
      st.setString(1,obj.getName());
      st.setInt(2,obj.getId());
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
  public void deleteById(Integer id) {
    PreparedStatement st = null;
    try{
      st = conn.prepareStatement(
          "DELETE FROM department WHERE Id = ?" , Statement.RETURN_GENERATED_KEYS
      );
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
  public Departments findById(Integer id) {
    PreparedStatement st = null;
    ResultSet rs = null;
    Departments dep = new Departments();
    try{
      st = conn.prepareStatement(
          "SELECT * FROM `department` WHERE Id = ?" , Statement.RETURN_GENERATED_KEYS
      );
      st.setInt(1,id);
      rs = st.executeQuery();
      if(rs.next()){
        dep.setId(rs.getInt("Id"));
        dep.setName(rs.getString("Name"));
      }
    }
    catch(SQLException e){
      throw new DbException(e.getMessage());
    }
    finally {
      DB.closeStatement(st);
    }
    return dep;
  }
  
  @Override
  public List<Departments> findAll() {
    Connection conn = null;
    Statement comando= null;
    ResultSet resultado = null;
    List<Departments> list = new ArrayList<>();
    try{
      conn = DB.getConnection();
      comando = conn.createStatement();
      resultado = comando.executeQuery("SELECT * FROM department");
      while (resultado.next()){
        Departments dep = new Departments(resultado.getInt("Id"),resultado.getString("Name"));
        list.add(dep);
      }
      System.out.println("Departamentos: \n"+ "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
      for( Departments dep : list){
        System.out.println(dep);
      }
      System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
    }
    catch (SQLException e){
      throw new DbException(e.getMessage());
    }
    finally {
      DB.closeStatement(comando);
      DB.closeResultSet(resultado);
    }
    return list;
  }
}
