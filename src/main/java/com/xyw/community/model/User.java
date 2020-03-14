package com.xyw.community.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String accountId;
    private String name;
    private String token;
    private Long gmtCreat;
    private Long gmtModified;
    private String avatarUrl;
}
