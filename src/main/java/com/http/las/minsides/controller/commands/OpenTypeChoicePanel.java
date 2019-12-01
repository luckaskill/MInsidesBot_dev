package com.http.las.minsides.controller.commands;

import com.http.las.minsides.controller.MInsidesBot;
import com.http.las.minsides.controller.commands.abstractCommands.Command;
import com.http.las.minsides.controller.entity.uiCommands.CommandName;
import com.http.las.minsides.controller.storage.SessionUpdate;
import com.http.las.minsides.controller.tools.ChatUtil;
import com.http.las.minsides.server.notes.service.NotesService;
import com.http.las.minsides.shared.entity.NoteType;
import com.http.las.minsides.shared.util.EntityUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Component(CommandName.NamesConstants.START_ADD_TYPE_TO_NOTE_COMMAND)
@AllArgsConstructor
public class OpenTypeChoicePanel implements Command {
    private NotesService service;
    private MInsidesBot source;
    private AddTypesToNoteByNumber addTypesToNote;

    @Override
    public void execute(SessionUpdate update) throws TelegramApiException {
        Long chatId = ChatUtil.getChatId(update);
        StringBuilder builder = new StringBuilder("Print new type name ");
        List<NoteType> types = service.getUserNoteTypes(chatId);
        if (!types.isEmpty()) {
            builder.append("or choose from existing: \n");
            try {
                List<String> typeNames = EntityUtil.readEntityValues(types, NoteType.TYPE_NAME_FIELDNAME);
                String numberedList = ChatUtil.buildSlashedNumList(typeNames);
                builder.append(numberedList);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                throw new TelegramApiException(e);
            }
        }
        ChatUtil.sendMsg(builder.toString(), update, source);
        update.setUserNotesTypes(types);
        update.setNextCommand(addTypesToNote);
    }
}
