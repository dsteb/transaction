package it.dsteb.transaction;

import java.util.ArrayList;
import java.util.Collection;

public class Transaction {

  private long id;
  private double amount;
  private String type;
  private Long parentId;
  private Collection<Transaction> children = new ArrayList<>();

  public Transaction(long id, double amount, String type, Long parentId) {
    this.id = id;
    this.amount = amount;
    this.type = type;
    this.parentId = parentId;
  }

  public long getId() {
    return id;
  }
  public void setId(long id) {
    this.id = id;
  }
  public double getAmount() {
    return amount;
  }
  public void setAmount(double amount) {
    this.amount = amount;
  }
  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }
  public Long getParentId() {
    return parentId;
  }
  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  public Collection<Transaction> getChildren() {
    return children;
  }
}
