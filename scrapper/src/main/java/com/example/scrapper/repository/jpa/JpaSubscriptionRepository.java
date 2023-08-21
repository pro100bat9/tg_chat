package com.example.scrapper.repository.jpa;

import com.example.scrapper.entity.Subscription;
import com.example.scrapper.entity.pk.SubscriptionPk;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(prefix = "app", name = "database-type", havingValue = "jpa")
public interface JpaSubscriptionRepository extends JpaRepository<Subscription, SubscriptionPk> {
    Integer countByLinkId(Long id);
}
