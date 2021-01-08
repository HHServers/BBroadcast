/*
package io.github.hhservers.bbroadcast.commands;

import io.github.hhservers.bbroadcast.util.BroadcastUtil;
import lombok.SneakyThrows;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class Remove implements CommandExecutor {
    @SneakyThrows
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        BroadcastUtil util = new BroadcastUtil();
        if(args.<Integer>getOne(Text.of("index")).isPresent()){
            int index = args.<Integer>getOne(Text.of("index")).get();
            util.removeCast(index);
            Sponge.getCommandManager().process(src, "bb");
        } else {src.sendMessage(util.textSerializer("&bYou must include an index. Type /bb to view the indexes."));}
        return CommandResult.success();
    }

    public static CommandSpec build(){
       return CommandSpec.builder()
                .permission("bbroadcast.admin.remove")
                .arguments(GenericArguments.integer(Text.of("index")))
                .description(Text.of("Remove command"))
                .executor(new Remove())
                .build();
    }
}
*/
