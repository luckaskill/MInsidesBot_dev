package com.http.las.minsides.controller.commands.person;

import com.http.las.minsides.controller.MInsidesBot;
import com.http.las.minsides.controller.commands.abstractCommands.Command;
import com.http.las.minsides.controller.commands.person.add.AddPersonDispatcher;
import com.http.las.minsides.controller.entity.ButtonKeyboardData;
import com.http.las.minsides.controller.entity.Messages;
import com.http.las.minsides.controller.entity.uiCommands.CommandName;
import com.http.las.minsides.controller.storage.SessionUpdate;
import com.http.las.minsides.controller.tools.ButtonUtil;
import com.http.las.minsides.controller.tools.ChatUtil;
import com.http.las.minsides.server.notes.service.NotesService;
import com.http.las.minsides.shared.entity.Note;
import com.http.las.minsides.shared.entity.Person;
import com.http.las.minsides.shared.util.EntityUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component(CommandName.NamesConstants.OPEN_PEOPLE_CHOICE_PANEL_COMMAND)
public class OpenPeopleChoicePanel implements Command {
    private NotesService notesService;
    private MInsidesBot source;
    private AddPersonDispatcher addPersonDispatcher;

    private String message;

    public OpenPeopleChoicePanel(NotesService notesService, MInsidesBot source, AddPersonDispatcher addPersonDispatcher) {
        this.notesService = notesService;
        this.source = source;
        this.addPersonDispatcher = addPersonDispatcher;
        setDefaultMessage();
    }

    @Override
    public void execute(SessionUpdate update) throws TelegramApiException {
        Long chatId = update.getChatId();
        Note note = update.getOrPutInCreationNote();
        List<Person> notePeople = note.getNotePeople();
        List<Person> userPeople = notesService.getUserPeople(chatId);
        List<Person> unselectedPeople = calculateUnselectedPerson(notePeople, userPeople);

        StringBuilder builder = new StringBuilder("Print person name ");

        if (!unselectedPeople.isEmpty()) {
            builder.append("or choose from existing: \n");
            try {
                String peopleView = buildPeopleView(unselectedPeople);
                builder.append(peopleView);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                throw new TelegramApiException(e);
            }
        }

        SendMessage sendMarkup = buildBackMarkup(chatId);
        update.setUserPeople(unselectedPeople);
        setDefaultMessage();

        ChatUtil.sendMsg(builder.toString(), update, source);
        source.execute(sendMarkup);
        update.setNextCommand(addPersonDispatcher);
    }

    private SendMessage buildBackMarkup(Long chatId) {
        InlineKeyboardMarkup markup = ButtonUtil.configureKeyboard(Collections.singletonList(
                new ButtonKeyboardData(CommandName.NamesConstants.SHOW_ADD_NOTE_PANEL_COMMAND, Messages.BACK, true)));
        SendMessage sendMarkup = ChatUtil.createSendMarkup(message, chatId, markup);
        return sendMarkup;
    }

    private String buildPeopleView(List<Person> peopleList) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        List<String> peopleNames = EntityUtil.readEntityValues(peopleList, Person.PERSON_NAME_FIELDNAME);
        String numberedList = ChatUtil.buildSlashedNumList(peopleNames);

        return numberedList;
    }

    private List<Person> calculateUnselectedPerson(List<Person> notePeople, List<Person> userPeople) {
        List<Person> unselectedPeople = new ArrayList<>(userPeople);
        unselectedPeople.removeAll(notePeople);

        return unselectedPeople;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private void setDefaultMessage() {
        this.message = "Default";
    }
}
