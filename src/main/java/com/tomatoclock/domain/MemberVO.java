package com.tomatoclock.domain;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MemberVO {

    private String id;
    private String password;
    private String email;
    private Date regdate;
    private Date updatedate;
    private boolean enabled;

    private List<AuthVO> authList;
}
