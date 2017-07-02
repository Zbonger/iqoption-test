package com.iqoption.api.register;

import com.iqoption.api.IqOptionApi;
import com.iqoption.api.beans.register.RegistrationInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import static io.restassured.RestAssured.withArgs;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;

@RunWith(Parameterized.class)
public class UserRegisterUnsuccessfulTest {

    @Parameter
    public RegistrationInfo regInfo;

    @Parameter(1)
    public String field;

    @Parameter(2)
    public String message;

    @Parameters(name = "validate {1}")
    public static Object[] registrationInfoProvider() {
        return new Object[][]{
                {new RegistrationInfo("nemanovich@gmail.com", "123456", "Ivan", "Drago"), "", "Вы уже зарегистрированы"},
                {new RegistrationInfo("nemanovich", "123456", "Ivan", "Drago"), "email", "Email неправильный"},
                {new RegistrationInfo("new.mail@m.com", "12345", "Ivan", "Drago"), "password", "Неправильная длина пароля"},
                {new RegistrationInfo("new.mail@m.com", "123456", "", "Drago"), "first_name", "Имя обязательно для заполнения"},
        };
    }

    @Test
    public void testResponseMessage() {
        IqOptionApi.register(regInfo)
                .then()
                .body("isSuccessful", equalTo(false))
                .body("message.%s", withArgs(field), hasItem(message));
    }
}
