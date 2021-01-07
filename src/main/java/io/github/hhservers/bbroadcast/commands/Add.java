package io.github.hhservers.bbroadcast.commands;

import io.github.hhservers.bbroadcast.util.BroadcastUtil;
import lombok.SneakyThrows;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class Add implements CommandExecutor {
    @SneakyThrows
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        BroadcastUtil util = new BroadcastUtil();

        if (args.<String>getOne(Text.of("message")).isPresent() && args.<String>getOne(Text.of("world")).isPresent() && args.<Boolean>getOne(Text.of("title?")).isPresent() && args.<Boolean>getOne(Text.of("random?")).isPresent()) {
            String message = args.<String>getOne(Text.of("message")).get();
            String world = args.<String>getOne(Text.of("world")).get();
            Boolean title = args.<Boolean>getOne(Text.of("title?")).get();
            Boolean random = args.<Boolean>getOne(Text.of("random?")).get();

            if (args.getOne(Text.of("interval")).isPresent()) {
                if (args.<Boolean>getOne(Text.of("random?")).get().equals(true)) {
                    src.sendMessage(util.textSerializer("&bRandom broadcasts cannot have an interval set. Remove the interval argument."));
                } else {
                    Integer interval = args.<Integer>getOne(Text.of("interval")).get();
                    util.addNewCast(message, world, title, random, interval);
                    src.sendMessage(util.textSerializer("&bBroadcast added."));
                }
            } else {
                util.addNewCast(message, world, title, random);
                src.sendMessage(util.textSerializer("&bBroadcast added."));
            }
        } else {
            src.sendMessage(util.textSerializer("&bCommand usage: &d/bb add &bString: &a\"message\" &d| &bTitle?: &atrue/false &d| &bString: &a\"worldname\" &d| &bRandom?: &atrue/false &d| &b<Only if Random=false>Interval: &aX"));
        }

        return CommandResult.success();
    }

    public static CommandSpec build(){
       return CommandSpec.builder()
                .arguments(GenericArguments.string(Text.of("message")), GenericArguments.bool(Text.of("title?")), GenericArguments.string(Text.of("world")), GenericArguments.bool(Text.of("random?")), GenericArguments.optional(GenericArguments.integer(Text.of("interval"))))
                .permission("bbroadcast.admin.add")
                .description(Text.of("Base command"))
                .executor(new Add())
                .build();
    }
}
