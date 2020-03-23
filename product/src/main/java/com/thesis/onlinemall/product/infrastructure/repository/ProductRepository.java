package com.thesis.onlinemall.product.infrastructure.repository;

import com.thesis.onlinemall.product.infrastructure.entity.TProduct;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductRepository extends CrudRepository<TProduct, String> {


  @Transactional
  @Modifying
  @Query("UPDATE TProduct SET status=?4,modifier=?2,lastModifiedTime=?3 WHERE productId=?1")
  void updateStatus(String productid, String submitter, Date submitTime, int status);

  @Transactional
  @Modifying
  @Query("UPDATE TProduct SET stock=?2,unit=?3 WHERE productId=?1")
  void updateStock(String productid, int stock, String unit);

  @Transactional
  @Modifying
  @Query("UPDATE TProduct SET price=?2 WHERE productId=?1")
  void updatePrice(String productid, BigDecimal price);


  @Query("SELECT t FROM TProduct t WHERE  t.status!=10 order by t.lastModifiedTime desc")
  List<TProduct> findAll();

  @Query("SELECT t FROM TProduct t WHERE  t.status=?1 order by t.lastModifiedTime desc")
  List<TProduct> findAll(int status);

  @Query("SELECT t FROM TProduct t WHERE  t.status=7 and catalogId=?1 order by t.lastModifiedTime desc")
  List<TProduct> findAllByCatalog(String catalogid);
}
