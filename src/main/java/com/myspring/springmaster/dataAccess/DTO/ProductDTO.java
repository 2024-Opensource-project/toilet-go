package com.myspring.springmaster.dataAccess.DTO;

import lombok.Data;

@Data
public class ProductDTO {
    private int idx;
    private String name;
    private String description;
    private long price;
    private int count;
    private String category;
    private String image;
    private String status;
    private String sellerId;;
}
