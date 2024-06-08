package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {
  
  private static Connection conn = null;
  
  public static Connection getConnection(){
    try {
      if (conn == null) {
        Properties props = loadProperties();
        String url = props.getProperty("dburl");
        conn = DriverManager.getConnection(url, props);
      }
      return conn;
    }
    catch(SQLException e ){
      throw new DbException(e.getMessage());
    }
  }
  public static void closeConnection(){
    if(conn!=null){
      try {
        conn.close();
      }
      catch(SQLException e ){
        throw new DbException(e.getMessage());
      }
    }
  }
  
  // método para carregar as propriedades do db.properties
  private static Properties loadProperties(){
    try(FileInputStream fs = new FileInputStream("src/db.properties")){
      Properties props = new Properties();
      props.load(fs);
      return props;
    }
    catch(IOException e){
      throw new DbException(e.getMessage());
    }
  }
  public static void closeStatement(Statement st){
    if (st!=null){
      try {
        st.close();
      } catch (SQLException e) {
        throw new DbException(e.getMessage());
      }
    }
  }
  public static void closeResultSet(ResultSet rs){
    if (rs!=null){
      try {
        rs.close();
      } catch (SQLException e) {
        throw new DbException(e.getMessage());
      }
    }
  }
  
}
