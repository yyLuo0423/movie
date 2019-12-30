package com.model.base;

import com.utils.time.Clock;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ThreadTransactionContext {
  private String transactionId = UUID.randomUUID().toString();
  private long timeStarted = Clock.now();
}
