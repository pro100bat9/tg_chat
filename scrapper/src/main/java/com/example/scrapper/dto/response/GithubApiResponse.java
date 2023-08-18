package com.example.scrapper.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.Map;


@Data
public class GithubApiResponse {

        @JsonProperty("type")
        private String type;

        @JsonProperty("created_at")
        private OffsetDateTime createdAt;

        private String nameRepository;

        @JsonProperty("repo")
        private void takeNameRepo(Map<String, Object> repo){
                this.nameRepository = (String) repo.get("name");

        }


}