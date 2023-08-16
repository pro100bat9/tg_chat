package com.example.scrapper.dto.entity;

import lombok.Data;
import org.jetbrains.annotations.Nullable;

import java.time.OffsetDateTime;

@Data
public class LinkEntity {
    private Long id;
    private String url;
    private OffsetDateTime lastCheckTime;

    @Nullable
    private OffsetDateTime updateAtTime;
}
