package com.thesis.onlinemall.product.service;

import com.thesis.onlinemall.product.domain.entity.ProductEntity;
import com.thesis.onlinemall.product.domain.entity.ProductStock;
import com.thesis.onlinemall.product.domain.entity.StatusConstants.STATUS_PRODUCT;
import com.thesis.onlinemall.product.infrastructure.entity.TProduct;
import com.thesis.onlinemall.product.infrastructure.entity.TProductStock;
import com.thesis.onlinemall.product.infrastructure.repository.ProductRepository;
import com.thesis.onlinemall.product.infrastructure.repository.StockRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class ProductQueryRestController {


  private ProductRepository productRepository;
  private StockRepository stockRepository;

  @Autowired
  public ProductQueryRestController(
      ProductRepository productRepository,
      StockRepository stockRepository) {
    this.productRepository = productRepository;
    this.stockRepository = stockRepository;
  }


  @GetMapping("/product/{id}")
  public ProductEntity findOne(@PathVariable String id) {
    Optional<TProduct> productOptional = productRepository.findById(id);
    if (!productOptional.isPresent()) {
      throw new IllegalArgumentException("Can not find<" + id + ">");
    }
    TProduct product = productOptional.get();
    ProductEntity entity = new ProductEntity();
    BeanUtils.copyProperties(product, entity);
    return entity;
  }

  @GetMapping("/stocks")
  public List<ProductEntity> searchStocks() {
    List<ProductEntity> products = searchall(9999, StringUtils.EMPTY);
    products.forEach(product -> {
      TProductStock stockPersistent = stockRepository.findStockOfProduct(product.getProductId());
      ProductStock productStock = new ProductStock();
      if (stockPersistent != null) {
        BeanUtils.copyProperties(stockPersistent, productStock);
        productStock.setProductId(stockPersistent.getProductid());
        product.setActualStock(productStock);
      }
    });
    return products;
  }


  @GetMapping("/products")
  public List<ProductEntity> searchall(
      @RequestParam(required = false, defaultValue = "9999") int status,
      @RequestParam(required = false) String catalogId) {
    List<ProductEntity> entities = new ArrayList<>();
    List<TProduct> tProducts = new ArrayList<>();
    if (status == 9999) {
      tProducts = productRepository.findAll();
    } else {
      if (StringUtils.isNotEmpty(catalogId)) {
        tProducts = productRepository.findAllByCatalog(catalogId);
      } else {
        tProducts = productRepository.findAll(status);
      }

    }
    tProducts.forEach(tProduct -> {
      ProductEntity entity = new ProductEntity();
      BeanUtils.copyProperties(tProduct, entity);
      entity.setStatus(STATUS_PRODUCT.values()[tProduct.getStatus()]);
      entities.add(entity);
    });
    return entities;
  }
}
