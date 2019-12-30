package com.utils.filters;

import brave.Span;
import brave.Tracer;
import brave.Tracing;
import brave.propagation.TraceContext;
import lombok.NonNull;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

public class TracingFilter extends OncePerRequestFilter {
  private Tracing tracing;

  public TracingFilter(@NonNull Tracing tracing) {
    if (tracing == null) {
      throw new NullPointerException("tracing");
    } else {
      this.tracing = tracing;
    }
  }

  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    Tracer tracer = this.tracing.tracer();
    TraceContext.Extractor extractor = this.tracing.propagation().extractor(HttpServletRequest::getHeader);
    String rawUri = request.getRequestURI();
    String compactUri = URI.create(rawUri).getPath();
    String method = request.getMethod();
    Span span = tracer.nextSpan(extractor.extract(request)).name(method).kind(Span.Kind.SERVER).tag("Method", method).tag("Uri", compactUri).tag("RawUri", rawUri).start();

    try {
      Tracer.SpanInScope ws = tracer.withSpanInScope(span);
      Throwable var11 = null;

      try {
        MDC.put("trace_id", span.context().traceIdString());
        super.doFilter(request, response, filterChain);
      } catch (Throwable var29) {
        var11 = var29;
        throw var29;
      } finally {
        if (ws != null) {
          if (var11 != null) {
            try {
              ws.close();
            } catch (Throwable var28) {
              var11.addSuppressed(var28);
            }
          } else {
            ws.close();
          }
        }

      }
    } catch (Error | Exception var31) {
      span.error(var31);
      throw var31;
    } finally {
      MDC.clear();
      span.finish();
    }

  }
}
