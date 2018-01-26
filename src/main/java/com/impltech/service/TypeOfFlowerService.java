package com.impltech.service;

import com.impltech.domain.TypeOfFlower;
import com.impltech.domain.Variety;
import com.impltech.repository.TypeOfFlowerRepository;
import com.impltech.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author alex
 */
@Service
@Transactional
public class TypeOfFlowerService {

    private final Logger log = LoggerFactory.getLogger(VarietyService.class);

    private final TypeOfFlowerRepository typeOfFlowerRepository;
    private final VarietyService varietyService;

    public TypeOfFlowerService(TypeOfFlowerRepository typeOfFlowerRepository, VarietyService varietyService) {
        this.typeOfFlowerRepository = typeOfFlowerRepository;
        this.varietyService = varietyService;
    }

    public List<TypeOfFlower> findTypeOfFlowersByIdCompany(Long id) {
        log.debug("Requesto to get TypeOfFlowersByIdCompany where id is " + id);
        return typeOfFlowerRepository.findTypeOfFlowersByIdCompany(id);
    }

    public List<TypeOfFlower> findTypeOfFlowersByCompany() {
        Long id = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        log.debug("Requesto to get TypeOfFlowersByIdCompany where id is " + id);
        return typeOfFlowerRepository.findTypeOfFlowersByIdCompany(id);
    }

    public List<String> delete(Long id) {
        log.debug("Request to delete Variety : {}", id);

        List<String> varietyName = new ArrayList<>();
        List<Variety> varieties =  varietyService.findAllByCompany();

        for(Variety variety: varieties) {
            if(variety.getTypeOfFlower().getId() == id) {
                varietyName.add(variety.getName());
            }
        }
        return varietyName;
    }
}
