package com.model.base;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseModel {
  @Autowired
  private DSLContext dslContext;

  protected DSLContext create() {
    return this.dslContext;
  }
}