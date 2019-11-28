package com.http.las.minsides.controller.commands;

import com.http.las.minsides.controller.commands.abstractCommands.Command;
import com.http.las.minsides.controller.MInsidesBot;
import com.http.las.minsides.controller.entity.uiCommands.CommandContainer;
import com.http.las.minsides.controller.tools.ChatUtil;
import com.http.las.minsides.controller.storage.SessionUtil;
import com.http.las.minsides.shared.entity.NoteType;
import com.http.las.minsides.server.notes.service.NotesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component(CommandContainer.NamesConstants.ADD_FILTERS)
@AllArgsConstructor
public class AddFilters implements Command {
    private NotesService service;
    private MInsidesBot source;

    @Override
    public void execute(Update update) throws TelegramApiException {
        Long chatId = ChatUtil.getChatId(update);
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
        SessionUtil.setUserNotesTypes(update, noteTypes);
    }
}
