package com.example.controller;

import com.example.service.SummaryService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/summarize")
public class SummarizerController {

  private final SummaryService summaryService;

  public SummarizerController(SummaryService summaryService) {
    this.summaryService = summaryService;
  }

  @PostMapping
  public Map<String, Object> summarize(@RequestBody Map<String, String> body) {
    String text = body.get("text");
    String result = summaryService.summarize(text);

    Map<String, Object> response = new HashMap<>();
    response.put("summary", result);
    return response;
  }
}
