package com.example.scrapper.service.jpa;

import com.example.scrapper.dto.model.LinkDto;
import com.example.scrapper.entity.Link;
import com.example.scrapper.repository.jpa.JpaChatRepository;
import com.example.scrapper.repository.jpa.JpaLinkRepository;
import com.example.scrapper.service.interfaces.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JpaLinkService implements LinkService {
    private final JpaLinkRepository jpaLinkRepository;

    @Override
    public List<LinkDto> updateLastCheckedTime(Duration offsetDateTime) {
        return jpaLinkRepository.updateLastTimeCheck(OffsetDateTime.now().plus(offsetDateTime))
                .stream()
                .map(LinkDto::new)
                .toList();
    }

    @Override
    public void updateLink(LinkDto link, OffsetDateTime time) {
        Optional<Link> linkOptional = jpaLinkRepository.findById(link.getId());
        Link linkEntity = linkOptional.get();
        linkEntity.setUpdateAtTime(time);
        jpaLinkRepository.saveAndFlush(linkEntity);
    }
}
