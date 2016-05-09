package com.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * メッセージで渡す情報用DTO.
 *
 * @author Tomoaki Mikami
 */
@Data
@NoArgsConstructor
public class Sample {
  @JsonProperty
  private String name;

  @JsonProperty
  private int age;

  @JsonProperty
  private Date now;
}
