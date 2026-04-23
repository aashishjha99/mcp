package org.aashish.mcp.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

class ChatClientConfigTest {

  private final ApplicationContextRunner contextRunner =
      new ApplicationContextRunner()
          .withUserConfiguration(ChatClientConfig.class)
          .withBean(OpenAiChatModel.class, () -> mock(OpenAiChatModel.class));

  @Test
  void shouldProvideOpenAiChatClientBean() {
    contextRunner.run(
        context -> {
          assertThat(context).hasSingleBean(ChatClientConfig.class);
          assertThat(context).hasSingleBean(ChatClient.class);
          assertThat(context).hasBean("openAiChatClient");

          ChatClient chatClient = context.getBean(ChatClient.class);
          assertThat(chatClient).isNotNull();
        });
  }
}
