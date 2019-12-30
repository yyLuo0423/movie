package com.utils.filters;


import brave.Tracing;
import brave.sampler.Sampler;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Reporter;
import zipkin2.reporter.okhttp3.OkHttpSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zipkin2.reporter.Sender;

@Configuration
public class TracingConfiguration {

  @Autowired
  TracingConfig tracingConfig;

  @Bean
  public Sender sender() {
    String senderType = tracingConfig.getSenderType();
      return OkHttpSender.create(tracingConfig.getHost());
  }

  @Bean
  public Reporter reporter(Sender sender) {
    return AsyncReporter.create(sender);
  }

  @Bean
  public Tracing tracing(Reporter reporter) {
    return Tracing.newBuilder()
        .localServiceName("movie-home-yabi")
        .sampler(Sampler.create(tracingConfig.getSampleRate()))
        .spanReporter(reporter)
        .build();
  }
}
