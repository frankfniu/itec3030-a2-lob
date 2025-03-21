package ca.yorku.cmg.lob.stockexchange.events;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import ca.yorku.cmg.lob.security.Security;
import ca.yorku.cmg.lob.security.SecurityList;
import ca.yorku.cmg.lob.stockexchange.tradingagent.INewsObserver;

/**
 * A NewsBoard object generates and shares financial/economic events that affect specific securities.
 */
public class NewsBoard {

    // Events are queued ordered by time
    private PriorityQueue<Event> eventQueue = new PriorityQueue<>(Comparator.comparingLong(Event::getTime));

    private SecurityList securities;

    // List of registered observers (TradingAgents)
    private List<INewsObserver> observers = new ArrayList<>();

    // Allowed event values
    private static final Set<String> VALID_EVENTS = new HashSet<>(
        Arrays.asList("Good", "Bad")
    );

    public NewsBoard(SecurityList x) {
        this.securities = x;
    }

    /**
     * Register an observer (TradingAgent) to receive event notifications.
     * This should be called in the TradingAgent constructor.
     */
    public void addObserver(INewsObserver observer) {
        observers.add(observer);
    }

    /**
     * Load events from file. Format: [Time, Relevant Ticker, EventType], where EventType is one of "Good" or "Bad"
     * @param filePath The path of the file.
     */
    public void loadEvents(String filePath) {
        String line;
        String delimiter = ","; // Assuming the CSV is comma-separated

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(delimiter);

                if (values.length != 3) {
                    System.err.println("Invalid line format: " + line);
                    continue;
                }

                String time = values[0].trim();
                String ticker = values[1].trim();
                String event = values[2].trim();

                // Validate event type
                if (!VALID_EVENTS.contains(event)) {
                    System.err.println("Invalid event value: " + event + " in line: " + line);
                    continue;
                }

                Security s = securities.getSecurityByTicker(ticker);
                if (s == null) {
                    System.err.println("Unknown ticker: " + ticker + " in line: " + line);
                    continue;
                }

                Event eventObj = event.equals("Good") ? new GoodNews(Long.parseLong(time), s) :
                                                        new BadNews(Long.parseLong(time), s);

                eventQueue.add(eventObj);
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    /**
     * Get the event that occurred at the specified time.
     * @param time The time to check for events.
     * @return The event at the specified time, or null if no event occurred.
     */
    public Event getEventAt(long time) {
        PriorityQueue<Event> clonedQueue = new PriorityQueue<>(eventQueue);
        while (!clonedQueue.isEmpty()) {
            long next = clonedQueue.peek().getTime();
            if (time > next) {
                clonedQueue.poll();
            } else if (time == next) {
                return clonedQueue.poll();
            } else {
                return null;
            }
        }
        return null;
    }

    /**
     * Run the event queue and notify all registered TradingAgents.
     * This implements the **push model** (Observer Pattern).
     */
    public void runEventsList() {
        while (!eventQueue.isEmpty()) {
            Event event = eventQueue.poll(); // Retrieve the next event
            
            // Push event updates to all registered observers (TradingAgents)
            for (INewsObserver observer : observers) {
                observer.update(event);
            }
        }
    }
}
