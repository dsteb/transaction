package it.dsteb.transaction;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CachedTransactionService implements TransactionService {

  private Map<Long, Transaction> transactions = new HashMap<>();

  @Override
  public void createTransaction(long id, double amount, String type, Long parentId) {
    Transaction newOne = new Transaction(id, amount, type, parentId);
    transactions.put(id, newOne);
  }

  @Override
  public Transaction getTransaction(long id) {
    return transactions.get(id);
  }

  @Override
  public Collection<Transaction> getByType(String type) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public double getSum(long parentId) {
    // TODO Auto-generated method stub
    return 0;
  }
}
