package com.example.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * トランザクション関連設定.
 *
 * @author Tomoaki Mikami
 */
@Configuration
public class TransactionConfiguration {
  /**
   * トランザクションマネージャを生成.
   *
   * @param dataSource データソース
   * @return 生成したトランザクションマネージャ
   */
  @Bean
  @Autowired
  public DataSourceTransactionManager transactionManager(DataSource dataSource) {
    DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
    transactionManager.setDataSource(dataSource);
    transactionManager.setRollbackOnCommitFailure(true); // commitエラー時にもちゃんとロールバックする。
    return transactionManager;
  }
}
