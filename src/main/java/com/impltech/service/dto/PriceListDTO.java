package com.impltech.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.impltech.domain.PriceList;
import com.impltech.domain.ShippingPolicy;
import com.impltech.domain.Variety;

import java.math.BigDecimal;

/**
 * @author alex
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceListDTO {
    private PriceList priceList;
    private Variety variety;
    private ShippingPolicy shippingPolicy;
    private BigDecimal _40;
    private BigDecimal _50;
    private BigDecimal _60;
    private BigDecimal _70;
    private BigDecimal _80;
    private BigDecimal _90;
    private BigDecimal _100;
    private BigDecimal _110;
    private BigDecimal _120;
    private BigDecimal _130;
    private BigDecimal _140;
    private BigDecimal _150;
    private BigDecimal _160;
    private BigDecimal _170;
    private BigDecimal _180;
    private BigDecimal _190;
    private BigDecimal _200;
    private BigDecimal _210;

    public PriceListDTO() {
    }

    public PriceList getPriceList() {
        return priceList;
    }

    public void setPriceList(PriceList priceList) {
        this.priceList = priceList;
    }

    public Variety getVariety() {
        return variety;
    }

    public void setVariety(Variety variety) {
        this.variety = variety;
    }

    public BigDecimal get_40() {
        return _40;
    }

    public void set_40(BigDecimal _40) {
        this._40 = _40;
    }

    public BigDecimal get_50() {
        return _50;
    }

    public void set_50(BigDecimal _50) {
        this._50 = _50;
    }

    public BigDecimal get_60() {
        return _60;
    }

    public void set_60(BigDecimal _60) {
        this._60 = _60;
    }

    public BigDecimal get_70() {
        return _70;
    }

    public void set_70(BigDecimal _70) {
        this._70 = _70;
    }

    public BigDecimal get_80() {
        return _80;
    }

    public void set_80(BigDecimal _80) {
        this._80 = _80;
    }

    public BigDecimal get_90() {
        return _90;
    }

    public void set_90(BigDecimal _90) {
        this._90 = _90;
    }

    public BigDecimal get_100() {
        return _100;
    }

    public void set_100(BigDecimal _100) {
        this._100 = _100;
    }

    public BigDecimal get_110() {
        return _110;
    }

    public void set_110(BigDecimal _110) {
        this._110 = _110;
    }

    public BigDecimal get_120() {
        return _120;
    }

    public void set_120(BigDecimal _120) {
        this._120 = _120;
    }

    public BigDecimal get_130() {
        return _130;
    }

    public void set_130(BigDecimal _130) {
        this._130 = _130;
    }

    public BigDecimal get_140() {
        return _140;
    }

    public void set_140(BigDecimal _140) {
        this._140 = _140;
    }

    public BigDecimal get_150() {
        return _150;
    }

    public void set_150(BigDecimal _150) {
        this._150 = _150;
    }

    public BigDecimal get_160() {
        return _160;
    }

    public void set_160(BigDecimal _160) {
        this._160 = _160;
    }

    public BigDecimal get_170() {
        return _170;
    }

    public void set_170(BigDecimal _170) {
        this._170 = _170;
    }

    public BigDecimal get_180() {
        return _180;
    }

    public void set_180(BigDecimal _180) {
        this._180 = _180;
    }

    public BigDecimal get_190() {
        return _190;
    }

    public void set_190(BigDecimal _190) {
        this._190 = _190;
    }

    public BigDecimal get_200() {
        return _200;
    }

    public void set_200(BigDecimal _200) {
        this._200 = _200;
    }

    public BigDecimal get_210() {
        return _210;
    }

    public void set_210(BigDecimal _210) {
        this._210 = _210;
    }

    public ShippingPolicy getShippingPolicy() {
        return shippingPolicy;
    }

    public void setShippingPolicy(ShippingPolicy shippingPolicy) {
        this.shippingPolicy = shippingPolicy;
    }

    @Override
    public String toString() {
        return "PriceListDTO{" +
            ", variety=" + variety +
            ", _40=" + _40 +
            ", _50=" + _50 +
            ", _60=" + _60 +
            ", _70=" + _70 +
            ", _80=" + _80 +
            ", _90=" + _90 +
            ", _100=" + _100 +
            ", _110=" + _110 +
            ", _120=" + _120 +
            ", _130=" + _130 +
            ", _140=" + _140 +
            ", _150=" + _150 +
            ", _160=" + _160 +
            ", _170=" + _170 +
            ", _180=" + _180 +
            ", _190=" + _190 +
            ", _200=" + _200 +
            ", _210=" + _210 +
            '}';
    }
}
