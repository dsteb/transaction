package it.dsteb.transaction;

import java.util.Collection;

public interface TransactionService {
  void createTransaction(long id, double amount, String type, Long parentId);
  Transaction getTransaction(long id);
  Collection<Transaction> getByType(String type);
  double getSum(long parentId);
}