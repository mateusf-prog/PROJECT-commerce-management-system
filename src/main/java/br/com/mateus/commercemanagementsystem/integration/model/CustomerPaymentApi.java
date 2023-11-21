package br.com.mateus.commercemanagementsystem.integration.model;

import lombok.Data;

@Data
public class CustomerPaymentApi {

    private String id;
    private String name;
    private String cpfCnpj;
    private String email;
    private String mobilePhone;
}
