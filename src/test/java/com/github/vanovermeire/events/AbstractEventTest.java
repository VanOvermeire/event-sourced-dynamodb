package com.github.vanovermeire.events;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AbstractEventTest {

    @Test
    public void testAbstractEventImplHasNameOfClass() {
        AbstractEventImpl abstractEvent = new AbstractEventImpl();

        assertEquals(abstractEvent.getClass().getCanonicalName(), abstractEvent.getName());
    }
}
