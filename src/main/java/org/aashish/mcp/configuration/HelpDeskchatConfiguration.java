package org.aashish.mcp.configuration;

import io.micrometer.core.instrument.MeterRegistry;
import java.util.List;
import org.aashish.mcp.advisor.TokenUsageAuditAdvisor;
import org.aashish.mcp.tools.TimeTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class HelpDeskchatConfiguration {

  @Value("classpath:static/helpDeskSystemPromptTemplate.st")
  Resource resource;

  @Bean("helpDeskChatClient")
  public ChatClient helpDeskChatClient(
      OpenAiChatModel openAiChatModel, ChatMemory chatMemory, TimeTools tools, MeterRegistry meterRegistry) {
    Advisor loggerAdvisor = new SimpleLoggerAdvisor();
    Advisor tokenAdvisor = new TokenUsageAuditAdvisor(meterRegistry);
    Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
    ChatClient.Builder chatClient =
        ChatClient.builder(openAiChatModel).defaultSystem(resource)
            .defaultTools(tools)
            .defaultAdvisors(List.of(loggerAdvisor, tokenAdvisor, memoryAdvisor));
    return chatClient.build();
  }
}
