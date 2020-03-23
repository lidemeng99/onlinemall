package com.thesis.onlinemall.sales.infrastructure.repository;

import com.thesis.onlinemall.sales.domain.entity.OrderConstants.ORDER_STATUS;
import com.thesis.onlinemall.sales.infrastructure.entity.TOrder;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Repository
public interface OrderRepository extends CrudRepository<TOrder, String> {
  @Transactional
  @Modifying
  @Query("UPDATE TOrder SET status=?4,modifier=?2,lastmodifiedtime=?3 WHERE orderid=?1")
  void updatePayStatus(String orderid, String submitter, Date submitTime, int status);


  @Query("SELECT t FROM TOrder t WHERE t.status!=10 and openid=?1")
  List<TOrder> findByCustomer(String openid);

  @Query("SELECT t FROM TOrder t WHERE t.status=?1")
  List<TOrder> findByStates(int status);

}
