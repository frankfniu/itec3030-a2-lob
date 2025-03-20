package ca.yorku.cmg.lob.stockexchange.tradingagent;

import ca.yorku.cmg.lob.stockexchange.StockExchange;
import ca.yorku.cmg.lob.stockexchange.events.NewsBoard;
import ca.yorku.cmg.lob.trader.Trader;

/**
 * Institutional trading agent that uses a strategy to trade.
 */
public class TradingAgentInstitutional extends TradingAgent {
    public TradingAgentInstitutional(Trader trader, StockExchange exchange, NewsBoard newsBoard, ITradingStrategy strategy) {
        super(trader, exchange, newsBoard, strategy);
    }
}
