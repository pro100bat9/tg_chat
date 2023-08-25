package com.example.scrapper.dto.model;

import com.example.scrapper.entity.Link;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
public class LinkDto {
    private Long id;
    private String url;
    private OffsetDateTime lastCheckTime;

    @Nullable
    private OffsetDateTime updateAtTime;

    public LinkDto(Link link) {
        this.id = link.getId();
        this.url = link.getUrl();
        this.lastCheckTime = link.getLastCheckTime();
        this.updateAtTime = link.getUpdateAtTime();
    }
}
