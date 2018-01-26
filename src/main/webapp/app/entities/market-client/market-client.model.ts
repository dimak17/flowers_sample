import { Market } from '../market';
import {Client} from '../client/client.model';
export class MarketClient {
    constructor(
        public id?: number,
        public market?: Market,
        public client?: Client,
    ) {
    }
}
