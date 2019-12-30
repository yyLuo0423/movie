package com.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonConverter {
  private static final ObjectMapper mapper = new ObjectMapper();

  private JsonConverter() {
  }

  public static JsonConverter getInstance() {
    return JsonConverter.InstanceHolder.instance;
  }

  public static ObjectMapper getMapper() {
    return mapper;
  }

  public <M> M from(String value, Class<M> clazz) {
    try {
      return mapper.readValue(value, clazz);
    } catch (IOException var4) {
      var4.printStackTrace();
      return null;
    }
  }

  public <M> M from(String value, TypeReference<M> clazz) {
    try {
      return mapper.readValue(value, clazz);
    } catch (IOException var4) {
      var4.printStackTrace();
      return null;
    }
  }

  public String to(Object obj) {
    try {
      return mapper.writeValueAsString(obj);
    } catch (JsonProcessingException var3) {
      return null;
    }
  }

  public <M> M convert(Object value, Class<M> clazz) {
    return mapper.convertValue(value, clazz);
  }

  static {
    //mapper.setTimeZone(YqgClock.getTimeZone().toTimeZone());
    //mapper.registerModule(new JodaModule());
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    //mapper.registerModule(new BigDecimalModule());
  }

  static class InstanceHolder {
    static JsonConverter instance = new JsonConverter();

    InstanceHolder() {
    }
  }
}
