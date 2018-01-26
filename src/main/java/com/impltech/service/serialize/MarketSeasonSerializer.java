package com.impltech.service.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.impltech.domain.MarketSeason;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by dima
 */
public class MarketSeasonSerializer extends JsonSerializer<Set<MarketSeason>> {

    @Override
    public void serialize(Set<MarketSeason> marketSeasons, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        Set<MarketSeason> marketSeasons1 = new HashSet<>();
        marketSeasons1.addAll(marketSeasons);
        marketSeasons1 = marketSeasons1.stream().peek(m -> m.setSeason(null)).collect(Collectors.toSet());
        jsonGenerator.writeObject(marketSeasons1);
    }
}
