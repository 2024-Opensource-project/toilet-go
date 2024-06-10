package com.myspring.springmaster.dataAccess.DTO;

public class CouponDTO {
    private int idx;
    private String ownerId;
    private String name;
    private String description;
    private int[] NotUsableCategoryId;
    private int[] usableProductId;
    private boolean percentageDiscount;
    private int amountOfDiscount;
    private int leastProductPrice;
}
