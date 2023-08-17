package com.example.scrapper.service.interfaces;

import com.example.scrapper.dto.entity.LinkEntity;

import java.time.OffsetDateTime;
import java.util.List;


public interface LinkService {
    List<LinkEntity> updateLastCheckedTime(OffsetDateTime offsetDateTime);
    void updateLink(LinkEntity link, OffsetDateTime time);
}
