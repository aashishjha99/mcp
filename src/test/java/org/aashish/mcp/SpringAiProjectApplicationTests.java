package org.aashish.mcp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class SpringAiProjectApplicationTests {

    @MockBean
    private OpenAiChatModel openAiChatModel;

    @MockBean
    private VectorStore vectorStore;

    @Test
    void contextLoads() {
    }

}