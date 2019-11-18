package com.http.las.minsides.controller.commands;

import com.http.las.minsides.controller.Command;
import com.http.las.minsides.controller.MInsidesBot;
import com.http.las.minsides.controller.storage.SessionUtil;
import com.http.las.minsides.controller.tools.ChatUtil;
import com.http.las.minsides.shared.entity.NoteType;
import com.http.las.minsides.server.notes.service.NotesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
@AllArgsConstructor
public class OpenTypeChoicePanel implements Command {
    private NotesService service;
    private MInsidesBot source;
    private AddTypesToNote addTypesToNote;

    @Override
    public void execute(Update update) throws TelegramApiException {
        Long chatId = ChatUtil.getChatId(update);
        StringBuilder builder = new StringBuilder("Print new type name");
        List<NoteType> types = service.getUserNoteTypes(chatId);
        if (!types.isEmpty()) {
            builder.append(" or choose from existing: \n");
            int i = 1;
            for (NoteType type : types) {
                builder.append(i++).append(". ").append(type.getTypeName()).append("\n");
            }
        }
        ChatUtil.sendMsg(builder.toString(), update, source);
        SessionUtil.setUserNotesTypes(update, types);
        SessionUtil.setNextCommand(update, addTypesToNote);
    }
}
