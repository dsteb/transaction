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
}