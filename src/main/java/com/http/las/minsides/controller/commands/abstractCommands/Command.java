package com.http.las.minsides.controller.commands.abstractCommands;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface Command {
    void execute(Update update) throws TelegramApiException;

    default boolean isRepeatable() {
        return false;
    }

}
