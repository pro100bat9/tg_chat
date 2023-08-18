package com.example.scrapper.service.github;

public enum GitHubType {
    CommitCommentEvent("Commit was committed!"),
    PushEvent("Event was pushed!"),
    IssueCommentEvent("Issue was commented!"),
    PullRequestReviewCommentEvent("Pull request was reviewed!"),
    UnknownEvent("Unknown event");

    GitHubType(String eventType) {
        this.description = eventType;
    }

    private final String description;

    public String getDescription() {
        return description;
    }
}
