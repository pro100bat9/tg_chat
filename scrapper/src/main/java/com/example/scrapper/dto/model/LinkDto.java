package com.example.scrapper.dto.model;

import lombok.Data;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.time.OffsetDateTime;

@Data
public class LinkDto {
    private Long id;
    private URI url;
    private OffsetDateTime lastCheckTime;

    @Nullable
    private OffsetDateTime updateAtTime;
}
