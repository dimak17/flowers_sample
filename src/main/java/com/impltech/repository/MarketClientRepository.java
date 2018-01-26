package com.impltech.repository;

import com.impltech.domain.MarketClient;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MarketClient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarketClientRepository extends JpaRepository<MarketClient,Long> {

}
