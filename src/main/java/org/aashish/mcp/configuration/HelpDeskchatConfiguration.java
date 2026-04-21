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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

/**
 * Spring configuration for creating a specialized {@link ChatClient} for the help desk system.
 *
 * <p>This configuration wires together the AI model, chat memory, and specific tools and advisors
 * to create a conversational AI tailored for help desk interactions.
 */
@Configuration
public class HelpDeskchatConfiguration {

  /**
   * The system prompt template that instructs the AI model on its role and capabilities within the
   * help desk context. Loaded from the classpath.
   */
  @Value("classpath:static/helpDeskSystemPromptTemplate.st")
  Resource resource;

  /**
   * Creates and configures the {@link ChatClient} bean for the help desk.
   *
   * <p>This bean is configured with:
   *
   * <ul>
   *   <li>A default system prompt to define the AI's persona and instructions.
   *   <li>Default tools (e.g., {@link TimeTools}) that the AI can use to perform actions.
   *   <li>A set of advisors for logging, token usage auditing, and chat memory management.
   * </ul>
   *
   * @param openAiChatModel The underlying AI model to use for generating responses (e.g., OpenAI's
   *     GPT).
   * @param chatMemory The memory store for maintaining conversation history across multiple turns.
   * @param tools A collection of tools (e.g., {@link TimeTools}) the AI can invoke.
   * @return A fully configured {@link ChatClient} instance ready for use.
   */
  @Bean("helpDeskChatClient")
  public ChatClient helpDeskChatClient(
      OpenAiChatModel openAiChatModel, ChatMemory chatMemory, TimeTools tools) {
    Advisor loggerAdvisor = new SimpleLoggerAdvisor();
    Advisor tokenAdvisor = new TokenUsageAuditAdvisor();
    Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
    ChatClient.Builder chatClient =
        ChatClient.builder(openAiChatModel).defaultSystem(resource)
            .defaultTools(tools)
            .defaultAdvisors(List.of(loggerAdvisor, tokenAdvisor, memoryAdvisor));
    return chatClient.build();
  }
}
