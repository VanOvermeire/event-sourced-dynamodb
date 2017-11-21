package com.github.vanovermeire.eventhandler;


import com.github.vanovermeire.events.Event;
import lombok.ToString;

@ToString
public class TestEvent implements Event {

    private String name = getName();

    @Override
    public String getName() {
        return name;
    }

    public void setName(String n) {
        name = n;
    }
}
