package com.lucas.googleSheets.challenge.model;

import lombok.Data;

import java.util.List;

@Data
public class Consumer {

    private String id;

    private Integer yearBirth;

    private String education;

    private String maritalStatus;

    private Integer kidHome;

    private Integer teenHome;

    public Consumer(String id, Integer yearBirth, String education, String maritalStatus, Integer kidHome, Integer teenHome) {
        this.id = id;
        this.yearBirth = yearBirth;
        this.education = education;
        this.maritalStatus = maritalStatus;
        this.kidHome = kidHome;
        this.teenHome = teenHome;
    }

    //Convert
    public Consumer(List<Object> c) {
        this.id = (String) c.get(0);
        this.yearBirth = convertInteger(c.get(1));
        this.education = (String) c.get(2);
        this.maritalStatus = (String) c.get(3);
        this.kidHome = convertInteger(c.get(5));
        this.teenHome = convertInteger(c.get(6));
    }

    private Integer convertInteger(Object c){
        try {
            return Integer.parseInt((String) c);
        }catch (NumberFormatException e){
            return null;
        }
    }
}
