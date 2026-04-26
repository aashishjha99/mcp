package org.aashish.mcp.configuration;

import io.micrometer.core.instrument.MeterRegistry;
import java.util.List;
import org.aashish.mcp.advisor.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ChatClientConfig {

  @Bean
  @Primary
  public ChatClient openAiChatClient(ChatClient.Builder builder, MeterRegistry meterRegistry) {
    return builder
        .defaultAdvisors(List.of(new SimpleLoggerAdvisor(), new TokenUsageAuditAdvisor(meterRegistry)))
        .build();
  }
}
