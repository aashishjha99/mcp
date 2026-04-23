package org.aashish.mcp.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

class ChatMemoryConfigurationTest {

  private final ApplicationContextRunner contextRunner =
      new ApplicationContextRunner()
          .withUserConfiguration(ChatMemoryConfiguration.class)
          .withBean(DataSource.class, () -> mock(DataSource.class))
          .withBean(OpenAiChatModel.class, () -> mock(OpenAiChatModel.class))
          .withBean(VectorStore.class, () -> mock(VectorStore.class));

  @Test
  void shouldProvideAllBeans() {
    contextRunner.run(
        context -> {
          // Verify configuration bean exists
          assertThat(context).hasSingleBean(ChatMemoryConfiguration.class);

          // Verify JdbcChatMemoryRepository bean
          assertThat(context).hasSingleBean(JdbcChatMemoryRepository.class);
          assertThat(context).hasBean("jdbcChatMemoryRepository");

          // Verify ChatMemory bean
          assertThat(context).hasSingleBean(ChatMemory.class);
          assertThat(context).hasBean("chatMemory");

          // Verify RetrievalAugmentationAdvisor bean
          assertThat(context).hasSingleBean(RetrievalAugmentationAdvisor.class);
          assertThat(context).hasBean("retrievalAugmentationAdvisor");

          // Verify ChatClient bean with specific name
          assertThat(context).hasBean("chatMemoryClient");
          ChatClient chatClient = context.getBean("chatMemoryClient", ChatClient.class);
          assertThat(chatClient).isNotNull();
        });
  }
}
