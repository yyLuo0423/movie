package com.utils.json;

import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtils {
  private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);
  private static final JsonConverter JSON_CACHE_CONVERTER = JsonConverter.getInstance();

  public JsonUtils() {
  }

  public static <M> M from(String value, Class<M> clazz) {
    return JSON_CACHE_CONVERTER.from(value, clazz);
  }

  public static <M> M from(String value, TypeReference<M> clazz) {
    return JSON_CACHE_CONVERTER.from(value, clazz);
  }

  public static <M> M fromOrException(String value, TypeReference<M> clazz) {
    M res = JSON_CACHE_CONVERTER.from(value, clazz);
    if (res == null) {
      throw new RuntimeException("deserialize error, " + value + " to " + clazz);
    } else {
      return res;
    }
  }

  public static <M> M fromOrException(String value, Class<M> clazz) {
    M res = JSON_CACHE_CONVERTER.from(value, clazz);
    if (res == null) {
      throw new RuntimeException("deserialize error, " + value + " to " + clazz);
    } else {
      return res;
    }
  }

  public static <M> M convertOrException(Object value, Class<M> clazz) {
    M res = JSON_CACHE_CONVERTER.convert(value, clazz);
    if (res == null) {
      throw new RuntimeException("convertOrException error, " + value + " to " + clazz);
    } else {
      return res;
    }
  }

  public static String toString(Object obj) {
    return JSON_CACHE_CONVERTER.to(obj);
  }
}
