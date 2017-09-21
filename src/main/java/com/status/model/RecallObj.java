package com.status.model;

import lombok.Data;

/**
 * Created by SevenFin on 2015/10/8.
 */
@Data
public class RecallObj implements java.io.Serializable {

    private static final long serialVersionUID = -8937394868687867865L;
    private Double timeSpan;
    private Integer recallInteger;

    public RecallObj(){
        recallInteger = 0;
    }
}
