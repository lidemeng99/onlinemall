package com.thesis.onlinemall.sales.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDeductAppliedEvent {
  private String productid;
  private int deduct;
}
