package io.github.hhservers.bbroadcast.commands;

import io.github.hhservers.bbroadcast.BBroadcast;
import io.github.hhservers.bbroadcast.util.BroadcastUtil;
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
        BroadcastUtil util = new BroadcastUtil();
        src.sendMessage(util.textSerializer("&d/bb list &a<timed/random> &l&8 - &r&bList broadcasts"));
        src.sendMessage(util.textSerializer("&d/bb reload &l&8 - &r&bReload config"));
        src.sendMessage(util.textSerializer("&d/bb help &l&8 - &r&bDetailed command usage"));
        src.sendMessage(util.textSerializer("&d/bb add &a<message> <title?> <world> <random?> &l&8 - &r&bAdd new broadcast"));
        return CommandResult.success();
    }

    public static CommandSpec build(){
       return CommandSpec.builder()
                .child(Reload.build(), "reload")
                .child(List.build(), "list")
                .child(Help.build(), "help")
                .child(Add.build(), "add")
                .permission("bbroadcast.admin.base")
                .description(Text.of("Base command"))
                .executor(new Base())
                .build();
    }
}
