package com.impltech.service.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.impltech.service.dto.DefaultPriceListDTO;
import com.impltech.service.dto.PriceListDTO;
import com.impltech.service.dto.SeasonPriceListDTO;

import java.io.IOException;
import java.util.List;

/**
 * @author platon
 */
public class PriceListTypeDeserializer extends JsonDeserializer<List<PriceListDTO>> {
    @Override
    public List<PriceListDTO> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(jsonParser);
        if (node.get(0).get("marketSeason") != null && !node.get(0).get("marketSeason").isNull()) {
           return mapper.reader().forType(new TypeReference<List<SeasonPriceListDTO>>() {}).readValue(node.toString());
        }
        return mapper.reader().forType(new TypeReference<List<DefaultPriceListDTO>>() {}).readValue(node.toString());
    }
}
