package com.thesis.onlinemall.sales.domain.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class ProductDeductService  {

  private final RestTemplate restTemplate = new RestTemplate();
  @Autowired
  private ExternalAPIs externalAPIs;

  @Autowired
  private  ObjectMapper objectMapper;

  public void deduct(String productid, int quantity) throws Exception {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    Map<String, String> map = new HashMap<>();
    map.put("productId", productid);
    map.put("deduct", String.valueOf(quantity));
    String jsonjs = objectMapper.writeValueAsString(map);
    System.err.println(jsonjs);
    restTemplate
        .postForObject(externalAPIs.getStockDeduction(), new HttpEntity<>(jsonjs, headers), void.class,
            productid);
  }

}
