package com.tomatoclock.domain;

import lombok.Data;

import java.util.Date;

@Data
public class TimerSessionVO {

    private long id;
    private String userid;
    private Date starttime;
    private Date endtime;
    private int duration;

}
