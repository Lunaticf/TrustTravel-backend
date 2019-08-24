package org.fisco.bcos.model;

import lombok.Data;

@Data
public class DigitUser {
    public DigitUser() {

    }

    private String username;
    private String name;
    private String identity;
    private String agency;
}
