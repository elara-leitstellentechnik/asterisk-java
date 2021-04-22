package org.asteriskjava.live;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HangupCauseTest
{
    @Test
    public void testGetByCode()
    {
        assertEquals("Valid enum for cause code 18", HangupCause.AST_CAUSE_18_NO_USER_RESPONSE, HangupCause.getByCode(18));
    }

    @Test
    public void testToString()
    {
        assertEquals("Valid toString for cause code 18", "NO_USER_RESPONSE", HangupCause.AST_CAUSE_18_NO_USER_RESPONSE.toString());
    }
}
