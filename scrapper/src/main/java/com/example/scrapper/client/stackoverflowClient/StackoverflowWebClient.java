package com.example.scrapper.client.stackoverflowClient;

import com.example.scrapper.dto.response.StackoverflowApiResponse;

public interface StackoverflowWebClient {
    StackoverflowApiResponse fetchQuestion(String id);
}
