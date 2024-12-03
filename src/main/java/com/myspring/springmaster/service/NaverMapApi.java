package com.myspring.springmaster.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

@Component
public class NaverMapApi implements MapApiService {

    private final Environment environment;

    @Autowired
    public NaverMapApi(Environment environment) {
        this.environment = environment;
    }

    @PostConstruct
    private void init(){

    }

    private void end(){

    }

    @Override
    public BigDecimal[] getLatAndLng(String address){
        String baseUrl = environment.getProperty("python.url");
        assert baseUrl != null;
        WebClient webClient = WebClient.builder().baseUrl(baseUrl).build();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("address", address);

        String result = webClient.post().body(BodyInserters.fromFormData(params)).retrieve().bodyToMono(String.class).block();
        if(result == null || result.isEmpty() || result.equals("Not Found")){
            return null;
        }
        String[] results = result.split(",");

        BigDecimal[] rtnValue = new BigDecimal[2];
        rtnValue[0] = BigDecimal.valueOf(Double.parseDouble(results[0]));
        rtnValue[1] = BigDecimal.valueOf(Double.parseDouble(results[1]));
        return rtnValue;
    }
}
