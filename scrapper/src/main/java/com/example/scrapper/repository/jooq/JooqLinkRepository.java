package com.example.scrapper.repository.jooq;

import com.example.scrapper.domain.jooq.tables.Link;
import com.example.scrapper.domain.jooq.tables.Subscription;
import com.example.scrapper.dto.model.LinkDto;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

import static org.jooq.impl.DSL.currentOffsetDateTime;
import static org.jooq.impl.DSL.select;

@Repository
@RequiredArgsConstructor
public class JooqLinkRepository {
    private final DSLContext dslContext;
    private final Link link = Link.LINK;
    private final Subscription subscription = Subscription.SUBSCRIPTION;

    public LinkDto addLink(String url){
       return dslContext.insertInto(link)
               .set(link.URL, url)
               .returning(link.fields())
               .fetchOneInto(LinkDto.class);

    }

    public LinkDto findByUrl(String url){
        return dslContext.select(link.fields())
                .from(link)
                .where(link.URL.eq(url))
                .fetchOneInto(LinkDto.class);


    }

    public LinkDto findById(Long linkId){
        return dslContext.select(link.fields())
                .from(link)
                .where(link.ID.eq(linkId))
                .fetchOneInto(LinkDto.class);

    }

    public List<LinkDto> findAll(){
        return dslContext.select(link.fields())
                .from(link)
                .fetchInto(LinkDto.class);

    }

    public List<LinkDto> findLinksFromChat(Long chatId){
        return dslContext.select(link.fields())
                .from(link)
                .join(subscription)
                .on(link.ID.eq(subscription.LINK_ID))
                .where(subscription.CHAT_ID.eq(chatId))
                .fetchInto(LinkDto.class);

    }


    public List<LinkDto> updateLastTimeCheck(OffsetDateTime date){
        return dslContext.update(link)
                .set(link.LAST_CHECK_TIME, currentOffsetDateTime())
                .where(link.LAST_CHECK_TIME.eq(date))
                .returning(link.fields())
                .fetchInto(LinkDto.class);

    }

    public void updateLastUpdateTime(Long chatId, OffsetDateTime date){
        dslContext.update(link)
                .set(link.UPDATED_AT, date)
                .where(link.ID.eq(chatId))
                .execute();
    }

    public void removeById(Long linkId){
        dslContext.delete(link)
                .where(link.ID.eq(linkId))
                .execute();
    }

    public void removeByUrl(String url){
        dslContext.delete(link)
                .where(link.URL.eq(url))
                .execute();
    }

    public void removeLinkWithZeroChats(){
        dslContext.delete(link)
                .where(link.ID.in(
                        select(link.ID)
                                .from(link)
                                .leftOuterJoin(subscription)
                                .on(link.ID.eq(subscription.LINK_ID))
                                .where(subscription.CHAT_ID.isNull())
                ))
                .execute();
    }
}
