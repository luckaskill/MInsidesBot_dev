package com.http.las.minsides.controller.commands;

import com.http.las.minsides.controller.Command;
import com.http.las.minsides.controller.MInsidesBot;
import com.http.las.minsides.controller.tools.ChatUtil;
import com.http.las.minsides.controller.tools.StockUtil;
import com.http.las.minsides.entity.Note;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@AllArgsConstructor
public class AddTitle implements Command {
    private ShowAddNotePanel showAddNotePanel;
    private MInsidesBot source;

    @Override
    public void execute(Update update) throws TelegramApiException {
        Long chatId = ChatUtil.getChatId(update);
        Note note = StockUtil.getOrPutInCreationNote(chatId);

        String textMsg = ChatUtil.getMessage(update);
        note.setTitle(textMsg);

        ChatUtil.sendMsg("Nice, now u can continue your creation", chatId, source);
        showAddNotePanel.execute(update);
    }
}
