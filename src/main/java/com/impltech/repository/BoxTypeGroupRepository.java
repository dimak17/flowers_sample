package com.impltech.repository;

import com.impltech.domain.BoxTypeGroup;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BoxTypeGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BoxTypeGroupRepository extends JpaRepository<BoxTypeGroup,Long> {

}
