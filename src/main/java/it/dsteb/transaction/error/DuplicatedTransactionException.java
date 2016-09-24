package it.dsteb.transaction.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value=HttpStatus.CONFLICT)
public class DuplicatedTransactionException extends RuntimeException {

  public DuplicatedTransactionException(String msg) {
    super(msg);
  }
}
