package com.example.scrapper.service.interfaces;

import com.example.scrapper.dto.model.LinkDto;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;


public interface LinkService {
    List<LinkDto> updateLastCheckedTime(Duration offsetDateTime);
    void updateLink(LinkDto link, OffsetDateTime time);
}
