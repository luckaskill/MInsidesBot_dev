package com.http.las.minsides.controller.storage;

import com.http.las.minsides.controller.commands.abstractCommands.Command;
import com.http.las.minsides.controller.MInsidesBot;
import com.http.las.minsides.controller.commands.CreateUserSession;
import com.http.las.minsides.controller.commands.abstractCommands.AskAndWait;
import com.http.las.minsides.controller.commands.abstractCommands.CreateSessionCommand;
import com.http.las.minsides.controller.tools.ChatUtil;
import com.http.las.minsides.controller.tools.ClientBeanService;
import com.http.las.minsides.shared.entity.Note;
import com.http.las.minsides.shared.entity.NoteType;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.List;

import static com.http.las.minsides.controller.entity.Messages.PRE_START;
import static com.http.las.minsides.controller.entity.Messages.TIMEOUT;

public class SessionUtil {
    private static final HashMap<Long, UserSessionInfo> SESSIONS = new HashMap<>();

    public static Command getNextCommand(Update update) throws TelegramApiException {
        UserSessionInfo sessionInfo = getSession(update);
        Command nextCommand = sessionInfo.getNextCommand();
        return nextCommand;
    }

    public static boolean implNextCommandIfExist(Update update) throws TelegramApiException {
        Command command = getNextCommand(update);
        boolean exists = command != null;
        if (exists) {
            command.execute(update);
        }
        return exists;
    }

    public static void implNextCommand(Update update) throws TelegramApiException {
        Command command = getNextCommand(update);
        command.execute(update);
    }

    public static void setNextCommand(Update update, Command command) throws TelegramApiException {
        UserSessionInfo sessionInfo;
        sessionInfo = getSession(update);
        sessionInfo.setNextCommand(command);
    }

    public static void clearNextCommand(Update update) throws TelegramApiException {
        setNextCommand(update, null);
    }

    public static boolean hasNextCommand(Update update) throws TelegramApiException {
        UserSessionInfo session = getSession(update);
        boolean hasNextCommand = session.getNextCommand() != null;
        return hasNextCommand;
    }

    public static NoteType getNoteTypeToSave(Update update) throws TelegramApiException {
        UserSessionInfo sessionInfo = getSession(update);
        NoteType typeToSave = sessionInfo.getTypeToSave();
        return typeToSave;
    }

    public static void setUserTypeToSave(Update update, NoteType typeToSave) throws TelegramApiException {
        UserSessionInfo sessionInfo = getSession(update);
        sessionInfo.setTypeToSave(typeToSave);
    }

    public static void setUserNotesTypes(Update update, List<NoteType> noteTypes) throws TelegramApiException {
        UserSessionInfo sessionInfo = getSession(update);
        sessionInfo.setNoteTypes(noteTypes);
    }

    public static List<NoteType> getUserNoteTypes(Update update) throws TelegramApiException {
        UserSessionInfo sessionInfo = getSession(update);
        List<NoteType> noteTypes = sessionInfo.getNoteTypes();
        return noteTypes;
    }

    public static void setUserNotes(Update update, List<Note> notes) throws TelegramApiException {
        UserSessionInfo sessionInfo = getSession(update);
        sessionInfo.setNotes(notes);
    }

    public static List<Note> getUserNotes(Update update) throws TelegramApiException {
        UserSessionInfo sessionInfo = getSession(update);
        List<Note> notes = sessionInfo.getNotes();
        return notes;
    }

    public static Note setNewCreationNote(Update update) throws TelegramApiException {
        UserSessionInfo session = getSession(update);
        Note note = new Note();
        session.setNoteInCreation(note);
        return note;
    }

    public static Note getOrPutInCreationNote(Update update) throws TelegramApiException {
        UserSessionInfo sessionInfo = getSession(update);

        Note noteInCreation = sessionInfo.getNoteInCreation();
        if (noteInCreation == null) {
            Note newNote = new Note();
            sessionInfo.setNoteInCreation(newNote);
            noteInCreation = newNote;
        }

        return noteInCreation;
    }

    public static void removeFromCreation(Update update) throws TelegramApiException {
        UserSessionInfo sessionInfo = getSession(update);
        sessionInfo.setNoteInCreation(null);
    }

    public static Note getCurrentEditedNote(Update update) throws TelegramApiException {
        Note note = getOrPutInCreationNote(update);
        return note;
    }

    private static UserSessionInfo getOrPutSession(long chatId) {
        UserSessionInfo session;
        if (!SESSIONS.containsKey(chatId)) {
            session = new UserSessionInfo(chatId);
            SESSIONS.put(chatId, session);
        } else {
            session = SESSIONS.get(chatId);
        }
        return session;
    }

    private static UserSessionInfo getSession(Update update) throws TelegramApiException {
        long chatId = ChatUtil.getChatId(update);
        UserSessionInfo sessionInfo = getOrPutSession(chatId);
        return sessionInfo;
    }

    private static UserSessionInfo getSession(Long chatId) {
        UserSessionInfo sessionInfo = getOrPutSession(chatId);
        return sessionInfo;
    }

    public static void invalidate(Update update) {
        Long chatId = ChatUtil.getChatId(update);
        SESSIONS.remove(chatId);
    }

    private static boolean checkTimeout(Update update) throws TelegramApiException {
        UserSessionInfo session = getSession(update);
        boolean timeOuted = session.timeOuted();
        if (timeOuted) {
            refreshSessionKey(session, update);
            timeOuted = session.timeOuted();
        }
        return timeOuted;
    }

    public static boolean hasKey(Update update) {
        Long chatId = ChatUtil.getChatId(update);
        UserSessionInfo session = getSession(chatId);
        boolean hasKey = session.hasKey();
        return hasKey;
    }

    public static void setKey(Update update, byte[] key) throws TelegramApiException {
        UserSessionInfo session = getSession(update);
        session.setKey(key);
    }

    public static byte[] getKey(Update update) throws TelegramApiException {
        UserSessionInfo session = getSession(update);
        byte[] key = session.getKey();
        return key;
    }

    public static boolean checkSession(Update update) throws TelegramApiException {
        boolean hasKey = SessionUtil.hasKey(update);
        if (!hasKey) {
            initKey(update);
        } else {
            boolean timeOuted = checkTimeout(update);
            return !timeOuted;
        }
        return false;
    }

    private static void initKey(Update update) throws TelegramApiException {
        implNextCommandOrAskAndWait(update, PRE_START, CreateUserSession.class);
    }

    private static void implNextCommandOrAskAndWait(Update update, String messageIfNotExist, Class<? extends Command> commandClass) throws TelegramApiException {
        boolean hasNextCommand = hasNextCommand(update);
        if (hasNextCommand) {
            implNextCommand(update);
        } else {
            Command command = ClientBeanService.getBean(commandClass);
            new AskAndWait(messageIfNotExist, command).execute(update);
        }
    }

    private static void refreshSessionKey(UserSessionInfo session, Update update) throws TelegramApiException {
        Command nextCommand = session.getNextCommand();
        if (nextCommand != null && nextCommand instanceof CreateSessionCommand) {
            nextCommand.execute(update);
        } else {
            Command command = ClientBeanService.getBean(CreateUserSession.class);
            MInsidesBot source = ClientBeanService.getBean(MInsidesBot.class);
            ChatUtil.sendMsg(TIMEOUT, update, source);
            session.setNextCommand(command);
        }

    }

    public static void refreshTimeout(Update update) throws TelegramApiException {
        UserSessionInfo session = getSession(update);
        session.refreshTimeout();
    }

}
