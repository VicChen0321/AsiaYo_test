package com.asiayo.api.controller;

import com.asiayo.api.service.CurrencyExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AsiaYoController {

  private final CurrencyExchangeService service;

  @GetMapping("/exchangeCurrency")
  public ResponseEntity<Object> exchangeCurrency(
      @RequestParam("source") final String source,
      @RequestParam("target") final String target,
      @RequestParam("amount") final String amount
  ) {
    return ResponseEntity.ok(service.getCurrencyExchange(source, target, amount));
  }

}
