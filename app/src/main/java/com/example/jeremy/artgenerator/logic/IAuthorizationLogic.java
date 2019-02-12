package com.example.jeremy.artgenerator.logic;

public interface IAuthorizationLogic {
    //service that check user data in db
    void runAuthorization(String email , String password);
    void clear();
}
