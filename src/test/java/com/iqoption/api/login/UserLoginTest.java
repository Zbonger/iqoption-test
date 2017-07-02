package com.iqoption.api.login;

import com.iqoption.api.IqOptionApi;
import com.iqoption.api.beans.login.Credentials;
import com.iqoption.api.beans.profile.Profile;
import io.restassured.response.Response;
import org.junit.Test;

import static com.iqoption.api.IqOptionApi.getProfile;
import static com.iqoption.api.IqOptionApi.login;
import static com.iqoption.data.factory.CredentialsProvider.registeredUser;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.text.IsEmptyString.isEmptyString;
import static org.junit.Assert.assertThat;


public class UserLoginTest {

    private final Credentials credentials = registeredUser();

    @Test
    public void testLoginIsSuccessful() {
        login(credentials)
                .then()
                .body("isSuccessful", is(true))
                .cookie("ssid", not(isEmptyString()));
    }

    @Test
    public void testResponseProfile() {
        Response loginResponse = IqOptionApi.login(credentials);
        assertThat(
                loginResponse.getBody().jsonPath().getObject("result", Profile.class),
                sameBeanAs(getProfile(loginResponse.cookie("ssid")).jsonPath().getObject("result", Profile.class))
        );
    }
}
