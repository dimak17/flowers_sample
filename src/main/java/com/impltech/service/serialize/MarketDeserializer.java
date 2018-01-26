package com.impltech.service.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.impltech.domain.Market;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dima
 */
public class MarketDeserializer extends JsonDeserializer<Set<Market>> {
    @Override
    public Set<Market> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(jsonParser);
        Set<Market> markets = mapper.convertValue(node, new TypeReference<HashSet<Market>>() {});
        return markets;
    }
}
