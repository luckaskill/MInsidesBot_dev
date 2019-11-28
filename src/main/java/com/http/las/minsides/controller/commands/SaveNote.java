package com.http.las.minsides.controller.commands;

import com.http.las.minsides.controller.commands.abstractCommands.Command;
import com.http.las.minsides.controller.MInsidesBot;
import com.http.las.minsides.controller.entity.uiCommands.Commands;
import com.http.las.minsides.controller.tools.ChatUtil;
import com.http.las.minsides.controller.storage.SessionUtil;
import com.http.las.minsides.shared.entity.Note;
import com.http.las.minsides.server.notes.service.NotesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.http.las.minsides.controller.storage.SessionUtil.getOrPutInCreationNote;

@Component(Commands.NamesConstants.SAVE_NOTE_COMMAND)
@AllArgsConstructor
public class SaveNote implements Command {
    private NotesService service;
    private ViewAll viewAll;
    private MInsidesBot source;

    @Override
    public void execute(Update update) throws TelegramApiException {
        Long chatId = ChatUtil.getChatId(update);
        Note note = getOrPutInCreationNote(update);
        note.setChatId(chatId);
        byte[] key = SessionUtil.getKey(update);
        service.saveNote(note, key);
        SessionUtil.removeFromCreation(update);
        ChatUtil.sendMsg("Nice, all done.", chatId, source);
        viewAll.execute(update);
    }
}
