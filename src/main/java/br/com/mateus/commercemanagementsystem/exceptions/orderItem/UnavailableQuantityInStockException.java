package br.com.mateus.commercemanagementsystem.exceptions.orderItem;

import org.apache.el.util.ReflectionUtil;

public class UnavailableQuantityInStockException extends RuntimeException {

    private String message;

    public UnavailableQuantityInStockException(String message) {
        super(message);
    }
}
