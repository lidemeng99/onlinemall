package com.thesis.onlinemall.sales.infrastructure.repository;

import com.thesis.onlinemall.sales.infrastructure.entity.TOrder;
import com.thesis.onlinemall.sales.infrastructure.entity.TShipping;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingRepository extends CrudRepository<TShipping, String> {
  @Query("SELECT t FROM TShipping t WHERE openid=?1")
  List<TShipping> findByCustomer(String openid);
}
