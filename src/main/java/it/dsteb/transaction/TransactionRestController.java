package it.dsteb.transaction;

import java.util.Collection;
import static java.util.stream.Collectors.toList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactionservice/")
public class TransactionRestController {

  @Autowired
  private TransactionService service;

  @PutMapping("transaction/{transaction_id}")
  public Transaction createTransaction(
      @PathVariable("transaction_id") long id,
      @RequestBody Transaction transaction) {
    service.createTransaction(id, transaction);
    return transaction;
  }

  @GetMapping("transaction/{transaction_id}")
  public Transaction getTransaction(
      @PathVariable("transaction_id") long id) {
    return service.getTransaction(id);
  }

  @GetMapping("types/{type}")
  public Collection<Long> getTypesId(
      @PathVariable("type") String type) {
    return service.getByType(type).stream().map(Transaction::getId).collect(toList());
  }

  @GetMapping("sum/{transaction_id}")
  public double getSum(
      @PathVariable("transaction_id") long id) {
    return service.getSum(id);
  }
}
