package com.example.service.impl;

import com.example.dto.Sample;
import com.example.repository.ReservationRepository;
import com.example.repository.entity.RabbitMqReservation;
import com.example.service.ConsumerService;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

import spring.support.amqp.rabbit.ExactlyOnceDeliveryAdvice.ExactlyOnceDelivery;
import spring.support.amqp.rabbit.ExactlyOnceDeliveryProducer;

/**
 * TODO クラス概要.
 *
 * @author Tomoaki Mikami
 */
@Service
public class ConsumerServiceImpl implements ConsumerService {
  /**
   * Consumer待ち合わせ用ラッチ.
   */
  private CountDownLatch consumerCountDownLatch = null;

  /**
   * 予約リポジトリ
   */
  @Autowired
  private ReservationRepository reservationRepository;

  /**
   * {@inheritDoc}.
   */
  @ExactlyOnceDelivery
  @RabbitListener(queues = "default.queue", containerFactory = "requeueRejectContainerFactory")
  @Override
  public void receive(Map<String, String> headers, Sample data) {
    // 受信したメッセージに応じて予約登録する.
    RabbitMqReservation reservation = new RabbitMqReservation();
    Optional<String> mutexOptional = Optional.of(headers.get(ExactlyOnceDeliveryProducer.MUTEX));
    String mutex = mutexOptional.orElseThrow(
        () -> new IllegalArgumentException(ExactlyOnceDeliveryProducer.MUTEX + " is required"));
    reservation.setMutex(Long.valueOf(mutex));
    reservation.setName(data.getName());
    reservation.setReservedAt(Calendar.getInstance().getTime());
    reservationRepository.save(reservation);
    // TODO たまにエラーにしてDLQへ飛ばしてみる
    consumerCountDownLatch.countDown();
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public void await() throws InterruptedException {
    consumerCountDownLatch.await();
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public void initLatch(int count) {
    consumerCountDownLatch = new CountDownLatch(count);
  }
}