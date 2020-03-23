package com.thesis.onlinemall.product.domain.event;

import com.thesis.onlinemall.product.domain.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductLifecycleEvent {
  private ProductEntity product;

}
