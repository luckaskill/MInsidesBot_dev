package com.http.las.minsides.controller.tools;

import com.http.las.minsides.controller.exception.UnknownErrorException;
import com.http.las.minsides.controller.exception.WrongInputException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.validation.constraints.NotNull;

public class ChatUtil {

    public static void sendMsg(@NotNull String msg, @NotNull Long chatId, @NotNull TelegramLongPollingBot source) throws TelegramApiException {
        SendMessage message = new SendMessage(chatId, msg);
        source.execute(message);
    }

    public static void sendMsg(@NotNull String msg, @NotNull Update update, @NotNull TelegramLongPollingBot source) throws TelegramApiException {
        Long chatId = getChatId(update);
        sendMsg(msg, chatId, source);
    }

    public static Long getChatId(Update update) {
        Long id = update.hasMessage() ?
                update.getMessage().getChatId() : update.hasCallbackQuery() ?
                update.getCallbackQuery().getMessage().getChatId() : null;
        if (id == null) {
            shitHappened();
        }
        return id;
    }

    public static String getMessage(Update update) {
        Message message = update.getMessage();
        return update.hasMessage()
                ? (message.hasText() ? message.getText() : null)
                : null;
    }

    public static void wrongInput() {
        throw new WrongInputException();
    }

    public static void shitHappened() {
        throw new UnknownErrorException();
    }

}
