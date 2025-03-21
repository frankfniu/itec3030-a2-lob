package ca.yorku.cmg.lob.stockexchange.tradingagent;

import ca.yorku.cmg.lob.stockexchange.StockExchange;
import ca.yorku.cmg.lob.stockexchange.events.Event;
import ca.yorku.cmg.lob.stockexchange.events.NewsBoard;
import ca.yorku.cmg.lob.trader.Trader;

/**
 * An trading agent that receives news and reacts by submitting ask or bid orders.
 */
public abstract class TradingAgent implements INewsObserver {  // Implements Observer Pattern
	protected Trader t;
	protected StockExchange exc;
	protected NewsBoard news;
	
	/**
	 * Constructor
	 * @param t The {@linkplain Trader} object associated with the agent.
	 * @param e The {@linkplain StockExchange} object at which the agent has an account and trades in. 
	 * @param n The {@linkplain NewsBoard} object that generates news events.
	 */
	public TradingAgent(Trader t, StockExchange e, NewsBoard n) {
		this.t=t;
		this.exc = e;
		this.news = n;

	        // Register as an observer (Push Model)
        	this.news.addObserver(this);
	}



	 /**
	     * Observer method: Called when an event occurs.
	     * @param e The Event received from NewsBoard.
    	 */
   	 @Override
  	  public void update(Event e) {
        	examineEvent(e); // Process the event when notified
   	 }


	/**
	 * Examine if an event is relevant for the Agent, i.e., if the Agent has a position on it.
	 * @param e The {@linkplain Event} object in question
	 */
	private void examineEvent(Event e) {
		int positionInSecurity = exc.getAccounts().getTraderAccount(t).getPosition(e.getSecrity().getTicker());
		if (positionInSecurity > 0) {
			actOnEvent(e,positionInSecurity,exc.getPrice(e.getSecrity().getTicker()));
		}
	}


	
	/**
	 * Act in response to a news {@linkplain Event}. Exact reaction strategy to be implemented by specialized agents.
	 * @param e The {@linkplain Event} in question
	 * @param pos The position (number of units) of the trader to the ticker that is mentioned in the Event.
	 * @param price The current price of the relevant ticker. 
	 */
	protected abstract void actOnEvent(Event e, int pos, int price);
	
	
	

}
