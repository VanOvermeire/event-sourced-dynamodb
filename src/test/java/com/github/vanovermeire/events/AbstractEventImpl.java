package com.github.vanovermeire.events;

import lombok.Data;

@Data
public class AbstractEventImpl extends AbstractEvent {
    private String additionalValue;
}
