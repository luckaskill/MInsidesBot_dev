package com.http.las.minsides.controller.commands;

import com.http.las.minsides.controller.Command;
import com.http.las.minsides.controller.tools.ChatUtil;
import com.http.las.minsides.controller.storage.SessionUtil;
import com.http.las.minsides.entity.NoteType;
import com.http.las.minsides.server.notes.service.NotesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class SaveNewType implements Command {
    private NotesService service;

    @Override
    public void execute(Update update) {
        NoteType type = SessionUtil.getNoteTypeToSave(update);
        if (type != null) {
            service.saveNewNoteType(type);
        }
    }
}
