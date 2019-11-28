package com.http.las.minsides.controller.commands;

import com.http.las.minsides.controller.commands.abstractCommands.Command;
import com.http.las.minsides.controller.entity.uiCommands.Commands;
import com.http.las.minsides.controller.storage.SessionUtil;
import com.http.las.minsides.shared.entity.NoteType;
import com.http.las.minsides.server.notes.service.NotesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component(Commands.NamesConstants.SAVE_NEW_TYPE)
@AllArgsConstructor
public class SaveNewType implements Command {
    private NotesService service;

    @Override
    public void execute(Update update) throws TelegramApiException {
        NoteType type = SessionUtil.getNoteTypeToSave(update);
        if (type != null) {
            service.saveNewNoteType(type);
        }
    }
}
