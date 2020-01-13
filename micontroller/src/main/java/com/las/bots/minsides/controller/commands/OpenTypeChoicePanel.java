package com.las.bots.minsides.controller.commands;

import com.las.bots.minsides.controller.MInsidesBot;
import com.las.bots.minsides.controller.commands.abstractCommands.Command;
import com.las.bots.minsides.controller.entity.uiCommands.CommandName;
import com.las.bots.minsides.controller.storage.SessionUpdate;
import com.las.bots.minsides.controller.tools.ChatUtil;
import com.las.bots.minsides.server.notes.service.NotesService;
import com.las.bots.minsides.shared.shared.entity.NoteType;
import com.las.bots.minsides.shared.shared.util.EntityUtil;
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
        Long chatId = update.getChatId();
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
