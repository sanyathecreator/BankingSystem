package org.example.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CustomerTest {

    private Customer customer;

    @Before
    public void setUp() {
        customer = new Customer("john_doe", "password123", 1000.0);
    }

    @Test
    public void testCustomerCreationWithParameters() {
        assertEquals("john_doe", customer.getUsername());
        assertEquals("password123", customer.getPassword());
        assertEquals(1000.0, customer.getBalance(), 0.001);
    }

    @Test
    public void testCustomerDefaultConstructor() {
        Customer defaultCustomer = new Customer();
        assertEquals("Unknown", defaultCustomer.getUsername());
        assertEquals("1234", defaultCustomer.getPassword());
        assertEquals(0.0, defaultCustomer.getBalance(), 0.001);
    }

    @Test
    public void testSetBalance() {
        customer.setBalance(432.1);
        assertEquals(432.1, customer.getBalance(), 0.001);
    }

    @Test
    public void testSetNegativeBalance() {
        customer.setBalance(-100.0);
        assertEquals(-100.0, customer.getBalance(), 0.001);
    }
}
