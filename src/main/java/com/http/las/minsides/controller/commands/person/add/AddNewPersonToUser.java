package com.http.las.minsides.controller.commands.person.add;

import com.http.las.minsides.controller.commands.abstractCommands.Command;
import com.http.las.minsides.controller.commands.person.OpenPeopleChoicePanel;
import com.http.las.minsides.controller.entity.Messages;
import com.http.las.minsides.controller.storage.SessionUpdate;
import com.http.las.minsides.controller.tools.ChatUtil;
import com.http.las.minsides.server.notes.service.NotesService;
import com.http.las.minsides.shared.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class AddNewPersonToUser implements Command {
    @Lazy
    @Autowired
    private OpenPeopleChoicePanel openPeopleChoicePanel;
    private NotesService notesService;

    AddNewPersonToUser(NotesService notesService) {
        this.notesService = notesService;
    }

    @Override
    public void execute(SessionUpdate update) throws TelegramApiException {
        String input = ChatUtil.getInput(update);
        if (input.equals(Messages.YES)) {
            Person personToSave = update.getPersonToSave();
            notesService.saveNewPerson(personToSave);
            openPeopleChoicePanel.setMessage(Messages.SAVED);
        } else if (input.equals(Messages.NO)) {
            openPeopleChoicePanel.setMessage(Messages.CANCELED);
        } else {
            ChatUtil.wrongInput();
        }
        openPeopleChoicePanel.execute(update);
    }
}
