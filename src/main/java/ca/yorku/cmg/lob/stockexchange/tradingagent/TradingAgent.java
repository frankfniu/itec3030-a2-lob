package ca.yorku.cmg.lob.stockexchange.tradingagent;

import ca.yorku.cmg.lob.stockexchange.StockExchange;
import ca.yorku.cmg.lob.stockexchange.events.Event;
import ca.yorku.cmg.lob.stockexchange.events.NewsBoard;
import ca.yorku.cmg.lob.trader.Trader;

/**
 * Abstract TradingAgent that uses a trading strategy.
 */
public abstract class TradingAgent implements INewsObserver {
    protected Trader trader;
    protected StockExchange exchange;
    protected NewsBoard newsBoard;
    protected ITradingStrategy strategy;

    public TradingAgent(Trader trader, StockExchange exchange, NewsBoard newsBoard, ITradingStrategy strategy) {
        this.trader = trader;
        this.exchange = exchange;
        this.newsBoard = newsBoard;
        this.strategy = strategy;
        this.newsBoard.registerObserver(this);
    }

    @Override
    public void update(Event event) {
        strategy.actOnEvent(event, trader.getPosition(), trader.getPrice());
    }
}
