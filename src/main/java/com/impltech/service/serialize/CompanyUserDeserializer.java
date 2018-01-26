package com.impltech.service.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.impltech.domain.CompanyUser;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dima
 */
public class CompanyUserDeserializer extends JsonDeserializer<Set<CompanyUser>> {

    @Override
    public Set<CompanyUser> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(jsonParser);
        Set<CompanyUser> companyUsers = mapper.convertValue(node, new TypeReference<HashSet<CompanyUser>>() {});
        return companyUsers;
    }
}
