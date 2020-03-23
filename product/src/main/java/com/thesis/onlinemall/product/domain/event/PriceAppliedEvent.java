package com.thesis.onlinemall.product.domain.event;

import com.thesis.onlinemall.product.domain.entity.ProductPrice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PriceAppliedEvent {

  private EventType.STOCK type;
  private ProductPrice price;
}
