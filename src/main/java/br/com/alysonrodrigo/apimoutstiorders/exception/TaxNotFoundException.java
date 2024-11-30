package br.com.alysonrodrigo.apimoutstiorders.exception;

public class TaxNotFoundException extends RuntimeException {

    public TaxNotFoundException(String message) {
        super(message);
    }

    public TaxNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
