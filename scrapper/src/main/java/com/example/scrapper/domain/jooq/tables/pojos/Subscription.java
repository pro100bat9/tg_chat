/*
 * This file is generated by jOOQ.
 */
package com.example.scrapper.domain.jooq.tables.pojos;


import java.beans.ConstructorProperties;
import java.io.Serializable;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.NotNull;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.17.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Subscription implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long chatId;
    private Long linkId;

    public Subscription() {}

    public Subscription(Subscription value) {
        this.chatId = value.chatId;
        this.linkId = value.linkId;
    }

    @ConstructorProperties({ "chatId", "linkId" })
    public Subscription(
        @NotNull Long chatId,
        @NotNull Long linkId
    ) {
        this.chatId = chatId;
        this.linkId = linkId;
    }

    /**
     * Getter for <code>public.subscription.chat_id</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getChatId() {
        return this.chatId;
    }

    /**
     * Setter for <code>public.subscription.chat_id</code>.
     */
    public void setChatId(@NotNull Long chatId) {
        this.chatId = chatId;
    }

    /**
     * Getter for <code>public.subscription.link_id</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getLinkId() {
        return this.linkId;
    }

    /**
     * Setter for <code>public.subscription.link_id</code>.
     */
    public void setLinkId(@NotNull Long linkId) {
        this.linkId = linkId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Subscription other = (Subscription) obj;
        if (this.chatId == null) {
            if (other.chatId != null)
                return false;
        }
        else if (!this.chatId.equals(other.chatId))
            return false;
        if (this.linkId == null) {
            if (other.linkId != null)
                return false;
        }
        else if (!this.linkId.equals(other.linkId))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.chatId == null) ? 0 : this.chatId.hashCode());
        result = prime * result + ((this.linkId == null) ? 0 : this.linkId.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Subscription (");

        sb.append(chatId);
        sb.append(", ").append(linkId);

        sb.append(")");
        return sb.toString();
    }
}