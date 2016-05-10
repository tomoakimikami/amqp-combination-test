package com.example.service.impl;

import com.example.dto.Sample;
import com.example.service.ProducerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;

import spring.support.amqp.rabbit.ExactlyOnceDeliveryProducer;

/**
 * メッセージ送信サービス実装.
 *
 * @author Tomoaki Mikami
 */
@Service
public class ProducerServiceImpl implements ProducerService {
  /**
   * Producer待ち合わせ用ラッチ.
   */
  private CountDownLatch producerCountDownLatch = null;

  @Autowired
  private ExactlyOnceDeliveryProducer producer;


  /**
   * Mutex idを付与してメッセージ送信.
   *
   * @param object 送信DTO
   */
  @Transactional
  public void send(Sample object) {
    producer.send("default.exchange", "routing-key", object);
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public void await() throws InterruptedException {
    producerCountDownLatch.await();
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public void initLatch(int count) {
    producerCountDownLatch = new CountDownLatch(count);
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public void countDownLatch() {
    producerCountDownLatch.countDown();
  }
}
