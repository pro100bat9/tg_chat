package com.example.scrapper.service.stackoverflow;

import com.example.scrapper.client.stackoverflowClient.StackoverflowWebClient;
import com.example.scrapper.dto.UpdateInfo;
import com.example.scrapper.dto.response.StackoverflowApiResponse;
import com.example.scrapper.dto.response.StackoverflowApiResponseItem;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StackoverflowService {
    private final StackoverflowWebClient stackoverflowWebClient;

    public @Nullable UpdateInfo fetchUpdate(String id){
        StackoverflowApiResponse stackoverflowApiResponse = stackoverflowWebClient.fetchQuestion(id);
        StackoverflowApiResponseItem stackoverflowApiResponseItem = stackoverflowApiResponse.items().get(0);
        if(stackoverflowApiResponseItem != null){
            return new UpdateInfo(stackoverflowApiResponseItem.lastActivityDate());
        }
        return null;
    }
}
