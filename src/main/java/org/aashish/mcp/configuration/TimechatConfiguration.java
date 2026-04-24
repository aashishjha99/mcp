package org.aashish.mcp.configuration;

import java.util.List;
import org.aashish.mcp.advisor.TokenUsageAuditAdvisor;
import org.aashish.mcp.tools.TimeTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up the Timechat application.
 *
 * @author ajha
 */

@Configuration
public class TimechatConfiguration {

  /** This bean configures a ChatClient specifically for time-related interactions. */
  @Bean("timeChatClient")
  public ChatClient timeChatClient(
      OpenAiChatModel openAiChatModel, ChatMemory chatMemory, TimeTools tools) {
    Advisor loggerAdvisor = new SimpleLoggerAdvisor();
    Advisor tokenAdvisor = new TokenUsageAuditAdvisor();
    Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
    ChatClient.Builder chatClient =
        ChatClient.builder(openAiChatModel)
            .defaultTools(tools)
            .defaultAdvisors(List.of(loggerAdvisor, tokenAdvisor, memoryAdvisor));
    return chatClient.build();
  }

  /**
  @Bean
  ToolExecutionExceptionProcessor processor() {
    return new DefaultToolExecutionExceptionProcessor(true);
  }
  */
}
