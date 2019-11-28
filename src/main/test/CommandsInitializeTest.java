import com.http.las.minsides.controller.commands.abstractCommands.Command;
import com.http.las.minsides.controller.entity.uiCommands.CommandContainer;
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
        CommandContainer[] commandNames = CommandContainer.values();
        for (CommandContainer command : commandNames) {
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
        List<CommandContainer> autoCommands = Arrays.stream(commandNames)
                .filter(CommandContainer::isAutoCommand)
                .collect(Collectors.toList());
        Assertions.assertEquals(autoCommands.size(), commands.size());
    }
}
