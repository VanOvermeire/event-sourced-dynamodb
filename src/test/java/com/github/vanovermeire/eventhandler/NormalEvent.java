package com.github.vanovermeire.eventhandler;

import com.github.vanovermeire.events.AbstractEvent;
import lombok.Data;

@Data
public class NormalEvent extends AbstractEvent {

    private String description;
    private int month;
}
