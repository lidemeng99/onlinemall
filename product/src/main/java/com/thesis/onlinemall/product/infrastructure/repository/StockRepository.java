package com.thesis.onlinemall.product.infrastructure.repository;

import com.thesis.onlinemall.product.infrastructure.entity.TProductStock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface StockRepository extends CrudRepository<TProductStock, String> {

  @Transactional
  @Modifying
  @Query("UPDATE TProductStock SET actual = ?2 WHERE productId=?1")
  void deductStock(String productid, int deduct);

  @Query("SELECT t FROM TProductStock t WHERE t.productid=?1")
  TProductStock findStockOfProduct(String productid);
}
