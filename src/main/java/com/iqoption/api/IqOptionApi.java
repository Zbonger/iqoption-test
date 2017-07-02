package com.iqoption.api;

import com.iqoption.api.beans.login.Credentials;
import com.iqoption.api.beans.register.RegistrationInfo;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static com.iqoption.api.utils.BeanСonverter.toParams;
import static io.restassured.RestAssured.given;
import static io.restassured.config.RestAssuredConfig.config;
import static io.restassured.mapper.ObjectMapperType.GSON;

public class IqOptionApi {
    public static RequestSpecification defaultSpec() {
        return new RequestSpecBuilder()
                .setBaseUri("https://iqoption.com/api")
                .addCookie("lang", "ru_RU")
                .setConfig(config().objectMapperConfig(new ObjectMapperConfig(GSON)))
                .build();
    }

    public static Response login(Credentials credentials) {
        return given(defaultSpec()).params(toParams(credentials)).post("login/v2");
    }

    public static Response getProfile(String ssid) {
        return given(defaultSpec()).cookie("ssid", ssid).get("getprofile");
    }

    public static Response register(RegistrationInfo regInfo) {
        return  given(defaultSpec()).params(toParams(regInfo)).post("register");
    }
}
