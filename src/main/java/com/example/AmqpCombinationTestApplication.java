package com.example;

import com.example.dto.Sample;
import com.example.service.ConsumerService;
import com.example.service.ProducerService;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Calendar;
import java.util.Random;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import lombok.extern.java.Log;

@SpringBootApplication
@EnableBatchProcessing
@Log
@MapperScan(basePackages = "com.example.repository")
public class AmqpCombinationTestApplication implements CommandLineRunner {
  /**
   * 実行回数.
   */
  private static final int COUNT = 10;

  @Autowired
  private ProducerService producerService;

  @Autowired
  private ConsumerService consumerService;

  /**
   * エントリーポイント.
   *
   * @param args 引数
   */
  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(AmqpCombinationTestApplication.class);
    application.setWebEnvironment(false);
    ApplicationContext context = application.run(args);
    SpringApplication.exit(context);
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public void run(String... args) {
    log.info("Start!");
    producerService.initLatch(COUNT);
    consumerService.initLatch(COUNT);
    randomIntStream()//
        .parallel()//
        .forEach(consumer());
    await();
    log.info("Done!");
  }

  /**
   * 待ち合わせ.
   */
  private void await() {
    try {
      producerService.await();
      consumerService.await();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * ランダムな整数リストのストリームを生成.
   *
   * @return ストリーム
   */
  private IntStream randomIntStream() {
    final int startIndex = 1;
    IntStream stream = IntStream.range(startIndex, startIndex + COUNT);
    return stream;
  }

  private IntConsumer consumer() {
    final int limit = 10;
    return new IntConsumer() {
      /**
       * {@inheritDoc}.
       */
      @Override
      public void accept(int value) {
        // 送信
        try {
          // ランダムな時間、スリープ
          Random random = new Random();
          int rand = random.nextInt(limit);
          rand = rand < 0 ? -rand : rand;
          final int sleep = rand * 1000;
          log.info(String.format("I'm sleeping:%dms", sleep));
          if (sleep > 0) {
            Thread.sleep(sleep);
          }
          // メッセージ送信d
          Sample sample = new Sample();
          sample.setAge(10);
          sample.setName("Taro");
          sample.setNow(Calendar.getInstance().getTime());
          producerService.send(sample);
        } catch (InterruptedException e) {
          log.info("Interrupted");
        } finally {
          producerService.countDownLatch();
        }
      }
    };
  };
}
