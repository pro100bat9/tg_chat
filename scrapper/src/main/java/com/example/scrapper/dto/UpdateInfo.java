package com.example.scrapper.dto;

import java.time.OffsetDateTime;
import java.util.List;

public record UpdateInfo(OffsetDateTime dateTime, List<String> descriptions) {
}
