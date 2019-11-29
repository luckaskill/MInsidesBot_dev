package com.http.las.minsides.controller.storage;

import com.http.las.minsides.controller.commands.CreateUserSession;
import com.http.las.minsides.controller.commands.abstractCommands.AskAndWait;
import com.http.las.minsides.controller.commands.abstractCommands.Command;
import com.http.las.minsides.controller.tools.ChatUtil;
import com.http.las.minsides.controller.tools.ClientBeanService;
import com.http.las.minsides.shared.entity.Note;
import com.http.las.minsides.shared.entity.NoteType;
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

import static com.http.las.minsides.controller.entity.Messages.PRE_START;

public class SessionUpdate extends Update {
    private Update update;
    private Long chatId;
    private UserSessionInfo session;

    public SessionUpdate(Update update) {
        this.update = update;
        chatId = ChatUtil.getChatId(update);
        this.session = SessionUtil.getOrPutSession(chatId);
    }

    public boolean implNextCommandIfExist(Update update) throws TelegramApiException {
        Command command = session.getNextCommand();
        boolean exists = command != null;
        if (exists) {
            command.execute(update);
        }
        return exists;
    }

    public boolean hasNextCommand() {
        boolean hasNextCommand = session.getNextCommand() != null;
        return hasNextCommand;
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
            session.setKey(null);
        }
        return timeOuted;
    }

    public boolean checkSession(Update update) throws TelegramApiException {
        boolean hasKey = session.hasKey();
        if (!hasKey) {
            initKey(update);
        } else {
            boolean timeOuted = checkTimeout();
            return !timeOuted;
        }
        return false;
    }

    private void initKey(Update update) throws TelegramApiException {
        implNextCommandOrAskAndWait(update, PRE_START, CreateUserSession.class);
    }

    private void implNextCommandOrAskAndWait(Update update, String messageIfNotExist, Class<? extends Command> commandClass) throws TelegramApiException {
        boolean hasNextCommand = hasNextCommand();
        if (hasNextCommand) {
            session.getNextCommand().execute(update);
        } else {
            Command command = ClientBeanService.getBean(commandClass);
            new AskAndWait(messageIfNotExist, command).execute(update);
        }
    }

    public void addTypeToSaveToCurNote() {
        Note noteInCreation = getOrPutInCreationNote();
        List<NoteType> noteTypes = noteInCreation.getNoteTypes();
        noteTypes.add(session.getTypeToSave());
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
