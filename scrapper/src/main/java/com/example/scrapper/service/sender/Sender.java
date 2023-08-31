package com.example.scrapper.service.sender;

import com.example.scrapper.dto.request.LinkUpdateRequest;

public interface Sender {
    void send(LinkUpdateRequest update);
}
