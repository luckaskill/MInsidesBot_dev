package com.http.las.minsides.controller.commands.abstractCommands;

import com.http.las.minsides.controller.storage.SessionUpdate;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface Command {
    void execute(SessionUpdate update) throws TelegramApiException;

    default boolean isRepeatable() {
        return false;
    }

}
