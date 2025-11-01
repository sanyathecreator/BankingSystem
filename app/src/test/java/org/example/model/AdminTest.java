package org.example.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AdminTest {
    private Admin admin;

    @Before
    public void setUp() {
        admin = new Admin("admin", "admin");
    }

    @Test
    public void testAdminCreationWithParameters() {
        assertEquals("admin", admin.getUsername());
        assertEquals("admin", admin.getPassword());
    }
}
