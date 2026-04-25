package org.aashish.mcp.advisor;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.metadata.Usage;

public class TokenUsageAuditAdvisor implements CallAdvisor {

  private static final Logger log = LoggerFactory.getLogger(TokenUsageAuditAdvisor.class);
  private final MeterRegistry meterRegistry;

  public TokenUsageAuditAdvisor(MeterRegistry meterRegistry) {
    this.meterRegistry = meterRegistry;
  }

  @Override
  public String getName() {
    return "TokenUsageAuditAdvisor";
  }

  @Override
  public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest,
      CallAdvisorChain callAdvisorChain) {
    ChatClientResponse chatResponse = callAdvisorChain.nextCall(chatClientRequest);
    
    if (chatResponse.chatResponse().getMetadata() != null) {
      Usage usage = chatResponse.chatResponse().getMetadata().getUsage();
      String model = chatResponse.chatResponse().getMetadata().getModel();
      
      if (usage != null) {
        log.info("Token Usage for model {}: Prompt={}, Completion={}, Total={}", 
            model, usage.getPromptTokens(), usage.getCompletionTokens(), usage.getTotalTokens());
            
        // Record metrics for Prometheus
        List<Tag> tags = List.of(Tag.of("model", model != null ? model : "unknown"));
        
        meterRegistry.counter("spring.ai.tokens.prompt", tags).increment(usage.getPromptTokens());
        meterRegistry.counter("spring.ai.tokens.completion", tags).increment(usage.getCompletionTokens());
        meterRegistry.counter("spring.ai.tokens.total", tags).increment(usage.getTotalTokens());
      }
    }
    return chatResponse;
  }

  @Override
  public int getOrder() {
    return 1;
  }
}
