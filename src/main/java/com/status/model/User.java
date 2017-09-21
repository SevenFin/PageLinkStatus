package com.status.model;

import lombok.Data;

@Data
public class User implements java.io.Serializable{

    private static final long serialVersionUID = -6960495932703520934L;

    private int userid;
    private String username;
    private String password;
    private int roleid;


}