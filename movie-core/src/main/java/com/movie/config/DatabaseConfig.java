package com.movie.config;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseConfig {

  public String getUrl() {
    return "jdbc:mysql";
  }

  public String getUserName() {
    return "root";
  }

  public String getPassword() {
    return "";
  }

  public Integer getMaxWait() {
    return 60000;
  }
  public List<String> getInitSqls() {
    List<String> sqls = new ArrayList<>();
    sqls.add("set names utf8mb4;");
    return sqls;
  }

}
