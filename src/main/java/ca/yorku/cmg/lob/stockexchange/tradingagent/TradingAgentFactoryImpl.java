package ca.yorku.cmg.lob.stockexchange.tradingagent;

import ca.yorku.cmg.lob.stockexchange.StockExchange;
import ca.yorku.cmg.lob.stockexchange.events.NewsBoard;
import ca.yorku.cmg.lob.trader.Trader;

/**
 * Concrete implementation of AbstractTradingAgentFactory.
 */
public class TradingAgentFactoryImpl extends AbstractTradingAgentFactory {
    @Override
    public TradingAgent createAgent(String type, String style, Trader t, StockExchange e, NewsBoard n) {
        ITradingStrategy strategy;

        // Assign trading strategy
        if ("Conservative".equalsIgnoreCase(style)) {
            strategy = new ConservativeStrategy();
        } else if ("Aggressive".equalsIgnoreCase(style)) {
            strategy = new AggressiveStrategy();
        } else {
            throw new IllegalArgumentException("Invalid trading strategy: " + style);
        }

        // Create trading agent based on type
        if ("Institutional".equalsIgnoreCase(type)) {
            return new TradingAgentInstitutional(t, e, n, strategy);
        } else if ("Retail".equalsIgnoreCase(type)) {
            return new TradingAgentRetail(t, e, n, strategy);
        } else {
            throw new IllegalArgumentException("Invalid trading agent type: " + type);
        }
    }
}
