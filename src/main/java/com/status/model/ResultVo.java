package com.status.model;

import lombok.Data;

/**
 * Created by SevenFin on 2015/9/22.
 */

@Data
public class ResultVo implements java.io.Serializable{


    private static final long serialVersionUID = 6232062392827944804L;

    private String year;
    private String count;
    private String countState0;
    private String countState1;
    private String countState2;
    private String countState3;
    private String countState6;
    private String countState7;
    private String countState8;
    private String countState9;

    public void addCount(){
        int count = Integer.parseInt(countState0) + Integer.parseInt(countState1) +
                Integer.parseInt(countState2) + Integer.parseInt(countState3) +
                Integer.parseInt(countState6) + Integer.parseInt(countState7) +
                Integer.parseInt(countState8) + Integer.parseInt(countState9);

        this.count = count+"";
    }
}
