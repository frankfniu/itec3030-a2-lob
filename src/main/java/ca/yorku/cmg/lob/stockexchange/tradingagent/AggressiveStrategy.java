package ca.yorku.cmg.lob.stockexchange.tradingagent;

import ca.yorku.cmg.lob.stockexchange.events.Event;

/**
 * Aggressive trading strategy: reacts strongly to events.
 */
public class AggressiveStrategy implements ITradingStrategy {
    @Override
    public void actOnEvent(Event e, int pos, int price) {
        // Aggressive traders buy/sell in large quantities
        System.out.println("Aggressive strategy reacting to event: " + e);
        int quantity = Math.max(1, pos / 2); // Buy/sell at 50% of position
        if (e.isGoodNews()) {
            System.out.println("Buying " + quantity + " shares at price " + price);
        } else {
            System.out.println("Selling " + quantity + " shares at price " + price);
        }
    }
}
