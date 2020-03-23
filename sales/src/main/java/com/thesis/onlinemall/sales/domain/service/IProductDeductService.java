package com.thesis.onlinemall.sales.domain.service;

import org.springframework.stereotype.Component;

@Component
public interface IProductDeductService {
  void deduct(String productid, int quantity);
}
