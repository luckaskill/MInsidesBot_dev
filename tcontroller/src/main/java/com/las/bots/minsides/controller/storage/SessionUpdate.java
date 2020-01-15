package com.las.bots.minsides.controller.storage;

import com.las.bots.minsides.controller.commands.CreateUserSession;
import com.las.bots.minsides.controller.commands.abstractCommands.AskAndWait;
import com.las.bots.minsides.controller.commands.abstractCommands.Command;
import com.las.bots.minsides.controller.commands.abstractCommands.CreateSessionCommand;
import com.las.bots.minsides.controller.tools.ChatUtil;
import com.las.bots.minsides.controller.tools.ClientBeanService;
import com.las.bots.minsides.shared.shared.entity.Note;
import com.las.bots.minsides.shared.shared.entity.NoteType;
import com.las.bots.minsides.shared.shared.entity.Person;
import lombok.Getter;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.ChosenInlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.payments.PreCheckoutQuery;
import org.telegram.telegrambots.meta.api.objects.payments.ShippingQuery;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

import static com.las.bots.minsides.shared.shared.Messages.PRE_START;
import static com.las.bots.minsides.shared.shared.Messages.TIMEOUT;

public class SessionUpdate extends Update {
    private Update update;
    @Getter
    private Long chatId;
    private UserSessionInfo session;
    private TelegramLongPollingBot source;

    public SessionUpdate(Update update, TelegramLongPollingBot source) {
        this.update = update;
        this.source = source;
        chatId = ChatUtil.getChatId(update);
        this.session = SessionUtil.getOrPutSession(chatId);
    }

    public boolean implNextCommandIfExist() throws TelegramApiException {
        boolean exists = hasNextCommand();
        if (exists) {
            implNextCommand();
        }
        return exists;
    }

    public void implNextCommand() throws TelegramApiException {
        Command command = session.getNextCommand();
        //first clean, then execute!!!
        cleanIfNotRepeatable();
        command.execute(this);
    }

    private void cleanIfNotRepeatable() {
        Command nextCommand = session.getNextCommand();
        if (!nextCommand.isRepeatable()) {
            session.setNextCommand(null);
        }
    }

    public byte[] getKey() {
        byte[] key = session.getKey();
        return key;
    }

    public void setUserNotes(List<Note> notes) {
        session.setNotes(notes);
    }

    public List<Note> getUserNotes() {
        List<Note> notes = session.getNotes();
        return notes;
    }

    public List<Person> getUserPeople() {
        List<Person> userPeople = session.getUserPeople();
        return userPeople;
    }

    public void setUserPeople(List<Person> userPeople) {
        session.setUserPeople(userPeople);
    }

    public boolean hasNextCommand() {
        boolean hasNextCommand = session.getNextCommand() != null;
        return hasNextCommand;
    }

    public void setKey(byte[] key) {
        session.setKey(key);
    }

    public void refreshTimeout() {
        session.refreshTimeout();
    }

    public void setUserNotesTypes(List<NoteType> types) {
        session.setNoteTypes(types);
    }

    public void setNextCommand(Command command) {
        session.setNextCommand(command);
    }

    public Command getNextCommand() {
        Command nextCommand = session.getNextCommand();
        return nextCommand;
    }

    public Note getCurrentEditedNote() {
        Note noteInCreation = session.getNoteInCreation();
        return noteInCreation;
    }

    public NoteType getNoteTypeToSave() {
        NoteType typeToSave = session.getTypeToSave();
        return typeToSave;
    }

    public void setPersonToSave(Person personToSave) {
        session.setPersonToSave(personToSave);
    }

    public Person getPersonToSave() {
        Person personToSave = session.getPersonToSave();
        return personToSave;
    }

    public void setTypeToSave(NoteType typeToSave) {
        session.setTypeToSave(typeToSave);
    }

    public List<NoteType> getUserNoteTypes() {
        List<NoteType> noteTypes = session.getNoteTypes();
        return noteTypes;
    }

    public Note getOrPutInCreationNote() {

        Note noteInCreation = session.getNoteInCreation();
        if (noteInCreation == null) {
            Note newNote = new Note();
            session.setNoteInCreation(newNote);
            noteInCreation = newNote;
        }

        return noteInCreation;
    }

