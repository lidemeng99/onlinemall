package com.thesis.onlinemall.product.domain.event;

import com.thesis.onlinemall.product.domain.entity.ProductEntity;
import com.thesis.onlinemall.product.domain.entity.ProductStock;
import com.thesis.onlinemall.product.domain.entity.StatusConstants.STATUS_PRODUCT;
import com.thesis.onlinemall.product.infrastructure.entity.TProductStock;
import com.thesis.onlinemall.product.infrastructure.repository.ProductRepository;
import com.thesis.onlinemall.product.infrastructure.repository.StockRepository;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductLifecycleEventHandler {

  private ProductRepository productRepository;
  @Autowired
  private StockRepository stockRepository;

  @Autowired
  public ProductLifecycleEventHandler(
      ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @EventHandler
  public void on(ProductLifecycleEvent event) throws Exception {
    ProductEntity product = event.getProduct();
    productRepository
        .updateStatus(product.getProductId(), product.getModifier(), product.getLastModifiedTime(),
            product.getStatus().ordinal());
    if (product.getStatus() == STATUS_PRODUCT.STOCKING
        || product.getStatus() == STATUS_PRODUCT.STOCKED) {
      productRepository.updateStock(product.getProductId(), product.getStock(), product.getUnit());
    } else if (product.getStatus() == STATUS_PRODUCT.PRICING
        || product.getStatus() == STATUS_PRODUCT.PRICED) {
      productRepository.updatePrice(product.getProductId(), product.getPrice());
    }
  }

  @EventHandler
  public void on(StockEnabledEvent event) {
    System.err.println(event);
    TProductStock tProductStock = new TProductStock();
    ProductStock stock = event.getStock();
    BeanUtils.copyProperties(stock, tProductStock);
    tProductStock.setId(UUID.randomUUID().toString());
    tProductStock.setProductid(stock.getProductId());
    stockRepository.save(tProductStock);
  }

  @EventHandler
  public void on(StockDeductEvent event) {
    TProductStock stock = stockRepository.findStockOfProduct(event.getProductId());
    stockRepository.deductStock(event.getProductId(), event.getDeduct() + stock.getActual());
  }
}
