package com.asiayo.api.service;


import com.asiayo.api.dto.CurrencyExchangeResp;

public interface CurrencyExchangeService {

  CurrencyExchangeResp getCurrencyExchange(String source, String target, String amount);
}
