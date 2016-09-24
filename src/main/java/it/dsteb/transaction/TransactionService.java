package it.dsteb.transaction;

import java.util.Collection;

public interface TransactionService {
  void createTransaction(long id, Transaction newOne);
  Transaction getTransaction(long id);
  Collection<Transaction> getByType(String type);
  double getSum(long parentId);
}