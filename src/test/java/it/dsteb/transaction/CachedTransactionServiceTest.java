package it.dsteb.transaction;

import static org.junit.Assert.*;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;


import org.junit.Test;

public class CachedTransactionServiceTest {

  @Test
  public void testInsert() {
    TransactionService service = new CachedTransactionService();
    String expectedType = "cars";
    service.createTransaction(10, 5000, expectedType, null);
    Transaction created = service.getTransaction(10);
    assertEquals("Types should be the same", expectedType, created.getType());
  }

  @Test
  public void testType() {
    TransactionService service = new CachedTransactionService();
    String type = "cars";
    service.createTransaction(10, 5000, type, null);
    service.createTransaction(11, 70000, type, null);
    service.createTransaction(12, 10000, "shopping", null);
    Collection<Transaction> created = service.getByType(type);
    List<Long> ids = created.stream().map(Transaction::getId).collect(toList());
    assertTrue("Contains id 10", ids.contains(10l));
    assertTrue("Contains id 11", ids.contains(11l));
    assertEquals("Contains 2 elements", 2, ids.size());
  }

  @Test
  public void testSum() {
    TransactionService service = new CachedTransactionService();
    String type = "cars";
    service.createTransaction(10, 5000, type, null);
    service.createTransaction(11, 7000, type, 10l);
    service.createTransaction(12, 10000, "shopping", 11l);
    double sum = service.getSum(12);
    assertEquals("Sum is 10k", 10000.0, sum, 0.0001);
    sum = service.getSum(11);
    assertEquals("Sum is 17k", 17000.0, sum, 0.0001);
    sum = service.getSum(10);
    assertEquals("Sum is 22k", 22000.0, sum, 0.0001);
  }

  @Test
  public void testStackOverflow() {
    TransactionService service = new CachedTransactionService();
    String type = "cars";
    int expected = 10000;
    service.createTransaction(1, 1, type, null);
    for (int i = 2; i <= expected; ++i) {
      service.createTransaction(i, 1, type, Long.valueOf(i - 1));
    }
    double sum = service.getSum(1);
    assertEquals("The some should be 10000", expected, sum, 0.001);
  }
}