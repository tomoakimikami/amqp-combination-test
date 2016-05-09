package com.example.repository.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

/**
 * 予約エンティティ.
 *
 * @author Tomoaki Mikami
 */
@Entity(name = "RabbitMqReservation")
@Table(name = "RABBITMQ_RESERVATION")
@Data
public class RabbitMqReservation implements Serializable {
  /** serialVersionUID. */
  private static final long serialVersionUID = 4645467854525140610L;

  /**
   * Mutex Id.
   */
  @Id
  @Column(name = "MUTEX", precision = 18, scale = 0)
  private Long mutex;

  /**
   * 予約名
   */
  @Column(name = "NAME", length = 32)
  private String name;

  /**
   * 予約時刻.
   */
  @Column(name = "RESERVED_AT")
  @Temporal(value = TemporalType.TIMESTAMP)
  private Date reservedAt;
}
