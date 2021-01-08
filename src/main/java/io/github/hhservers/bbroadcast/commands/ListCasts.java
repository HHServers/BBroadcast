package io.github.hhservers.bbroadcast.commands;

import io.github.hhservers.bbroadcast.util.BroadcastUtil;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class ListCasts implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        BroadcastUtil util = new BroadcastUtil();
        util.broadcastList().sendTo(src);
        return CommandResult.success();
    }

    public static CommandSpec build(){
       return CommandSpec.builder()
                .child(Add.build(), "add")
                .permission("bbroadcast.admin.list.timed")
                .description(Text.of("List command"))
                .executor(new ListCasts())
                .build();
    }
}
