package org.aashish.mcp.configuration;

import java.util.List;
import org.aashish.mcp.advisor.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
// import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ChatClientConfig {

  /*
    * Chat client bean for OpenAiChatModel with logging and token usage audit advisors.
    *
    */
  @Bean()
  @Primary
  public ChatClient openAiChatClient(OpenAiChatModel openAiChatModeln) {
    return ChatClient.builder(openAiChatModeln)
        .defaultAdvisors(List.of(new SimpleLoggerAdvisor(), new TokenUsageAuditAdvisor()))
        .build();
  }

  /*
   * Chat client bean for OllamaChatModel with logging and token usage audit advisors.
   *  This client will be used to interact with the Ollama language model,
   *  and the advisors will provide logging and token usage auditing for each chat interaction.
   *
   * NOTE: Ollama starter is currently disabled in build.gradle. Uncomment if needed.
   */

  /*
  @Bean()
  public ChatClient ollamaChatClient(OllamaChatModel ollamaChatModel) {
    ChatClient.Builder chatClient =
        ChatClient.builder(ollamaChatModel)
            .defaultAdvisors(List.of(new SimpleLoggerAdvisor(), new TokenUsageAuditAdvisor()));
    return chatClient.build();
  }
  */
}
