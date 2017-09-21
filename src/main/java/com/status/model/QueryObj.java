package com.status.model;

import lombok.Data;

/**
 * Created by SevenFin on 2015/9/22.
 */

@Data
public class QueryObj implements java.io.Serializable{


    private static final long serialVersionUID = -9052025544125572539L;
    private String vendor;
    private Integer type;
    private String condition = "";
    private String beginYear;
    private String endYear;
    private String stateFrom;
    private String stateTo;
}
