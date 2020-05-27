package com.neotys.tricentis.MongoDB;

public class DatabaseException extends Exception
{
    // Parameterless Constructor
    public DatabaseException() {}

    // Constructor that accepts a message
    public DatabaseException(String message)
    {
        super(message);
    }
} 

