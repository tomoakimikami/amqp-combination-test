package com.example.repository;

import com.example.repository.entity.RabbitMqReservation;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 予約テーブルアクセス用リポジトリ.
 *
 * @author Tomoaki Mikami
 */
@Repository
public interface RabbitMqReservationRepository extends CrudRepository<RabbitMqReservation, Long>,
    JpaSpecificationExecutor<RabbitMqReservation> {
}
