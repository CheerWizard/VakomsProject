package com.example.jeremy.artgenerator.logic;

public interface IRegistrationLogic {
    //service that register user data in db
    void runRegistration(String name , String email , String password);
    void clear();
}
