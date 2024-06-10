package com.myspring.springmaster.dataAccess.DTO;

import lombok.Data;

@Data
public class UserDTO {
    private int idx;
    private String id;
    private String pw;
    private String name;
    private String email;
    private String level;
    private long money;
    private CartDTO cart;
    private int[] couponIdx;
}
