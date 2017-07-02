package com.iqoption.websocket.matchers;

import com.iqoption.websocket.rules.AnswerCollector;
import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;

import static org.hamcrest.core.Is.is;

public class IsAnswerHandledMatcher extends FeatureMatcher<AnswerCollector, Boolean> {
    public IsAnswerHandledMatcher() {
        super(is(true), "answer", "handled answer from web socket");
    }

    @Override
    protected Boolean featureValueOf(AnswerCollector collector) {
        return collector.isFilteredAnswerHandled();
    }

    @Factory
    public static IsAnswerHandledMatcher isAnswerHandled() {
        return new IsAnswerHandledMatcher();
    }
}
