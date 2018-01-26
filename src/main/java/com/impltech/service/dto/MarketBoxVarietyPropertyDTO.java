package com.impltech.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.impltech.domain.Market;
import com.impltech.domain.MarketBox;
import com.impltech.domain.Variety;
import com.impltech.domain.enumeration.Length;
import com.impltech.service.serialize.MapKeyDeserializer;
import com.impltech.service.serialize.DtoMapDeserializer;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;

/**
 * @author platon
 */
public class MarketBoxVarietyPropertyDTO {

    @JsonDeserialize(keyUsing = MapKeyDeserializer.class)
    private Map<SimpleEntry<Length, Integer>, Long> capacitiesOnLength;

    @JsonDeserialize(using = DtoMapDeserializer.class)
    private Object capacitiesOnLengthObj;

    private Variety variety;
    private Market market;
    private MarketBox marketBox;

    public MarketBoxVarietyPropertyDTO() {
    }

    public Object getCapacitiesOnLengthObj() {
        return capacitiesOnLengthObj;
    }

    public void setCapacitiesOnLengthObj(Object capacitiesOnLengthObj) {
        this.capacitiesOnLengthObj = capacitiesOnLengthObj;
    }

    public MarketBox getMarketBox() {
        return marketBox;
    }

    public void setMarketBox(MarketBox marketBox) {
        this.marketBox = marketBox;
    }

    public Variety getVariety() {
        return variety;
    }

    public void setVariety(Variety variety) {
        this.variety = variety;
    }

    public Map<SimpleEntry<Length, Integer>, Long> getCapacitiesOnLength() {
        return capacitiesOnLength;
    }

    public void setCapacitiesOnLength(Map<SimpleEntry<Length, Integer>, Long> capacitiesOnLength) {
        this.capacitiesOnLength = capacitiesOnLength;
    }

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MarketBoxVarietyPropertyDTO that = (MarketBoxVarietyPropertyDTO) o;

        if (!variety.equals(that.variety)) return false;
        if (capacitiesOnLength != null ? !capacitiesOnLength.equals(that.capacitiesOnLength) : that.capacitiesOnLength != null)
            return false;
        return marketBox.equals(that.marketBox);
    }

    @Override
    public int hashCode() {
        int result = variety.hashCode();
        result = 31 * result + (capacitiesOnLength != null ? capacitiesOnLength.hashCode() : 0);
        result = 31 * result + marketBox.hashCode();
        return result;
    }
}
