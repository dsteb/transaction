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

  private static final String NOT_FOUND_MSG = "Not found transaction with ID=";
  private static final String DUPLICATE_MSG = "Duplicate transaction id=";

  private Map<Long, Transaction> transactions = new HashMap<>();
  private Map<String, Collection<Transaction>> transactionsByType = new HashMap<>();

  // TODO: #dsteb Implement synchronization

  @Override
  public void createTransaction(long id, Transaction newOne) {
    newOne.setId(id);
    if (newOne.getParentId() != null) {
      Transaction parent = transactions.get(newOne.getParentId());
      if (parent == null) {
        throw new TransactionNotFoundException(NOT_FOUND_MSG + newOne.getParentId());
      }
      parent.getChildren().add(newOne);
    }
    Transaction oldOne = transactions.get(id);
    if (oldOne != null) {
      throw new DuplicatedTransactionException(DUPLICATE_MSG + id);
    }
    transactions.put(id, newOne);
    Collection<Transaction> transactionsByType = this.transactionsByType.get(newOne.getType());
    if (transactionsByType == null) {
      transactionsByType = new ArrayList<>();
      this.transactionsByType.put(newOne.getType(), transactionsByType);
    }
    transactionsByType.add(newOne);
  }

  @Override
  public Transaction getTransaction(long id) {
    Transaction transaction = transactions.get(id);
    if (transaction == null) {
      throw new TransactionNotFoundException(NOT_FOUND_MSG + id);
    }
    return transaction;
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
    if (root == null) {
      throw new TransactionNotFoundException(NOT_FOUND_MSG + parentId);
    }
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
