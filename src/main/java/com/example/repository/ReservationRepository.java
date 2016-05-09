package com.example.repository;

import com.example.repository.entity.RabbitMqReservation;

/**
 * 予約テーブルアクセス用リポジトリ.
 *
 * @author Tomoaki Mikami
 */
public interface ReservationRepository {

  /**
   * 予約レコード登録.
   *
   * @param reservation 予約レコード
   */
  void save(RabbitMqReservation reservation);
}
