package com.utils.filters;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.commons.io.IOUtils;

public class ReReadableRequestWrapper extends HttpServletRequestWrapper {

  private final byte[] body;

  public ReReadableRequestWrapper(HttpServletRequest request) throws IOException {
    super(request);
    body = IOUtils.toByteArray(request.getInputStream());
  }

  @Override
  public BufferedReader getReader() throws IOException {
    return new BufferedReader(new InputStreamReader(this.getInputStream()));
  }

  @Override
  public ServletInputStream getInputStream() throws IOException {
    return new ReReadableServletInputStream(body);
  }

  private class ReReadableServletInputStream extends ServletInputStream {

    private final ByteArrayInputStream in;

    public ReReadableServletInputStream(byte[] bytes) {
      this.in = new ByteArrayInputStream(bytes);
    }

    @Override
    public boolean isFinished() {
      return in.available() == 0;
    }

    @Override
    public boolean isReady() {
      return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {
      throw new RuntimeException("Not implemented");
    }

    @Override
    public int read() throws IOException {
      return this.in.read();
    }
  }
}
