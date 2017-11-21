package com.github.vanovermeire.eventhandler;

import com.github.vanovermeire.events.Event;
import lombok.extern.log4j.Log4j2;

/**
 * Event handler implementation for AWS
 */
@Log4j2
public class AWSEventHandler implements EventHandler {

    // send JSON with the name and data to the API Gateway endpoint in the env var
    public void sendEvent(Event event) {
        log.debug("Received event {}", event);

    }
}