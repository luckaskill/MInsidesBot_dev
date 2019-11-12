package com.http.las.minsides.controller.tools;

import com.http.las.minsides.controller.storage.UserSessionInfo;
import com.http.las.minsides.entity.Note;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.validation.constraints.NotNull;

import static com.http.las.minsides.controller.tools.ChatUtil.getChatId;

public class StockUtil {
    public static Note getOrPutInCreationNote(Long chatId) {
        Note note = UserSessionInfo.NOTES_IN_CREATION.get(chatId);
        if (note == null) {
            UserSessionInfo.NOTES_IN_CREATION.put(chatId, (note = new Note()));
        }
        return note;
    }

    public static void removeFromCreation(Long chatId) {
        UserSessionInfo.NOTES_IN_CREATION.put(chatId, null);
    }

    public static Note getCurrentEditedNote(Update update) {
        Long chatId = getChatId(update);
        Note note = getOrPutInCreationNote(chatId);
        return note;
    }

    public static Note getCurrentEditedNote(@NotNull Long chatId) {
        Note note = getOrPutInCreationNote(chatId);
        return note;
    }
}
