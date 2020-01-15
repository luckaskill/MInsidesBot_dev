package com.las.bots.minsides.controller.commands.abstractCommands;

import com.las.bots.minsides.controller.storage.SessionUpdate;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface Command {
    void execute(SessionUpdate update) throws TelegramApiException;

    default boolean isRepeatable() {
        return false;
    }

}
