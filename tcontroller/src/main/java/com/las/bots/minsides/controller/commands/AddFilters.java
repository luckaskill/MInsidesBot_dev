package com.las.bots.minsides.controller.commands;

import com.las.bots.minsides.controller.MInsidesBot;
import com.las.bots.minsides.controller.entity.uiCommands.CommandName;
import com.las.bots.minsides.controller.storage.SessionUpdate;
import com.las.bots.minsides.controller.tools.ChatUtil;
import com.las.bots.minsides.controller.commands.abstractCommands.Command;
import com.las.bots.minsides.server.notes.service.NotesService;
import com.las.bots.minsides.shared.shared.entity.NoteType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component(CommandName.NamesConstants.ADD_FILTERS)
@AllArgsConstructor
public class AddFilters implements Command {
    private NotesService service;
    private MInsidesBot source;

    @Override
    public void execute(SessionUpdate update) throws TelegramApiException {
        Long chatId = update.getChatId();
        List<NoteType> noteTypes = service.getUserNoteTypes(chatId);

        int i = 1;
        StringBuilder builder = new StringBuilder();
        for (NoteType type : noteTypes) {
            builder.append('/')
                    .append(i++)
                    .append(" ")
                    .append(type.getTypeName())
                    .append("\n");
        }
        ChatUtil.sendMsg("Choose type(s)", update, source);
        ChatUtil.sendMsg(builder.toString(), update, source);
        update.setUserNotesTypes(noteTypes);
    }
}
