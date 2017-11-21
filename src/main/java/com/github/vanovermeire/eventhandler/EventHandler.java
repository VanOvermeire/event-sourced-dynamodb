package com.github.vanovermeire.eventhandler;

import com.github.vanovermeire.events.Event;

/**
 * This class handles the sending of events.
 */
public interface EventHandler {
    /**
     * Sends the event to the database, setting the id, format, etc. automatically
     */
    void sendEvent(Event event);

    /**
     * The event name is required, so this checks whether it is filled in.
     */
    default void checkEvent(Event event) {
        if(event.getName() == null) {
            throw new IllegalArgumentException("The name of an event cannot be null!");
        }
    }
}
