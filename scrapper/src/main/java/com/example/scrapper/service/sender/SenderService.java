package com.example.scrapper.service.sender;

import com.example.scrapper.dto.request.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SenderService {
    private final Sender sender;

    public void sendUpdate(LinkUpdateRequest update){
        sender.send(update);
    }
}
