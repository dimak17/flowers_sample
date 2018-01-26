package com.impltech.service.dto;

import com.impltech.domain.MarketSeason;

/**
 * @author alex
 */
public class SeasonPriceListDTO extends PriceListDTO {

    private MarketSeason marketSeason;

    public SeasonPriceListDTO() {
    }

    public MarketSeason getMarketSeason() {
        return marketSeason;
    }

    public void setMarketSeason(MarketSeason marketSeason) {
        this.marketSeason = marketSeason;
    }
}