    public void removeFromCreation() {
        session.setNoteInCreation(null);
    }

    private boolean checkTimeout() throws TelegramApiException {
        boolean timeOuted = session.timeOuted();
        if (timeOuted) {
            SessionUtil.invalidate(chatId);
            ChatUtil.sendMsg(TIMEOUT, this, source);
        }
        return timeOuted;
    }

    public boolean initSession() throws TelegramApiException {
        boolean hasKey = session.hasKey();
        if (!hasKey) {
            initKey();
        } else {
            boolean timeOuted = checkTimeout();
            return !timeOuted;
        }
        return false;
    }

    private void initKey() throws TelegramApiException {
        Command nextCommand = getNextCommand();
        if (nextCommand instanceof CreateSessionCommand) {
            session.getNextCommand().execute(this);
        } else {
            Command command = ClientBeanService.getBean(CreateUserSession.class);
            new AskAndWait(PRE_START, command).execute(this);
        }
    }

    public void addTypeToSaveToCurNote() {
        Note noteInCreation = getOrPutInCreationNote();
        List<NoteType> noteTypes = noteInCreation.getNoteTypes();
        noteTypes.add(session.getTypeToSave());
    }

    public void addPersonToSaveToCurNote() {
        Note noteInCreation = getOrPutInCreationNote();
        List<Person> notePeople = noteInCreation.getNotePeople();
        notePeople.add(session.getPersonToSave());
    }

    public void addTypeToNote(NoteType type) {
        Note noteInCreation = getOrPutInCreationNote();
        List<NoteType> noteTypes = noteInCreation.getNoteTypes();
        noteTypes.add(type);
    }

    public void addPersonToNote(Person person) {
        Note noteInCreation = getOrPutInCreationNote();
        List<Person> notePeople = noteInCreation.getNotePeople();

        if (!notePeople.contains(person)) {
            notePeople.add(person);
        }
    }

    @Override
    public Integer getUpdateId() {
        return update.getUpdateId();
    }

    @Override
    public Message getMessage() {
        return update.getMessage();
    }

    @Override
    public InlineQuery getInlineQuery() {
        return update.getInlineQuery();
    }

    @Override
    public ChosenInlineQuery getChosenInlineQuery() {
        return update.getChosenInlineQuery();
    }

    @Override
    public CallbackQuery getCallbackQuery() {
        return update.getCallbackQuery();
    }

    @Override
    public Message getEditedMessage() {
        return update.getEditedMessage();
    }

    @Override
    public Message getChannelPost() {
        return update.getChannelPost();
    }

    @Override
    public Message getEditedChannelPost() {
        return update.getEditedChannelPost();
    }

    @Override
    public ShippingQuery getShippingQuery() {
        return update.getShippingQuery();
    }

    @Override
    public PreCheckoutQuery getPreCheckoutQuery() {
        return update.getPreCheckoutQuery();
    }

    @Override
    public Poll getPoll() {
        return update.getPoll();
    }

    @Override
    public boolean hasMessage() {
        return update.hasMessage();
    }

    @Override
    public boolean hasInlineQuery() {
        return update.hasInlineQuery();
    }

    @Override
    public boolean hasChosenInlineQuery() {
        return update.hasChosenInlineQuery();
    }

    @Override
    public boolean hasCallbackQuery() {
        return update.hasCallbackQuery();
    }

    @Override
    public boolean hasEditedMessage() {
        return update.hasEditedMessage();
    }

    @Override
    public boolean hasChannelPost() {
        return update.hasChannelPost();
    }

    @Override
    public boolean hasEditedChannelPost() {
        return update.hasEditedChannelPost();
    }

    @Override
    public boolean hasShippingQuery() {
        return update.hasShippingQuery();
    }

    @Override
    public boolean hasPreCheckoutQuery() {
        return update.hasPreCheckoutQuery();
    }

    @Override
    public boolean hasPoll() {
        return update.hasPoll();
    }

    @Override
    public String toString() {
        return update.toString();
    }
}
