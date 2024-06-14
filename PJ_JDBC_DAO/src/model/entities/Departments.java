package model.entities;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Departments implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  
  private Integer id;
  private String name;
  
  public Departments() {
  }
  
  public Departments(Integer id, String name) {
    this.id = id;
    this.name = name;
  }
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Departments that = (Departments) o;
    return Objects.equals(id, that.id);
  }
  
  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
  
  @Override
  public String toString() {
    return id + " - " + name;
  }
  
  
}
