package com.tomatoclock.domain;

import lombok.Data;

@Data
public class UserSettingVO {

    private long id;
    private String userId;
    private int duration;
    private String txtColor;
    private String shadowColor;
    private String bgColor;
    private String bgImgUuid;
    private String bgImgName;
    private String bgImgUrl;

}
