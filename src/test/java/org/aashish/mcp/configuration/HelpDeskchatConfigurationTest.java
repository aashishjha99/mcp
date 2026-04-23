package org.aashish.mcp.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.aashish.mcp.tools.TimeTools;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

class HelpDeskchatConfigurationTest {

  private final ApplicationContextRunner contextRunner =
      new ApplicationContextRunner()
          .withUserConfiguration(HelpDeskchatConfiguration.class)
          .withBean(OpenAiChatModel.class, () -> mock(OpenAiChatModel.class))
          .withBean(ChatMemory.class, () -> mock(ChatMemory.class))
          .withBean(TimeTools.class, () -> mock(TimeTools.class));

  @Test
  void shouldProvideHelpDeskChatClientBean() {
    contextRunner.run(
        context -> {
          assertThat(context).hasSingleBean(HelpDeskchatConfiguration.class);
          assertThat(context).hasBean("helpDeskChatClient");
          
          ChatClient chatClient = context.getBean("helpDeskChatClient", ChatClient.class);
          assertThat(chatClient).isNotNull();
        });
  }
}
