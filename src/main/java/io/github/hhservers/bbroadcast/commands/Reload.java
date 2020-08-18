package io.github.hhservers.bbroadcast.commands;

import io.github.hhservers.bbroadcast.BBroadcast;
import lombok.SneakyThrows;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

public class Reload implements CommandExecutor {
    @SneakyThrows
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        BBroadcast.getInstance().reloadConfig();
        src.sendMessage(Text.of("BBroadcast config reloaded"));
        return CommandResult.success();
    }
}
