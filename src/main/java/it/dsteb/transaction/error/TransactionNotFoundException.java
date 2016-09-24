package it.dsteb.transaction.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class TransactionNotFoundException extends RuntimeException {

  public TransactionNotFoundException(String msg) {
    super(msg);
  }
}
