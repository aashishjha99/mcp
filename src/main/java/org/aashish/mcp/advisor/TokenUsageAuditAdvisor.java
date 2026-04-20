package org.aashish.mcp.advisor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.metadata.Usage;


public class TokenUsageAuditAdvisor implements CallAdvisor {

  private static final Logger log = LoggerFactory.getLogger(TokenUsageAuditAdvisor.class);
  /**
   * Return the name of the advisor.
   *
   * @return the advisor name.
   */
  @Override
  public String getName() {
    return "TokenUsageAuditAdvisor";
  }

  @Override
  public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest,
      CallAdvisorChain callAdvisorChain) {
    ChatClientResponse chatResponse = callAdvisorChain.nextCall(chatClientRequest);
    if (chatResponse.chatResponse().getMetadata()!=null) {
      Usage usage = chatResponse.chatResponse().getMetadata().getUsage();
      if (usage != null) {
        log.info("Usage is {}", usage);
      }
    }
    return chatResponse;
  }

  @Override
  public int getOrder() {
    return 1;
  }
}
