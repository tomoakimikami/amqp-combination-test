package com.example.repository;

import com.example.repository.entity.RabbitMqMutex;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Mutexテーブルアクセス用リポジトリ.
 *
 * @author Tomoaki Mikami
 */
@Repository
public interface RabbitMqMutexRepository
    extends CrudRepository<RabbitMqMutex, Long>, JpaSpecificationExecutor<RabbitMqMutex> {
  /**
   * mutex用シーケンス値を取得.
   *
   * @return シーケンス値
   */
  @Query(value = "select RABBITMQ_MUTEX_SEQ.nextval from dual", nativeQuery = true)
  long nextSeq();
}
