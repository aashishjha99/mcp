package org.aashish.mcp.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@WebMvcTest
class GlobalExceptionHandlerTest {

  @Autowired private MockMvc mockMvc;

  // A dummy controller to trigger exceptions
  @RestController
  static class TestController {
    @GetMapping("/test-error")
    public String throwError() {
      throw new RuntimeException("Test exception message");
    }
  }

  // Import the GlobalExceptionHandler and the TestController for this test context
  @Configuration
  @Import({GlobalExceptionHandler.class, TestController.class})
  static class TestConfig {}

  @Test
  void handleAllExceptions_shouldReturnInternalServerError() throws Exception {
    mockMvc
        .perform(get("/test-error"))
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.status").value(HttpStatus.INTERNAL_SERVER_ERROR.value()))
        .andExpect(jsonPath("$.error").value(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()))
        .andExpect(jsonPath("$.message").value("Test exception message"))
        .andExpect(jsonPath("$.timestamp").exists());
  }
}
