package com.http.las.minsides.controller.commands;

import com.http.las.minsides.controller.commands.abstractCommands.Command;
import com.http.las.minsides.controller.MInsidesBot;
import com.http.las.minsides.controller.storage.SessionUtil;
import com.http.las.minsides.controller.tools.ChatUtil;
import com.http.las.minsides.shared.entity.Note;
import com.http.las.minsides.shared.entity.NoteType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

import static com.http.las.minsides.controller.storage.SessionUtil.getOrPutInCreationNote;

@Component
@AllArgsConstructor
public class AddNoteType implements Command {
    private ShowAddNotePanel showAddNotePanel;
    private MInsidesBot source;

    @Override
    public void execute(Update update) throws TelegramApiException {
        Long chatId = ChatUtil.getChatId(update);
        NoteType type = SessionUtil.getNoteTypeToSave(update);
        Note note = getOrPutInCreationNote(update);
        List<NoteType> noteTypes = note.getNoteTypes();
        noteTypes.add(type);
        ChatUtil.sendMsg("Nice, now u can continue your creation", chatId, source);
        showAddNotePanel.execute(update);

    }
}
