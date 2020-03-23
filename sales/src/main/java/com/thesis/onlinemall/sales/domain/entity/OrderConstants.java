package com.thesis.onlinemall.sales.domain.entity;

public interface OrderConstants {

  enum ORDER_STATUS {
    SUBMITTED,
    PAYING,
    PAID,
    ALLOCATING,
    ALLOCATED,
    DELIVERING,
    DELIVERED,
    RETURNING,
    RETURNED,
    CLOSED,
    CANCELLED
  }
}
