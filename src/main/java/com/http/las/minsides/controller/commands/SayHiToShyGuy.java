package com.http.las.minsides.controller.commands;

import com.http.las.minsides.controller.MInsidesBot;
import com.http.las.minsides.controller.commands.abstractCommands.Command;
import com.http.las.minsides.controller.entity.uiCommands.CommandName;
import com.http.las.minsides.controller.storage.SessionUpdate;
import com.http.las.minsides.controller.tools.ChatUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.http.las.minsides.controller.entity.Messages.HI_SHY_GUY;


@Component(CommandName.NamesConstants.SAY_HI_SHY_GUY_COMMAND)
@AllArgsConstructor
public class SayHiToShyGuy implements Command {
    private MInsidesBot source;

    @Override
    public void execute(SessionUpdate update) throws TelegramApiException {
        ChatUtil.sendMsg(HI_SHY_GUY, update, source);
    }
}
