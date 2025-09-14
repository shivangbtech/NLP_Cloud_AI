package com.example.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class SummaryService {

  private final WebClient http;
  private final ObjectMapper objectMapper = new ObjectMapper();

  public SummaryService() {
    this.http = WebClient.builder()
        .baseUrl("https://api.nlpcloud.io/v1")
        .defaultHeader("Authorization", "Token " + System.getenv("NLPCLOUD_API_TOKEN"))
        .defaultHeader("Content-Type", "application/json")
        .build();
  }

  public String summarize(String text) {
    SummaryRequest request = new SummaryRequest(text);
    // Read response as String instead of trying to bind automatically
    String rawResponse = http.post()
        .uri("/bart-large-cnn/summarization")
        .body(Mono.just(request), SummaryRequest.class)
        .retrieve()
        .bodyToMono(String.class)
        .block();

    try {
      JsonNode json = objectMapper.readTree(rawResponse);
      // NLP Cloud usually returns {"summary_text": "..."}
      if (json.has("summary_text")) {
        return json.get("summary_text").asText();
      }
      return "Unexpected response: " + rawResponse;
    } catch (Exception e) {
      return "Failed to parse response: " + rawResponse;
    }
  }

  static class SummaryRequest {
    public String text;
    public SummaryRequest(String text) {
      this.text = text;
    }
  }

  static class SummaryResponse {
    public String summary_text; // NLP Cloud returns summary_text field
  }
}
