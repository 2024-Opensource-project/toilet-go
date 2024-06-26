package com.myspring.springmaster;

import com.myspring.springmaster.dataAccess.repository.HouseRepository;
import com.myspring.springmaster.service.HouseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class SpringMasterApplicationTests {

    private final HouseService houseService;

    @Autowired
    SpringMasterApplicationTests(HouseService houseService) {
        this.houseService = houseService;
    }

    @Test
    void contextLoads() {
        System.out.println("Test Start\n\n\n");


        System.out.println("Test End\n\n\n");
    }

}
