package com.las.bots.minsides.controller.commands.person.add;

import com.las.bots.minsides.controller.commands.person.OpenPeopleChoicePanel;
import com.las.bots.minsides.controller.entity.uiCommands.CommandName;
import com.las.bots.minsides.controller.storage.SessionUpdate;
import com.las.bots.minsides.controller.tools.ChatUtil;
import com.las.bots.minsides.controller.commands.abstractCommands.Command;
import com.las.bots.minsides.shared.shared.Messages;
import com.las.bots.minsides.shared.shared.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component(CommandName.NamesConstants.ADD_PEOPLE_TO_NOTE_COMMAND)
public class AddPersonToNoteByNumber implements Command {
    @Lazy
    @Autowired
    private OpenPeopleChoicePanel openPeopleChoicePanel;

    @Override
    public void execute(SessionUpdate update) throws TelegramApiException {
        String personNumber = ChatUtil.getNotNullMessageText(update);

        try {
            List<Person> userPeople = update.getUserPeople();
            Person person = ChatUtil.getByCommandNumber(userPeople, personNumber);
            update.addPersonToNote(person);
        } finally {
            openPeopleChoicePanel.setMessage(Messages.ADDED);
            openPeopleChoicePanel.execute(update);
        }
    }

}
