package application;

import db.DB;
import db.DbException;
import db.DbIntegrityException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Program {
  public static void main(String[] args) {
    
    Connection conn = null;
    Statement st = null;
    
    try {
      conn = DB.getConnection();
      st = conn.createStatement();
      
      // deixando a confirmação pendente
      conn.setAutoCommit(false);
      
      int rows1 = st.executeUpdate("UPDATE seller SET BaseSalary = 2090.00 WHERE DepartmentId = 1");
//      if (1 < 2) {
//        throw new SQLException("Fake error");
//      }
      int rows2 = st.executeUpdate("UPDATE seller SET BaseSalary = 3090 WHERE DepartmentId = 2");
      
      // confirmando a transação
      conn.commit();
      
      System.out.println("Rows1 = " + rows1);
      System.out.println("Rows2 = " + rows2);
      
    } catch (SQLException e) {
      try {
        conn.rollback();
        throw new DbException("Transação não concluída, causa : " + e.getMessage());
      } catch (SQLException ex) {
        throw new DbException("Error ao tentar voltar da transação, causa : "+ ex.getMessage());
      }
      
    } finally {
      DB.closeStatement(st);
      DB.closeConnection();
    }
    
    
  }
}

