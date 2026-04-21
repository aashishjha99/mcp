package org.aashish.mcp.configuration;

import java.util.List;
import javax.sql.DataSource;
import org.aashish.mcp.advisor.TokenUsageAuditAdvisor;
import org.aashish.mcp.rag.PIIMaskingDocumentPostProcessor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 @author ajha

*/
@Configuration
public class ChatMemoryConfiguration {

  /*
   * JdbcChatMemoryRepository bean that uses a DataSource to connect to a database.
   * This repository will be used to store conversation history for the ChatMemory implementation.
   *
   */
  @Bean
  public JdbcChatMemoryRepository jdbcChatMemoryRepository(DataSource dataSource) {
    return JdbcChatMemoryRepository.builder().dataSource(dataSource).build();
  }

  /*
   * ChatMemory bean that uses a JDBC repository to store conversation history in a database. The MessageWindowChatMemory implementation will keep the last 10 messages in memory for quick access, while the rest of the conversation history will be stored in the database.
   */
  @Bean
  public ChatMemory chatMemory(JdbcChatMemoryRepository jdbcChatMemoryRepository) {
    return MessageWindowChatMemory.builder()
        .maxMessages(10)
        .chatMemoryRepository(jdbcChatMemoryRepository)
        .build();
  }

  /*
   * Chat client bean with memory advisor added to the advisor chain. This will ensure that the
   * conversation history is stored in the database and can be retrieved later for context in the conversation.
   *
   * The MessageChatMemoryAdvisor will automatically handle storing the conversation history in the database
   *
   */
  @Bean("chatMemoryClient")
  public ChatClient chatMemoryClient(
      OpenAiChatModel ollamaChatModel,
      ChatMemory chatMemory,
      RetrievalAugmentationAdvisor advisor) {
    Advisor loggerAdvisor = new SimpleLoggerAdvisor();
    Advisor tokenAdvisor = new TokenUsageAuditAdvisor();
    Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
    ChatClient.Builder chatClient =
        ChatClient.builder(ollamaChatModel)
            .defaultAdvisors(List.of(loggerAdvisor, tokenAdvisor, memoryAdvisor, advisor));
    return chatClient.build();
  }



  @Bean
  public RetrievalAugmentationAdvisor retrievalAugmentationAdvisor(VectorStore vectorStore) {
    return RetrievalAugmentationAdvisor.builder()
        .documentRetriever(
            VectorStoreDocumentRetriever.builder()
                .similarityThreshold(0.5)
                .topK(3)
                .vectorStore(vectorStore)
                .build())
        .documentPostProcessors(PIIMaskingDocumentPostProcessor.builder())
        .build();
  }
}
