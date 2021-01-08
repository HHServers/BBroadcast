package io.github.hhservers.bbroadcast.commands;

import io.github.hhservers.bbroadcast.util.BroadcastUtil;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class Help implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        BroadcastUtil util = new BroadcastUtil();
        src.sendMessage(util.textSerializer("&b- &d/bb add &bString: &a\"message\" &d| &bTitle?: &atrue/false &d| &bString: &a\"worldname\" &d| &bRandom?: &atrue/false &d| &b<Only if Random=false>Interval: &aX"));
        return CommandResult.success();
    }

    public static CommandSpec build(){
       return CommandSpec.builder()
                //.arguments(GenericArguments.string(Text.of("StringArg")), GenericArguments.integer(Text.of("IntArg")))
                .permission("bbroadcast.admin.help")
                .description(Text.of("Base command"))
                .executor(new Help())
                .build();
    }
}
