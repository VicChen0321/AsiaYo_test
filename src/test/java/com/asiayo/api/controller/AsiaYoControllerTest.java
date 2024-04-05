package com.asiayo.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class AsiaYoControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void testExchangeCurrencyWithoutDecimalSuccess() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = get("/exchangeCurrency")
        .param("source", "USD")
        .param("target", "JPY")
        .param("amount", "2,010.0167");
    /* 2010.0167 => 2010.02
    2010.02 * 111.801 = 224722.24602
    224722.24602 => return 224,722.25
    */
    mockMvc.perform(requestBuilder)
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content()
            .json("{\"msg\":\"success\",\"amount\":\"224,722.25\"}"));
  }

  @Test
  void testExchangeCurrencyWithDecimalSuccess() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = get("/exchangeCurrency")
        .param("source", "TWD")
        .param("target", "USD")
        .param("amount", "1575");
    /*
    1575 * 0.03281 = 51.67575
    51.67575 => return 51.68
    * */
    mockMvc.perform(requestBuilder)
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content()
            .json("{\"msg\":\"success\",\"amount\":\"51.68\"}"));
  }

  @Test
  void testExchangeCurrencyWithSourceNotSupportFailed() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = get("/exchangeCurrency")
        .param("source", "KRW")
        .param("target", "USD")
        .param("amount", "1000");
    mockMvc.perform(requestBuilder)
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content()
            .json("{\"status\":400,\"reason\":\"Source currency:KRW not support\"}"));
  }

  @Test
  void testExchangeCurrencyWithTargetNotSupportFailed() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = get("/exchangeCurrency")
        .param("source", "TWD")
        .param("target", "KRW")
        .param("amount", "1000");
    mockMvc.perform(requestBuilder)
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content()
            .json("{\"status\":400,\"reason\":\"Target currency:KRW not support\"}"));
  }

  @Test
  void testExchangeCurrencyWithAmountFormatValidFailed() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = get("/exchangeCurrency")
        .param("source", "TWD")
        .param("target", "USD")
        .param("amount", "1.00.0");
    mockMvc.perform(requestBuilder)
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content()
            .json("{\"status\":400,\"reason\":\"Amount is not valid format\"}"));
  }
  @Test
  void testExchangeCurrencyWithAmountWithLetterFormatValidFailed() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = get("/exchangeCurrency")
        .param("source", "TWD")
        .param("target", "USD")
        .param("amount", "100150 TWD");
    mockMvc.perform(requestBuilder)
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content()
            .json("{\"status\":400,\"reason\":\"Amount is not valid format\"}"));
  }

}
