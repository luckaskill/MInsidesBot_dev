package com.http.las.minsides.controller.tools;

import com.http.las.minsides.controller.exception.UnknownErrorException;
import com.http.las.minsides.controller.exception.WrongInputException;
import com.http.las.minsides.shared.entity.DaoEntity;
import lombok.Cleanup;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.validation.constraints.NotNull;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

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

    public static String getInput(Update update) {
        Message message = update.getMessage();
        String input = message != null
                ? (message.hasText() ? message.getText() : null)
                : null;

        input = input == null
                ? (update.hasCallbackQuery() ? update.getCallbackQuery().getData() : null)
                : input;
        return input;
    }

    public static String getMessageText(Update update) {
        Message message = update.getMessage();
        String text = update.hasMessage()
                ? (message.hasText() ? message.getText() : null)
                : null;
        return text;
    }

    public static String getNotNullMessageText(Update update) {
        String text = getMessageText(update);
        if (text == null) {
            wrongInput();
        }
        return text;
    }

    public static Object readUpdate(Update update) {
        boolean hasMsg = update.hasMessage();
        Object key = null;
        if (hasMsg) {
            Message message = update.getMessage();
            key = message.hasText() ? message.getText()
                    : message.hasPhoto() ? message.getPhoto()
                    : message.hasAudio() ? message.getAudio()
                    : message.hasAnimation() ? message.getAnimation()
                    : null;
            if (key == null) {
                wrongInput();
            } else {
                return key;
            }
        } else {
            wrongInput();
        }
        return key;
    }

    public static byte[] createKeyFromObject(Object o) {
        byte[] result = null;
        try (ByteArrayOutputStream arrayOutput = new ByteArrayOutputStream();
             ObjectOutputStream objectOutput = new ObjectOutputStream(arrayOutput)) {
            objectOutput.writeObject(o);
            objectOutput.flush();
            result = arrayOutput.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            shitHappened();
        }
        return result;
    }

    public static void wrongInput() {
        throw new WrongInputException();
    }

    public static void shitHappened() {
        throw new UnknownErrorException();
    }

    public static SendMessage createSendMarkup(String message, Long chatId, InlineKeyboardMarkup markup) {
        return new SendMessage()
                .setText(message)
                .setChatId(chatId)
                .setReplyMarkup(markup);
    }

    public static SendMessage createSendMarkup(String message, Update update, InlineKeyboardMarkup markup) {
        Long chatId = getChatId(update);
        return createSendMarkup(message, chatId, markup);
    }

    public static String buildSlashedNumList(List<String> values) {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i <= values.size(); i++) {
            builder.append('/')
                    .append(i)
                    .append(" ")
                    .append(values.get(i - 1))
                    .append('\n');
        }
        return builder.toString();
    }

    public static <T> T getByCommandNumber(List<T> list, String commandNumber) {
        T result = null;
        try {
            if (!commandNumber.isEmpty()) {
                int number = defineInt(commandNumber);
                if (number > 0 && !list.isEmpty() && list.size() >= number) {
                    result = list.get(number - 1);
                } else {
                    ChatUtil.wrongInput();
                }
            } else {
                ChatUtil.wrongInput();
            }
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            ChatUtil.wrongInput();
        }
        return result;
    }


    private static Integer defineInt(String command) {
        int number;
        if (command.charAt(0) == '/') {
            number = Integer.parseInt(command.substring(1));
        } else {
            number = Integer.parseInt(command);
        }
        return number;
    }

}
