package com.http.las.minsides.controller.commands;

import com.http.las.minsides.controller.MInsidesBot;
import com.http.las.minsides.controller.commands.abstractCommands.Command;
import com.http.las.minsides.controller.entity.uiCommands.CommandName;
import com.http.las.minsides.controller.storage.SessionUpdate;
import com.http.las.minsides.controller.tools.ChatUtil;
import com.http.las.minsides.server.notes.service.NotesService;
import com.http.las.minsides.shared.entity.NoteType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component(CommandName.NamesConstants.START_ADD_TYPE_TO_NOTE_COMMAND)
@AllArgsConstructor
public class OpenTypeChoicePanel implements Command {
    private NotesService service;
    private MInsidesBot source;
    private AddTypesToNote addTypesToNote;

    @Override
    public void execute(SessionUpdate update) throws TelegramApiException {
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
        update.setUserNotesTypes(types);
        update.setNextCommand(addTypesToNote);
    }
}
