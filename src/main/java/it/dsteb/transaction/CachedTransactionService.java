package it.dsteb.transaction;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class CachedTransactionService implements TransactionService {

  private Map<Long, Transaction> transactions = new HashMap<>();
  private Map<String, Collection<Transaction>> transactionsByType = new HashMap<>();

  // TODO: #dsteb Implement synchronization

  @Override
  public void createTransaction(long id, Transaction newOne) {
    newOne.setId(id);
    if (newOne.getParentId() != null) {
      Transaction parent = transactions.get(newOne.getParentId());
      // TODO: #dsteb Check parent not found
      parent.getChildren().add(newOne);
    }

    // TODO: #dsteb Check duplication
    transactions.put(id, newOne);
    Collection<Transaction> transactions = transactionsByType.get(newOne.getType());
    if (transactions == null) {
      transactions = new ArrayList<>();
      transactionsByType.put(newOne.getType(), transactions);
    }
    transactions.add(newOne);
  }

  @Override
  public Transaction getTransaction(long id) {
    // TODO: #dsteb Check not found
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
    Transaction root = transactions.get(parentId);
    // FIXME: #dsteb Check not found
    double sum = 0;
    Deque<Transaction> frontier = new ArrayDeque<>();
    frontier.add(root);
    while (!frontier.isEmpty()) {
      root = frontier.pop();
      sum += root.getAmount();
      frontier.addAll(root.getChildren());
    }
    return sum;
  }
}
