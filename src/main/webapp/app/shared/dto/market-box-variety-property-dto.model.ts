import {Variety} from '../../entities/variety/variety.model';
import {Length} from '../constants/length.constants';
import {MarketBox} from '../../entities/market-box/market-box.model';
import {Market} from '../../entities/market/market.model';

export class MarketBoxVarietyPropertyDto {
    market: Market;
    variety: Variety;
    marketBox: MarketBox;
    capacitiesOnLength: Map<[Length, number], number>;
    capacitiesOnLengthObj: any;

    constructor()
    constructor(json: MarketBoxVarietyPropertyDtoResponse)
    constructor(json?: MarketBoxVarietyPropertyDtoResponse) {
        if (json) {
            this.variety = json.variety;
            this.marketBox = json.marketBox;
            this.market = json.market;

            this.capacitiesOnLength = new Map<[Length, number], number>();
            Object.keys(json.capacitiesOnLength).forEach((rawKey) => {
                const parts = rawKey.split('=');
                const length = Length[parts[0]];
                const pair: [Length, number] = [length, Number(parts[1])];
                this.addParameter(pair, json.capacitiesOnLength[rawKey]);
            });
        }
    }

    addParameter(key: [Length, number], value: number) {
        this.capacitiesOnLength.set(key, value);
    }
}

export interface MarketBoxVarietyPropertyDtoResponse {
    market: Market;
    variety: Variety;
    marketBox: MarketBox;
    capacitiesOnLength: [[Length, number], number];
}
