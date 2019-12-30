package com.utils.filters;

import org.springframework.context.annotation.Configuration;

@Configuration
public class TracingConfig {
  private static final String DEFAULT_HOST = "http://localhost:9411/api/v2/spans";
  private static final String DEFAULT_SAMPLE_RATE = "1.0";
  private static final String DEFAULT_SENDER_TYPE = "HTTP";


  public String getHost() {
    return DEFAULT_HOST;
  }

  public Float getSampleRate() {
    return Float.valueOf(DEFAULT_SAMPLE_RATE);
  }

  public String getSenderType() {
    return DEFAULT_SENDER_TYPE;
  }
}
