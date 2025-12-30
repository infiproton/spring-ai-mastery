package com.infiproton.springaidemo.rag;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class VectorStoreService {
    private final PDFLoader pdfLoader;
    private final TokenTextSplitter textSplitter;
    @Getter
    private final VectorStore vectorStore;

    @Value("${app.documents.travel-policy.file.path}")
    private String travelPolicyFilePath;
    @Value("${app.documents.events.file.path}")
    private String eventsFilePath;

    public VectorStoreService(PDFLoader pdfLoader, VectorStore vectorStore) {
        this.pdfLoader = pdfLoader;
        this.vectorStore = vectorStore;
        this.textSplitter = new TokenTextSplitter();
    }

    public void initialize() throws IOException {
        String travelPolicyText = pdfLoader.loadPDF(travelPolicyFilePath);
        String eventsText = pdfLoader.loadPDF(eventsFilePath);

        vectorStore.add(textSplitter.split(new Document(travelPolicyText)));
        vectorStore.add(textSplitter.split(new Document(eventsText)));

        log.info("✅ Vector store initialized with both PDF file's content.");
    }

}
