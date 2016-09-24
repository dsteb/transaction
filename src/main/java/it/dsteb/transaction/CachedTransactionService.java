package it.dsteb.transaction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CachedTransactionService implements TransactionService {

  private Map<Long, Transaction> transactions = new HashMap<>();
  private Map<String, Collection<Transaction>> transactionsByType = new HashMap<>();

  @Override
  public void createTransaction(long id, double amount, String type, Long parentId) {
    Transaction newOne = new Transaction(id, amount, type, parentId);
    transactions.put(id, newOne);
    Collection<Transaction> transactions = transactionsByType.get(type);
    if (transactions == null) {
      transactions = new ArrayList<>();
      transactionsByType.put(type, transactions);
    }
    transactions.add(newOne);
  }

  @Override
  public Transaction getTransaction(long id) {
    return transactions.get(id);
  }

  @Override
  public Collection<Transaction> getByType(String type) {
    Collection<Transaction> transactions = transactionsByType.get(type);
    if (transactions == null) {
      transactions = new ArrayList<>();
    }
    return transactions;
  }

  @Override
  public double getSum(long parentId) {
    // TODO Auto-generated method stub
    return 0;
  }
}
