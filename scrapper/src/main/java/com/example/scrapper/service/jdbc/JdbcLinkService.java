package com.example.scrapper.service.jdbc;

import com.example.scrapper.dto.model.LinkDto;
import com.example.scrapper.repository.jdbc.JdbcLinkRepository;
import com.example.scrapper.service.interfaces.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;

@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {
    private final JdbcLinkRepository jdbcLinkRepository;

    @Override
    @Transactional
    public List<LinkDto> updateLastCheckedTime(Duration offsetDateTime) {
        return jdbcLinkRepository.updateLastTimeCheck(OffsetDateTime.now().plus(offsetDateTime));
    }

    @Override
    @Transactional
    public void updateLink(LinkDto link, OffsetDateTime time) {
        jdbcLinkRepository.updateLastUpdateTime(link.getId(), time);

    }
}
