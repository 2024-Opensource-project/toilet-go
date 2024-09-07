package com.myspring.springmaster.service;

import java.math.BigDecimal;

public interface MapApiService {
    public BigDecimal[] getLatAndLng(String address);
}
