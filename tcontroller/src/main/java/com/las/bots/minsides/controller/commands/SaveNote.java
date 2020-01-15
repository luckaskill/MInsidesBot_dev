package com.las.bots.minsides.controller.commands;

import com.las.bots.minsides.controller.MInsidesBot;
import com.las.bots.minsides.controller.entity.uiCommands.CommandName;
import com.las.bots.minsides.controller.storage.SessionUpdate;
import com.las.bots.minsides.controller.tools.ChatUtil;
import com.las.bots.minsides.controller.commands.abstractCommands.Command;
import com.las.bots.minsides.server.notes.service.NotesService;
import com.las.bots.minsides.shared.shared.entity.Note;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component(CommandName.NamesConstants.SAVE_NOTE_COMMAND)
@AllArgsConstructor
public class SaveNote implements Command {
    private NotesService service;
    private ViewAll viewAll;
    private MInsidesBot source;

    @Override
    public void execute(SessionUpdate update) throws TelegramApiException {
        Long chatId = update.getChatId();
        Note note = update.getOrPutInCreationNote();
        note.setChatId(chatId);
        byte[] key = update.getKey();
        service.saveNote(note, key);
        update.removeFromCreation();
        ChatUtil.sendMsg("Nice, all done.", chatId, source);
        viewAll.execute(update);
    }
}
