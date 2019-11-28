package com.http.las.minsides.controller.tools;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.ChosenInlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.payments.PreCheckoutQuery;
import org.telegram.telegrambots.meta.api.objects.payments.ShippingQuery;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;

public class CustomUpdate extends Update {
    private Update update;
    private Long chatId;
    private String input;

    public CustomUpdate(Update update) {
        this.update = update;
        chatId = ChatUtil.getChatId(update);
        input = ChatUtil.getInput(update);
    }

    @Override
    public Integer getUpdateId() {
        return update.getUpdateId();
    }

    @Override
    public Message getMessage() {
        return update.getMessage();
    }

    @Override
    public InlineQuery getInlineQuery() {
        return update.getInlineQuery();
    }

    @Override
    public ChosenInlineQuery getChosenInlineQuery() {
        return update.getChosenInlineQuery();
    }

    @Override
    public CallbackQuery getCallbackQuery() {
        return update.getCallbackQuery();
    }

    @Override
    public Message getEditedMessage() {
        return update.getEditedMessage();
    }

    @Override
    public Message getChannelPost() {
        return update.getChannelPost();
    }

    @Override
    public Message getEditedChannelPost() {
        return update.getEditedChannelPost();
    }

    @Override
    public ShippingQuery getShippingQuery() {
        return update.getShippingQuery();
    }

    @Override
    public PreCheckoutQuery getPreCheckoutQuery() {
        return update.getPreCheckoutQuery();
    }

    @Override
    public Poll getPoll() {
        return update.getPoll();
    }

    @Override
    public boolean hasMessage() {
        return update.hasMessage();
    }

    @Override
    public boolean hasInlineQuery() {
        return update.hasInlineQuery();
    }

    @Override
    public boolean hasChosenInlineQuery() {
        return update.hasChosenInlineQuery();
    }

    @Override
    public boolean hasCallbackQuery() {
        return update.hasCallbackQuery();
    }

    @Override
    public boolean hasEditedMessage() {
        return update.hasEditedMessage();
    }

    @Override
    public boolean hasChannelPost() {
        return update.hasChannelPost();
    }

    @Override
    public boolean hasEditedChannelPost() {
        return update.hasEditedChannelPost();
    }

    @Override
    public boolean hasShippingQuery() {
        return update.hasShippingQuery();
    }

    @Override
    public boolean hasPreCheckoutQuery() {
        return update.hasPreCheckoutQuery();
    }

    @Override
    public boolean hasPoll() {
        return update.hasPoll();
    }

    @Override
    public String toString() {
        return update.toString();
    }
}
