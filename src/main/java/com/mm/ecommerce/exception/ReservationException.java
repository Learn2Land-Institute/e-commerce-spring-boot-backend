package com.mm.ecommerce.exception;

import java.io.Serial;

public class ReservationException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = -6399875486485173105L;

    public ReservationException(Exception e) {
        super(e);
    }

    public ReservationException(String message) {
        super(message);
    }
}
