package com.tomatoclock.domain;

import lombok.Data;

import java.util.Date;

@Data
public class MemberVO {

    private String id;
    private String password;
    private String email;
    private Date regdate;
    private Date updatedate;
    private char enabled;

}
