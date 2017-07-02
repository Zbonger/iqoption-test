package com.iqoption.api.login;

import com.iqoption.api.IqOptionApi;
import com.iqoption.api.beans.login.Credentials;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import static io.restassured.RestAssured.withArgs;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;

@RunWith(Parameterized.class)
public class UserLoginUnsuccessfulTest {

    @Parameter
    public Credentials credentials;

    @Parameter(1)
    public String field;

    @Parameter(2)
    public String message;

    @Parameters(name = "validate {1}")
    public static Object[] registrationInfoProvider() {
        return new Object[][]{
                {new Credentials("nemanovich@gmail.com", "1234567"), "", "Неправильный логин или пароль"},
                {new Credentials("nemanovich", "123456"), "email", "Email \"nemanovich\" введен неверно"},
        };
    }

    @Test
    public void testResponseMessage() {
        IqOptionApi.login(credentials)
                .then()
                .body("isSuccessful", equalTo(false))
                .body("message.%s", withArgs(field), hasItem(message));
    }

}
