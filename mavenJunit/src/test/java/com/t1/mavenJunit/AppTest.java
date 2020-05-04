package com.t1.mavenJunit;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest 
{
    @Test
    public void helloWorld()
    {
        assertEquals( "Hello, world!", App.getHelloWorld() );
    }

    @Test
    public void getNumber()
    {
        assertEquals( 10, App.getNumber() );
    }

    @Test
    public void getArray()
    {
        int[] array = new int[]{1, 2, 3};
        assertArrayEquals( array, App.getArray() );
    }

    @Test
    public void getCondition()
    {
        assertTrue( App.getBool() == true );
    }
}

