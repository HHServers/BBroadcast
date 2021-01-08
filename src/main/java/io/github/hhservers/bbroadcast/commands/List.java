package io.github.hhservers.bbroadcast.commands;

import io.github.hhservers.bbroadcast.util.BroadcastUtil;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class List implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        BroadcastUtil util = new BroadcastUtil();
        src.sendMessage(util.textSerializer("&bYou can use &d/bb list timed&b to list timed broadcasts and &d/bb list random&b to list random broadcasts."));
        return CommandResult.success();
    }

    public static CommandSpec build(){
       return CommandSpec.builder()
                .child(ListCasts.build(), "timed")
                .child(ListRandom.build(), "random")
                .permission("bbroadcast.admin.list")
                .description(Text.of("List command"))
                .executor(new List())
                .build();
    }
}
