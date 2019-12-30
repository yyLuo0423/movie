package com.movie.api.config;

import brave.Tracing;
import com.utils.filters.MethodLoggingFilter;
import com.utils.filters.TracingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;


@Configuration
public class FilterConfig {
  @Bean
  public FilterRegistrationBean requestLoggingFilterBean() {
    FilterRegistrationBean bean = new FilterRegistrationBean();
    MethodLoggingFilter filter = new MethodLoggingFilter();
    filter.setMaxRequestLoggingLength(1000);
    filter.setMaxResponseLoggingLength(1000);
    bean.setFilter(filter);
    return bean;
  }

  @Bean
  public FilterRegistrationBean tracingFilterBean(Tracing tracing) {
    FilterRegistrationBean bean = new FilterRegistrationBean();
    TracingFilter filter = new TracingFilter(tracing);
    bean.setFilter(filter);
    bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return bean;
  }

}
