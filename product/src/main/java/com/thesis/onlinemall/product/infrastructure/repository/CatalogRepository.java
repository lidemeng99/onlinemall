package com.thesis.onlinemall.product.infrastructure.repository;

import com.thesis.onlinemall.product.infrastructure.entity.TCatalog;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CatalogRepository extends CrudRepository<TCatalog, String> {

  @Transactional
  @Modifying
  @Query("UPDATE TCatalog SET status=?4,modifier=?2,lastModifiedTime=?3 WHERE catalogId=?1")
  void submitCatalog(String catalogId, String submitter, Date submitTime, int status);

  @Transactional
  @Modifying
  @Query("UPDATE TCatalog SET status=?4,modifier=?2,lastModifiedTime=?3 WHERE parentId=?1")
  void submitChildCatalog(String catalogId, String submitter, Date submitTime, int status);

  @Query("SELECT t FROM TCatalog t WHERE t.parentId=?1 and t.status!=3 order by t.ordernum")
   List<TCatalog> findByPID(String pid);

  @Query("SELECT t FROM TCatalog t WHERE t.parentId=?1 and t.status=?2 order by t.ordernum")
  List<TCatalog> findByPID(String pid,int status);
}
