package com.impltech.repository;

import com.impltech.domain.MixType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MixType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MixTypeRepository extends JpaRepository<MixType,Long> {

}
