package com.http.las.minsides.controller;

import com.http.las.minsides.controller.commands.*;
import com.http.las.minsides.controller.commands.abstractCommands.AskAndWait;
import com.http.las.minsides.controller.commands.abstractCommands.Command;
import com.http.las.minsides.controller.entity.Messages;
import com.http.las.minsides.controller.entity.uiCommands.CommandName;
import com.http.las.minsides.controller.exception.WrongInputException;
import com.http.las.minsides.controller.storage.SessionUpdate;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;


@Component
public class TaskManager {
    private final Map<String, Command> TASK_IMPLS = new HashMap<>();

    public TaskManager(ApplicationContext context, AddNoteContent addNoteContent, AddTitle addTitle) {
        autoCommandsLoad(context);

        TASK_IMPLS.put(CommandName.ADD_NOTE_CONTENT_COMMAND.getCommandName(), new AskAndWait(Messages.PRINT_SOMETHING_FROM_UR_MIND, addNoteContent));
        TASK_IMPLS.put(CommandName.ADD_TITLE_COMMAND.getCommandName(), new AskAndWait(Messages.ANY_TITLE, addTitle));
    }

    void impl(String input, SessionUpdate update) throws TelegramApiException {
        Command command = TASK_IMPLS.get(input);

        if (command == null) {
            update.implNextCommandIfExist();
        } else {
            impl(command, update);
        }
    }


    private void impl(Command command, SessionUpdate update) throws TelegramApiException {
        if (command != null) {
            command.execute(update);
        } else {
            throw new WrongInputException();
        }
    }

    private void autoCommandsLoad(ApplicationContext context) {
        for (CommandName command : CommandName.values()) {
            String commandName = command.getCommandName();
            if (context.containsBean(commandName)) {
                Object commandBean = context.getBean(commandName);
                try {
                    TASK_IMPLS.put(commandName, (Command) commandBean);
                } catch (ClassCastException e) {
                    throw new RuntimeException("Command name is used as name of not Command instance.", e);
                }
            }
        }
    }

}
