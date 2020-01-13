import com.las.bots.minsides.controller.commands.abstractCommands.Command;
import com.las.bots.minsides.controller.entity.uiCommands.CommandName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.*;
import java.util.stream.Collectors;

public class CommandsInitializeTest extends BaseTest {
    @Autowired
    private ApplicationContext context;
    private final Map<String, Command> TASK_IMPLS = new HashMap<>();

    @Test
    public void commandsInitTest() {
        CommandName[] commandNames = CommandName.values();
        for (CommandName command : commandNames) {
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
        Collection<Command> commands = TASK_IMPLS.values();
        List<CommandName> autoCommands = Arrays.stream(commandNames)
                .filter(CommandName::isAutoCommand)
                .collect(Collectors.toList());
        Assertions.assertEquals(autoCommands.size(), commands.size());
    }
}
