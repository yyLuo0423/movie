package com.model.base;

import com.movie.util.Clock;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ThreadTransactionContext {
  private String transactionId = UUID.randomUUID().toString();
  private long timeStarted = Clock.now();
}
