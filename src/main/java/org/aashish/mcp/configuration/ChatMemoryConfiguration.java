package org.aashish.mcp.configuration;

import io.micrometer.core.instrument.MeterRegistry;
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

@Configuration
public class ChatMemoryConfiguration {

  @Bean
  public JdbcChatMemoryRepository jdbcChatMemoryRepository(DataSource dataSource) {
    return JdbcChatMemoryRepository.builder().dataSource(dataSource).build();
  }

  @Bean
  public ChatMemory chatMemory(JdbcChatMemoryRepository jdbcChatMemoryRepository) {
    return MessageWindowChatMemory.builder()
        .maxMessages(10)
        .chatMemoryRepository(jdbcChatMemoryRepository)
        .build();
  }

  @Bean("chatMemoryClient")
  public ChatClient chatMemoryClient(
      OpenAiChatModel aiChatModel,
      ChatMemory chatMemory,
      RetrievalAugmentationAdvisor advisor,
      MeterRegistry meterRegistry) {
    Advisor loggerAdvisor = new SimpleLoggerAdvisor();
    Advisor tokenAdvisor = new TokenUsageAuditAdvisor(meterRegistry);
    Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
    ChatClient.Builder chatClient =
        ChatClient.builder(aiChatModel)
            .defaultAdvisors(List.of(loggerAdvisor, tokenAdvisor, memoryAdvisor, advisor));
    return chatClient.build();
  }

  @Bean
  public RetrievalAugmentationAdvisor retrievalAugmentationAdvisor(VectorStore vectorStore) {
    return RetrievalAugmentationAdvisor.builder()
        .documentRetriever(
            VectorStoreDocumentRetriever.builder()
                .similarityThreshold(0.0)
                .topK(3)
                .vectorStore(vectorStore)
                .build())
        .documentPostProcessors(PIIMaskingDocumentPostProcessor.builder())
        .build();
  }
}
