package com.example.BusInventoryService.exception;

public class BusNotFoundException extends RuntimeException{

    public BusNotFoundException(String message)
    {
        super(message);
    }
}
