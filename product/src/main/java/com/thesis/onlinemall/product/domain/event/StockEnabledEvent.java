package com.thesis.onlinemall.product.domain.event;

import com.thesis.onlinemall.product.domain.entity.ProductStock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StockEnabledEvent {
  private ProductStock stock;
}
