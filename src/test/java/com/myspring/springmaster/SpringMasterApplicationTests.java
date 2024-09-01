package com.myspring.springmaster;

import com.myspring.springmaster.dataAccess.repository.HouseRepository;
import com.myspring.springmaster.service.HouseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

@SpringBootTest
class SpringMasterApplicationTests {

    private final HouseService houseService;

    @Autowired
    SpringMasterApplicationTests(HouseService houseService) {
        this.houseService = houseService;
    }

    @Test
    public void sendRequest() {
        BigDecimal bigDecimal = new BigDecimal(123);
        BigDecimal bigDecimal2 = new BigDecimal(124);
        BigDecimal[] arr = {bigDecimal, bigDecimal2};
        System.out.println(Arrays.toString(Arrays.stream(arr).mapToDouble(BigDecimal::doubleValue).toArray()));
    }

}
