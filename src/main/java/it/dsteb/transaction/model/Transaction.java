package it.dsteb.transaction.model;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Transaction {

  private long id;
  private double amount;
  private String type;

  @JsonProperty("parent_id")
  private Long parentId;

  @JsonIgnore
  private Collection<Transaction> children = new ArrayList<>();

  public Transaction() {}

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
