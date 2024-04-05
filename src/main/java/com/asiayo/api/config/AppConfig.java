package com.asiayo.api.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class AppConfig {

  @Bean
  public Map<String, Map<String, BigDecimal>> exchangeRates() {
    Map<String, Map<String, BigDecimal>> exchangeRates = new HashMap<>();
    try {
      String filePath = "data/ExchangeRate.json";
      ClassPathResource resource = new ClassPathResource(filePath);
      ObjectMapper om = new ObjectMapper();
      // Read file and parse to map
      JsonNode root = om.readTree(resource.getInputStream());
      JsonNode currenciesNode = root.get("currencies");
      for (Entry<String, JsonNode> sourceProperties : currenciesNode.properties()) {
        String source = sourceProperties.getKey();
        Map<String, BigDecimal> targetRateMap = new HashMap<>();
        for (Entry<String, JsonNode> property : sourceProperties.getValue().properties()) {
          targetRateMap.put(property.getKey(), property.getValue().decimalValue());
        }
        exchangeRates.put(source, targetRateMap);
      }
    } catch (IOException e) {
      throw new RuntimeException("Failed to load exchange rates data file", e);
    }
    return exchangeRates;
  }

}
