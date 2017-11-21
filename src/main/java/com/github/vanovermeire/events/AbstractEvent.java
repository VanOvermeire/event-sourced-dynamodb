package com.github.vanovermeire.events;

/**
 * Convenience implementation of Event. Sets the name to match the canonical class name. for setting the name to match the class name.
 * It also offers a setter for changing the name if required
 */
public abstract class AbstractEvent implements Event {

    private String eventName = this.getClass().getCanonicalName();

    @Override
    public String getName() {
        return eventName;
    }

    public void setName(String name) {
        eventName = name;
    }

    @Override
    public String toString() {
        return eventName;
    }
}
