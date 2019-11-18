package com.http.las.minsides.controller.commands;

import com.http.las.minsides.controller.Command;
import com.http.las.minsides.controller.MInsidesBot;
import com.http.las.minsides.controller.tools.ChatUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.http.las.minsides.controller.entity.Messages.HI_SHY_GUY;


@Component
@AllArgsConstructor
public class SayHiToShyGuy implements Command {
    private MInsidesBot source;

    @Override
    public void execute(Update update) throws TelegramApiException {
        ChatUtil.sendMsg(HI_SHY_GUY, update, source);
    }
}
