package com.mm.ecommerce.exception;

import java.io.Serial;

public class RecordAlreadyExistsException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 3946919557745116819L;

  public RecordAlreadyExistsException(Exception e) {
    super(e);
  }

  public RecordAlreadyExistsException(final String message) {
    super(message);
  }
}
