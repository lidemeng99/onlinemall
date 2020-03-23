package com.thesis.onlinemall.sales.infrastructure.repository;

import com.thesis.onlinemall.sales.infrastructure.entity.TOrder;
import com.thesis.onlinemall.sales.infrastructure.entity.TOrderProduct;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends CrudRepository<TOrderProduct, String> {

  @Query("SELECT t FROM TOrderProduct t WHERE t.orderid=?1")
  List<TOrderProduct> findAllItemsByOrder(String orderid);

}
