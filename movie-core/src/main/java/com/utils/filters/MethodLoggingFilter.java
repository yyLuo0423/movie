package com.utils.filters;

import com.utils.json.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Slf4j
public class MethodLoggingFilter extends OncePerRequestFilter {

  private int maxRequestLoggingLength = -1;
  private int maxResponseLoggingLength = 0;


  /**
   * @param length The max length of the request string to be logged.
   *               If length < 0, the length is unlimited.
   */
  public void setMaxRequestLoggingLength(int length) {
    this.maxRequestLoggingLength = length;
  }

  public int getMaxRequestLoggingLength() {
    return maxRequestLoggingLength;
  }

  /**
   * @param length The max length of the response string to be logged.
   *               If length < 0, the length is unlimited.
   */
  public void setMaxResponseLoggingLength(int length) {
    this.maxResponseLoggingLength = length;
  }

  public int getMaxResponseLoggingLength() {
    return maxResponseLoggingLength;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
    log.info("[ REQUEST START ]");

    ContentCachingResponseWrapper responseToPass = new ContentCachingResponseWrapper(response);
    HttpServletRequest requestToPass;

    if (isJsonRequest(request)) {
      requestToPass = new ReReadableRequestWrapper(request);
    } else {
      requestToPass = request;
    }

    log.info("[ REQUEST ] " + formatRequest(requestToPass));

    super.doFilter(requestToPass, responseToPass, chain);

    log.info("[ RESPONSE ] " + formatResponse(responseToPass));
    log.info("[ REQUEST END ]");
  }

  protected String formatRequest(HttpServletRequest request) {
    String remoteAddress = request.getRemoteAddr();
    Integer remotePort = request.getRemotePort();
    String method = request.getMethod();
    String path = request.getServletPath();

    StringBuilder headers = new StringBuilder();
    Collections.list(request.getHeaderNames()).forEach(name -> {
      headers.append(name).append(":").append(Collections.list(request.getHeaders(name)).toString()).append(",");
    });

    String body = null;

    if (isJsonRequest(request)) {
      try {
        body = IOUtils.toString(request.getReader()).replaceAll("\\s+", "");
      } catch (IOException e) {
        log.error("Failed to get request body. ", e);
      }
    } else {
      body = JsonUtils.toString(request.getParameterMap());
    }

    return limitLength(String.format("%s:%s %s %s Headers: { %s } Payload: %s",
        remoteAddress,
        remotePort,
        method,
        path,
        headers.toString(),
        body
    ), maxRequestLoggingLength);
  }

  protected String formatResponse(ContentCachingResponseWrapper responseWrapper) throws IOException {

    String body = IOUtils.toString(responseWrapper.getContentInputStream());
    responseWrapper.copyBodyToResponse();

    Integer status = responseWrapper.getStatus();

    /*
     StringBuilder headers = new StringBuilder();
     responseWrapper.getHeaderNames().forEach(name -> {
     headers.append(name).append(":").append(responseWrapper.getHeaders(name).toString()).append(",");
     });
     */
    return limitLength(String.format("%s Payload: %s",
        status,
        body
    ), maxResponseLoggingLength);
  }

  protected String limitLength(String str, int maxLength) {
    if (maxLength >= 0) {
      int length = str.length();
      if (length > maxLength) {
        return StringUtils.left(str, maxLength) + " ... [ " + (length - maxLength) + " characters are omitted ]";
      }
    }
    return str;
  }

  protected boolean isJsonRequest(HttpServletRequest request) {
    if (request == null) {
      return false;
    }
    String contentType = request.getContentType();
    return MediaType.APPLICATION_JSON_VALUE.equals(contentType) || MediaType.APPLICATION_JSON_UTF8_VALUE.equals(contentType);
  }

}
