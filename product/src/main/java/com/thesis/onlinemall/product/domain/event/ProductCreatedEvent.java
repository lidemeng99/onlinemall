package com.thesis.onlinemall.product.domain.event;

import com.thesis.onlinemall.product.domain.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreatedEvent {
  private String productId;
  private ProductEntity productEntity;
}
