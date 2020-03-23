package com.thesis.onlinemall.product.domain.entity;

public interface StatusConstants {

  enum STATUS_CATALOG {
    DRAFT, SUBMITTED, APPROVED, CANCELED
  }

  enum STATUS_PRODUCT {
    INITIALLED, SUBMITTED, STOCKING, STOCKED, PRICING, PRICED, LAUNCHING, LAUNCHED, CANCELLING, CANCELLED, DELETED
  }
}
