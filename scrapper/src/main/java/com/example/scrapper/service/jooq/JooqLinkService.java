package com.example.scrapper.service.jooq;

import com.example.scrapper.dto.model.LinkDto;
import com.example.scrapper.repository.jooq.JooqLinkRepository;
import com.example.scrapper.service.interfaces.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JooqLinkService implements LinkService {

    private final JooqLinkRepository jooqLinkRepository;


    @Override
    @Transactional
    public List<LinkDto> updateLastCheckedTime(Duration offsetDateTime) {
        return jooqLinkRepository.updateLastTimeCheck(OffsetDateTime.now().plus(offsetDateTime));
    }

    @Override
    @Transactional
    public void updateLink(LinkDto link, OffsetDateTime time) {
        jooqLinkRepository.updateLastUpdateTime(link.getId(), time);

    }
}
