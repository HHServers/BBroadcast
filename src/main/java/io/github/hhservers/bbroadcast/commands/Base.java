package io.github.hhservers.bbroadcast.commands;

import io.github.hhservers.bbroadcast.BBroadcast;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class Base implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        //do things
        //args.<String>getOne(Text.of("StringArgs")).get()
        return CommandResult.success();
    }

    public static CommandSpec build(){
       return CommandSpec.builder()
                .child(Child.build(), "child")
                .child(Reload.build(), "reload")
                //.arguments(GenericArguments.string(Text.of("StringArg")), GenericArguments.integer(Text.of("IntArg")))
                .permission("bbroadcast.admin.base")
                .description(Text.of("Base command"))
                .executor(new Base())
                .build();
    }
}
