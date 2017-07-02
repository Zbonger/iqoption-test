package com.iqoption.data.factory;

import com.iqoption.api.beans.login.Credentials;

public class CredentialsProvider {

    public static Credentials registeredUser(){
        return new Credentials("nemanovich@gmail.com", "123456");
    }
}
