package com.impltech.service.dto;

import com.impltech.domain.Market;

/**
 * @author platon
 */
public class DefaultPriceListDTO extends PriceListDTO {

    private Market market;

    public DefaultPriceListDTO() {
    }

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }
}
