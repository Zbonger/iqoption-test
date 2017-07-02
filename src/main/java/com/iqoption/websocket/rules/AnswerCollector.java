package com.iqoption.websocket.rules;

import com.google.gson.Gson;
import com.iqoption.websocket.beans.Message;

import java.util.function.Predicate;

public class AnswerCollector {

    private Message answer;
    private boolean filteredAnswerHandled = false;

    public Message getAnswer() {
        return answer;
    }

    public void setAnswer(Message answer) {
        this.answer = answer;
    }

    public boolean isFilteredAnswerHandled() {
        return filteredAnswerHandled;
    }

    public void setFilteredAnswerHandled(boolean filteredAnswerHandled) {
        this.filteredAnswerHandled = filteredAnswerHandled;
    }

    public void collectWithCondition(String message, Predicate<Message> cond){
        Message answer = new Gson().fromJson(message, Message.class);
        if (cond.test(answer)) {
            setAnswer(answer);
            setFilteredAnswerHandled(true);
        }
    }
}
