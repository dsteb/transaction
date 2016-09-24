package it.dsteb.transaction;

import static org.junit.Assert.*;

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

}