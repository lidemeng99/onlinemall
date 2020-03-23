package com.thesis.onlinemall.product.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDeductEvent {
  private String productId;
  private int deduct;

}
