package com.github.vanovermeire.events;

/**
 * All events have to implement this interface. Every event needs a name.
 * Ideally, events should only contain Strings and primitives.
 * Ideally, an event should be immutable.
 */
public interface Event {
    String getName();
}
