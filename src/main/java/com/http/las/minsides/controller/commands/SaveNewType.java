package com.http.las.minsides.controller.commands;

import com.http.las.minsides.controller.commands.abstractCommands.Command;
import com.http.las.minsides.controller.entity.uiCommands.CommandName;
import com.http.las.minsides.controller.storage.SessionUpdate;
import com.http.las.minsides.server.notes.service.NotesService;
import com.http.las.minsides.shared.entity.NoteType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component(CommandName.NamesConstants.SAVE_NEW_TYPE)
@AllArgsConstructor
public class SaveNewType implements Command {
    private NotesService service;

    @Override
    public void execute(SessionUpdate update) throws TelegramApiException {
        NoteType type = update.getNoteTypeToSave();
        if (type != null) {
            service.saveNewNoteType(type);
        }
    }
}
