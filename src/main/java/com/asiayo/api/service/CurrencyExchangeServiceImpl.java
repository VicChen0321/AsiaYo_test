package com.asiayo.api.service;

import com.asiayo.api.dto.CurrencyExchangeResp;
import com.asiayo.api.exception.ApiException;
import com.asiayo.api.utils.NumberUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

  /**
   * Exchange rates. Through constructor inject
   */
  private final Map<String, Map<String, BigDecimal>> exchangeRates;

  @Override
  public CurrencyExchangeResp getCurrencyExchange(String source, String target, String amount) {
    if (!exchangeRates.containsKey(source)) {
      throw new ApiException(HttpStatus.BAD_REQUEST,
          String.format("Source currency:%s not support", source));
    }
    Map<String, BigDecimal> targetRates = exchangeRates.get(source);
    if (!targetRates.containsKey(target)) {
      throw new ApiException(HttpStatus.BAD_REQUEST,
          String.format("Target currency:%s not support", target));
    }
    amount = amount.replace(",", "");
    if (!NumberUtils.isNumeric(amount)) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "Amount is not valid format");
    }
    BigDecimal rate = targetRates.get(target);
    BigDecimal sourceAmount = new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP);
    BigDecimal targetAmount = sourceAmount.multiply(rate);
    DecimalFormat df = new DecimalFormat("#,###.00");
    return new CurrencyExchangeResp("success", df.format(targetAmount));
  }
}
