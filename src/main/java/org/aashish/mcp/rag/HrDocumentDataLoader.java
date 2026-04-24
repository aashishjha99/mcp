package org.aashish.mcp.rag;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class HrDocumentDataLoader implements loadDataInVectorDatabase {

  private final VectorStore vectorStore;

  @Value("classpath:static/Eazybytes_HR_Policies.pdf")
  Resource hrdatasource;

  public HrDocumentDataLoader(VectorStore vectorStore) {
    this.vectorStore = vectorStore;
  }

  @PostConstruct
  @Override
  public void loadSentencesIntoVectorStore() throws IOException {
    TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(hrdatasource);
    List<Document> documents = tikaDocumentReader.get();
    TextSplitter textSplitter =
        TokenTextSplitter.builder().withChunkSize(200).withMaxNumChunks(400).build();
    vectorStore.add(textSplitter.split(documents));
  }
}
