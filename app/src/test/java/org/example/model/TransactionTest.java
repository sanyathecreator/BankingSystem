package org.example.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TransactionTest {
    public Transaction transaction;

    @Before
    public void setup() {
        transaction = new Transaction("john_doe", "johnny_silverhand", 1000.0);
    }

    @Test
    public void testTransactionCreationWithParameters() {
        assertEquals("john_doe", transaction.getSenderUsername());
        assertEquals("johnny_silverhand", transaction.getReceiverUsername());
        assertEquals(1000.0, transaction.getAmount(), 0.001);
    }
}
