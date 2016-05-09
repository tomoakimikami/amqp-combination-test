package com.example.service;

import com.example.dto.Sample;

/**
 * TODO クラス概要.
 *
 * @author Tomoaki Mikami
 */
public interface ProducerService {
  /**
   * Mutex idを付与してメッセージ送信.
   *
   * @param object 送信DTO
   */
  void send(Sample object);

  /**
   * @throws InterruptedException
   *
   */
  void await() throws InterruptedException;

  /**
   * @param count
   */
  void initLatch(int count);

  void countDownLatch();
}
