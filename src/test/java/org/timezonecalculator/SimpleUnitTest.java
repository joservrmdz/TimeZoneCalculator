package org.timezonecalculator;

import org.junit.Test;
import ratpack.test.MainClassApplicationUnderTest;

import static org.junit.Assert.assertEquals;

public class SimpleUnitTest {

    private MainClassApplicationUnderTest mainClassApplicationUnderTest = new MainClassApplicationUnderTest(Server.class);

    @Test
    public void start_application() throws Exception {
        int status = mainClassApplicationUnderTest.getHttpClient().get().getStatusCode();
        assertEquals(status,200);
    }

}