package com.myspring.springmaster;

import com.myspring.springmaster.service.HouseService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class SpringMasterApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("Test Start\n\n\n");
        HouseService houseService = new HouseService();
        //String[] test = houseService.getLatitudeAndLongitude("경기도 화성시 비봉면 새비봉동로 17");
        //System.out.println(Arrays.toString(test));
        System.out.println("Test End\n\n\n");
    }

}
