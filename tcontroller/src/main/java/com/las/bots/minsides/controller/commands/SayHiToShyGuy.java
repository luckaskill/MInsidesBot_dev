package com.las.bots.minsides.controller.commands;

import com.las.bots.minsides.controller.MInsidesBot;
import com.las.bots.minsides.controller.commands.abstractCommands.Command;
import com.las.bots.minsides.shared.shared.Messages;
import com.las.bots.minsides.controller.entity.uiCommands.CommandName;
import com.las.bots.minsides.controller.storage.SessionUpdate;
import com.las.bots.minsides.controller.tools.ChatUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component(CommandName.NamesConstants.SAY_HI_SHY_GUY_COMMAND)
@AllArgsConstructor
public class SayHiToShyGuy implements Command {
    private MInsidesBot source;

    @Override
    public void execute(SessionUpdate update) throws TelegramApiException {
        ChatUtil.sendMsg(Messages.HI_SHY_GUY, update, source);
    }
}
