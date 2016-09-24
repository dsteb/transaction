package it.dsteb.transaction.service;

import java.util.Collection;

import it.dsteb.transaction.model.Transaction;

public interface TransactionService {
  void createTransaction(long id, Transaction newOne);
  Transaction getTransaction(long id);
  Collection<Transaction> getByType(String type);
  double getSum(long parentId);
}