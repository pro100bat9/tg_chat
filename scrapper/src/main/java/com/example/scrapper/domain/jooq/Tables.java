/*
 * This file is generated by jOOQ.
 */
package com.example.scrapper.domain.jooq;


import com.example.scrapper.domain.jooq.tables.Databasechangelog;
import com.example.scrapper.domain.jooq.tables.Databasechangeloglock;
import com.example.scrapper.domain.jooq.tables.Link;
import com.example.scrapper.domain.jooq.tables.Subscription;
import com.example.scrapper.domain.jooq.tables.TgChat;

import javax.annotation.processing.Generated;


/**
 * Convenience access to all tables in public.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.17.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>public.databasechangelog</code>.
     */
    public static final Databasechangelog DATABASECHANGELOG = Databasechangelog.DATABASECHANGELOG;

    /**
     * The table <code>public.databasechangeloglock</code>.
     */
    public static final Databasechangeloglock DATABASECHANGELOGLOCK = Databasechangeloglock.DATABASECHANGELOGLOCK;

    /**
     * The table <code>public.link</code>.
     */
    public static final Link LINK = Link.LINK;

    /**
     * The table <code>public.subscription</code>.
     */
    public static final Subscription SUBSCRIPTION = Subscription.SUBSCRIPTION;

    /**
     * The table <code>public.tg_chat</code>.
     */
    public static final TgChat TG_CHAT = TgChat.TG_CHAT;
}
