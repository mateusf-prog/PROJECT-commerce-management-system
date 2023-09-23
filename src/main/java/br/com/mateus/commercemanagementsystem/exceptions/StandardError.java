package br.com.mateus.commercemanagementsystem.exceptions;

import lombok.Data;

import java.io.Serializable;

@Data
public class StandardError implements Serializable {

    private String message;
    private int status;

    public StandardError() {
    }

    public StandardError(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
