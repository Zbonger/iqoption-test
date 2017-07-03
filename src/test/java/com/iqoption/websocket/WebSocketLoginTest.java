package com.iqoption.websocket;

import com.iqoption.api.IqOptionApi;
import com.iqoption.api.beans.login.Credentials;
import com.iqoption.api.beans.profile.Profile;
import com.iqoption.websocket.beans.Message;
import com.iqoption.websocket.rules.AnswerCollector;
import com.iqoption.websocket.rules.WebSocketClientRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.iqoption.data.factory.CredentialsProvider.registeredUser;
import static com.iqoption.websocket.matchers.IsAnswerHandledMatcher.isAnswerHandled;
import static com.iqoption.websocket.rules.WebSocketClientRule.defaultWebSocketClient;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;
import static ru.yandex.qatools.matchers.decorators.MatcherDecorators.should;
import static ru.yandex.qatools.matchers.decorators.MatcherDecorators.timeoutHasExpired;


public class WebSocketLoginTest {
    private static final Credentials credentials = registeredUser();

    @Rule
    public final WebSocketClientRule client = defaultWebSocketClient();
    private AnswerCollector collector = new AnswerCollector();

    private final String ssid = IqOptionApi.login(credentials).cookie("ssid");

    private Profile expectedProfile;

    @Before
    public void getProfile() {
        expectedProfile = IqOptionApi.getProfile(ssid).jsonPath().getObject("result", Profile.class);
        assumeThat("из api/getprofile передан не пустой ответ", expectedProfile.getEmail(), not(nullValue()));
    }

    @Test
    public void testResponseProfile() {
        client.setMessageHandler(message ->
                collector.collectWithCondition(message, answer -> answer.getName().equals("profile"))
        );
        client.sendMessage(new Message("ssid", ssid));

        assertThat(collector, should(isAnswerHandled()).whileWaitingUntil(timeoutHasExpired()));
        assertThat(collector.getAnswer().getMessage(Profile.class), sameBeanAs(expectedProfile));
    }
}
