package io.github.hhservers.bbroadcast.commands;

import io.github.hhservers.bbroadcast.BBroadcast;
import lombok.SneakyThrows;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

public class Reload implements CommandExecutor {
    @SneakyThrows
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        BBroadcast.getInstance().reloadConfig();
        src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&l&8[&r&aB&dBroadcast&l&8]&r&bConfig reloaded."));
        return CommandResult.success();
    }

    public static CommandSpec build(){
        return CommandSpec.builder()
                .permission("bbroadcast.admin.reload")
                .description(Text.of("Child command of Base"))
                .executor(new Reload())
                .build();
    }
}
