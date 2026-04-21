package org.aashish.mcp;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class SpringAiProjectApplication {

  private final Environment env;

  public SpringAiProjectApplication(Environment env) {
    this.env = env;
  }

  public static void main(String[] args) {
    SpringApplication.run(SpringAiProjectApplication.class, args);
  }

  @PostConstruct
  public void check() { // ✅ no args
    String key = env.getProperty("spring.ai.openai.api-key");
    System.out.println("KEY = " + key);
  }
}
