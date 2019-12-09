package com.http.las.minsides.controller.commands.person.add;

import com.http.las.minsides.controller.MInsidesBot;
import com.http.las.minsides.controller.commands.abstractCommands.Command;
import com.http.las.minsides.controller.storage.SessionUpdate;
import com.http.las.minsides.controller.tools.ButtonUtil;
import com.http.las.minsides.controller.tools.ChatUtil;
import com.http.las.minsides.shared.entity.Person;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@AllArgsConstructor
public class AddPersonDispatcher implements Command {
    private AddPersonToNoteByNumber addPersonToNoteByNumber;
    private AddNewPersonToUser addNewPersonToUser;
    private MInsidesBot source;

    @Override
    public void execute(SessionUpdate update) throws TelegramApiException {
        String personName = ChatUtil.getNotNullMessageText(update);
        long chatId = update.getChatId();

        if (personName.startsWith("/")) {
            addPersonToNoteByNumber.execute(update);
        } else {
            InlineKeyboardMarkup markup = ButtonUtil.createYesNoMarkup();
            update.setPersonToSave(new Person(personName, chatId));
            String message = "You sure you want to save new person with name " + personName + "?";
            SendMessage sendMsg = ChatUtil.createSendMarkup(message, update.getChatId(), markup);
            update.setNextCommand(addNewPersonToUser);
            source.execute(sendMsg);
        }
    }

}
