package ca.yorku.cmg.lob.stockexchange.tradingagent;

import ca.yorku.cmg.lob.stockexchange.StockExchange;
import ca.yorku.cmg.lob.stockexchange.events.NewsBoard;
import ca.yorku.cmg.lob.trader.Trader;

/**
 * Retail trading agent that uses a strategy to trade.
 */
public class TradingAgentRetail extends TradingAgent {
    public TradingAgentRetail(Trader trader, StockExchange exchange, NewsBoard newsBoard, ITradingStrategy strategy) {
        super(trader, exchange, newsBoard, strategy);
    }
}
