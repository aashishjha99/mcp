package org.aashish.mcp;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

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
  public void checkMcpFile() throws Exception {
    System.out.println(System.getenv("GITHUB_TOKEN"));
    ClassPathResource resource = new ClassPathResource("mcp-servers.json");
    System.out.println("MCP JSON exists: " + resource.exists());
  }
}
