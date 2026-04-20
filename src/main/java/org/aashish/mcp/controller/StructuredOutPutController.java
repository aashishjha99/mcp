package org.aashish.mcp.controller;

import java.util.List;
import java.util.Map;
import org.aashish.mcp.dto.CountryCity;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/structured")
public class StructuredOutPutController {

  private final ChatClient ollamaChatClient;

  public StructuredOutPutController(@Qualifier("openAiChatClient") ChatClient ollamaChatClient) {
    this.ollamaChatClient = ollamaChatClient;
  }

  /**
   * endpoint for mapping data in the list
   * @param message
   * @return
   */
  @GetMapping("/chat-list")
  public ResponseEntity<List<String>> chatList(@RequestParam("message") String message) {
    List countryCities =
        ollamaChatClient
            .prompt()
            .user(message)
            .call()
            .entity(new ListOutputConverter());
    return new ResponseEntity<>(countryCities, HttpStatus.OK);
  }

  /**
   * end point for fetching parametrized reference for list of objects
   * @param message
   * @return
   */
  @GetMapping("/ol")
  public ResponseEntity<List<CountryCity>> chat(@RequestParam("message") String message) {
    List<CountryCity> countryCities =
        ollamaChatClient
            .prompt()
            .user(message)
            .call()
            .entity(new ParameterizedTypeReference<List<CountryCity>>() {});
    return new ResponseEntity<>(countryCities, HttpStatus.OK);
  }

  /**
   * end point for mapping data in map using mapoutputconverter to give output in map
   * @param message
   * @return
   */
  @GetMapping("/chat-map")
  public ResponseEntity<Map<String,String>> chatMap(@RequestParam("message") String message) {
    Map countryCities =
        ollamaChatClient
            .prompt()
            .user(message)
            .call()
            .entity(new MapOutputConverter());
    return new ResponseEntity<>(countryCities, HttpStatus.OK);
  }

}
