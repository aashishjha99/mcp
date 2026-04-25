package org.aashish.mcp.configuration;

import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import org.springframework.beans.factory.annotation.Value;

/**
 * Disabling custom configuration to allow Spring Boot 3.3 auto-configuration 
 * to take over using properties defined in application.yaml.
 */
// @Configuration
public class OpentelemetryConfiguration {
  // @Bean
  public OtlpGrpcSpanExporter otlpGrpcSpanExporter(
      @Value("${management.otlp.tracing.endpoint:http://localhost:4317}") String url) {
    return OtlpGrpcSpanExporter.builder().setEndpoint(url).build();
  }
}
