package br.com.mateus.commercemanagementsystem.integration.model;

import lombok.Data;

@Data
public class Customer {

    private String id;
    private String name;
    private String cpfCnpj;
    private String email;
    private String mobilePhone;
}
