package com.http.las.minsides.controller;

import com.http.las.minsides.controller.commands.*;
import com.http.las.minsides.controller.commands.abstractCommands.AskAndWait;
import com.http.las.minsides.controller.commands.abstractCommands.Command;
import com.http.las.minsides.controller.entity.uiCommands.Commands;
import com.http.las.minsides.controller.exception.WrongInputException;
import com.http.las.minsides.controller.storage.SessionUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

import static com.http.las.minsides.controller.entity.uiCommands.CommandNames.*;

@Component
public class TaskManager {
    private final Map<String, Command> TASK_IMPLS = new HashMap<>();

    public TaskManager(ApplicationContext context, AddNoteContent addNoteContent, AddTitle addTitle) {
        autoCommandsLoad(context);

        TASK_IMPLS.put(ADD_NOTE_CONTENT_COMMAND, new AskAndWait("Print something from ur mind:", addNoteContent));
        TASK_IMPLS.put(ADD_TITLE_COMMAND, new AskAndWait("Print something from ur mind:", addTitle));
    }

    void impl(String input, Update update) throws TelegramApiException {
        Command command = TASK_IMPLS.get(input);
        command = command == null
                ? SessionUtil.getNextCommand(update)
                : command;

        if (SessionUtil.hasNextCommand(update)) {
            SessionUtil.clearNextCommand(update);
        }
        impl(command, update);
    }


    private void impl(Command command, Update update) throws TelegramApiException {
        if (command != null) {
            command.execute(update);
        } else {
            throw new WrongInputException();
        }
    }

    private void autoCommandsLoad(ApplicationContext context) {
        for (Commands command : Commands.values()) {
            String commandName = command.getCommandName();
            if (context.containsBean(commandName)) {
                Object commandBean = context.getBean(commandName);
                if (commandBean instanceof Command) {
                    TASK_IMPLS.put(commandName, (Command) commandBean);
                }
            }
        }
    }

}
