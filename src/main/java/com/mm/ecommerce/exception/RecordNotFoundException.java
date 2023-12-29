package com.mm.ecommerce.exception;

import java.io.Serial;

public class RecordNotFoundException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -6399875486485173105L;

  public RecordNotFoundException(Exception e) {
    super(e);
  }

  public RecordNotFoundException(String message) {
    super(message);
  }
}
