package com.raghav.service;

import com.raghav.model.CoinDTO;
import com.raghav.response.ApiResponse;

public interface ChatBotService {
    ApiResponse getCoinDetails(String coinName);

    CoinDTO getCoinByName(String coinName);

    String simpleChat(String prompt);
}
