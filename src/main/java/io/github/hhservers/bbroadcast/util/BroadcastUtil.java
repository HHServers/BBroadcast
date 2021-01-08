package io.github.hhservers.bbroadcast.util;

import io.github.hhservers.bbroadcast.BBroadcast;
import io.github.hhservers.bbroadcast.config.ConfigHandler;
import io.github.hhservers.bbroadcast.config.MainPluginConfig;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.api.text.title.Title;
import org.spongepowered.api.world.World;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BroadcastUtil {

    public void buildBroadcast(){
        List<BroadcastObject> broadcasts = BBroadcast.getMainPluginConfig().getBroadcastList();
        if(!broadcasts.isEmpty()) {
            for (int i = 0; i < broadcasts.size(); i++) {
                BroadcastObject cast = broadcasts.get(i);
                if (cast.getWorld().equals("global")) {
                    if (cast.getTitle()) {
                        BBroadcast.taskList.add(Task.builder()
                                .execute(() -> {
                                    Title title = Title.builder()
                                            .title(TextSerializers.FORMATTING_CODE.deserialize(cast.getMessage()))
                                            .fadeIn(5)
                                            .fadeOut(5)
                                            .build();
                                    if (!(cast.getSubtitle() == null || cast.getSubtitle().isEmpty())) {
                                        sendGlobalTitle(title.toBuilder().subtitle(TextSerializers.FORMATTING_CODE.deserialize(cast.getSubtitle())).build());
                                    } else {
                                        sendGlobalTitle(title);
                                    }
                                })
                                .delay(10, TimeUnit.SECONDS)
                                .interval(cast.getInterval(), TimeUnit.SECONDS)
                                .async()
                                .submit(BBroadcast.getInstance()));
                    } else {

                        BBroadcast.taskList.add(Task.builder()
                                .execute(() -> {
                                    sendGlobalCast(textSerializer(cast.getMessage()));
                                })
                                .delay(10, TimeUnit.SECONDS)
                                .interval(cast.getInterval(), TimeUnit.SECONDS)
                                .async()
                                .submit(BBroadcast.getInstance()));

                    }
                } else { //*ELSE IF NOT GLOBAL, THEN*//*
                    if (cast.getTitle()) {
                        BBroadcast.taskList.add(Task.builder()
                                .execute(() -> {
                                    Title title = Title.builder()
                                            .title(TextSerializers.FORMATTING_CODE.deserialize(cast.getMessage()))
                                            .fadeIn(5)
                                            .fadeOut(5)
                                            .build();
                                    if (!(cast.getSubtitle() == null || cast.getSubtitle().isEmpty())) {
                                        Sponge.getServer().getWorld(cast.getWorld()).get().sendTitle(title.toBuilder().subtitle(TextSerializers.FORMATTING_CODE.deserialize(cast.getSubtitle())).build());
                                    } else {
                                        Sponge.getServer().getWorld(cast.getWorld()).get().sendTitle(title);
                                    }
                                })
                                .delay(10, TimeUnit.SECONDS)
                                .interval(cast.getInterval(), TimeUnit.SECONDS)
                                .async()
                                .submit(BBroadcast.getInstance()));
                    } else {
                        BBroadcast.taskList.add(Task.builder()
                                .execute(() -> {
                                    if (cast.getWorld().equals("global")) {
                                        sendGlobalCast(textSerializer(cast.getMessage()));
                                    } else {
                                        Sponge.getServer().getWorld(cast.getWorld()).get().sendMessage(textSerializer(cast.getMessage()));
                                    }
                                })
                                .delay(10, TimeUnit.SECONDS)
                                .interval(cast.getInterval(), TimeUnit.SECONDS)
                                .async()
                                .submit(BBroadcast.getInstance()));
                    }
                }

            }
        }
    }

    public void buildOtherCasts(){
        List<RandomBroadcastObject> broadcasts = BBroadcast.getMainPluginConfig().getRandomList();
        int interval = BBroadcast.getMainPluginConfig().getInterval();
        Random rand = new Random();

        BBroadcast.taskList.add(Task.builder()
                .interval(interval, TimeUnit.SECONDS)
                .delay(10, TimeUnit.SECONDS)
                .async()
                .execute(task -> {
                    RandomBroadcastObject cast = broadcasts.get(rand.nextInt(broadcasts.size()));
                    if(cast.getWorld().equalsIgnoreCase("global")){
                        if(cast.getTitle()){
                            Title title = Title.builder()
                                    .title(TextSerializers.FORMATTING_CODE.deserialize(cast.getMessage()))
                                    .fadeIn(5)
                                    .fadeOut(5)
                                    .build();
                            if (!(cast.getSubtitle() == null || cast.getSubtitle().isEmpty())) {
                                sendGlobalTitle(title.toBuilder().subtitle(TextSerializers.FORMATTING_CODE.deserialize(cast.getSubtitle())).build());
                            } else {
                                sendGlobalTitle(title);
                            }
                        } else {sendGlobalCast(textSerializer(cast.getMessage()));}
                    } else {
                        if(Sponge.getServer().getWorld(cast.getWorld()).isPresent()){
                            if(cast.getTitle()) {
                                Title title = Title.builder()
                                        .title(TextSerializers.FORMATTING_CODE.deserialize(cast.getMessage()))
                                        .fadeIn(5)
                                        .fadeOut(5)
                                        .build();
                                if (!(cast.getSubtitle() == null || cast.getSubtitle().isEmpty())) {
                                    Sponge.getServer().getWorld(cast.getWorld()).get().sendTitle(title.toBuilder().subtitle(TextSerializers.FORMATTING_CODE.deserialize(cast.getSubtitle())).build());
                                } else {
                                    Sponge.getServer().getWorld(cast.getWorld()).get().sendTitle(title);
                                }
                            } else {Sponge.getServer().getWorld(cast.getWorld()).get().sendMessage(textSerializer(cast.getMessage()));}
                        }
                    }

                }).submit(BBroadcast.getInstance()));
    }

    private void sendGlobalTitle(Title title){
        Collection<World> worlds = Sponge.getServer().getWorlds();
        for (World w : worlds) {
            w.clearTitle();
            w.sendTitle(title);
        }
    }

    private void sendGlobalCast(Text cast){
        Collection<World> worlds = Sponge.getServer().getWorlds();
        for (World w : worlds) {
            w.sendMessage(cast);
        }
    }

    public void addNewCast(String message, String world, Boolean title, Boolean random) throws IOException, ObjectMappingException {
        MainPluginConfig conf = BBroadcast.getMainPluginConfig();
        if(random){
            RandomBroadcastObject rCast = new RandomBroadcastObject();
            rCast.setMessage(message);
            rCast.setWorld(world);
            rCast.setTitle(title);
            conf.getRandomList().add(rCast);
        } else {
            BroadcastObject cast = new BroadcastObject();
            cast.setTitle(title);
            cast.setWorld(world);
            cast.setMessage(message);
            conf.getBroadcastList().add(cast);
        }
        BBroadcast.getConfigHandler().saveConfig(conf);
        BBroadcast.getInstance().reloadConfig();
    }

    public void removeCast(int index) throws IOException, ObjectMappingException {
        MainPluginConfig conf = BBroadcast.getMainPluginConfig();
        conf.getBroadcastList().remove(index);
        BBroadcast.getConfigHandler().saveConfig(conf);
        BBroadcast.getInstance().reloadConfig();
    }

    public void removeRandom(int index) throws IOException, ObjectMappingException {
        MainPluginConfig conf = BBroadcast.getMainPluginConfig();
        conf.getRandomList().remove(index);
        BBroadcast.getConfigHandler().saveConfig(conf);
        BBroadcast.getInstance().reloadConfig();
    }

    public void addNewCast(String message, String world, Boolean title, Boolean random, Integer interval) throws IOException, ObjectMappingException {
        MainPluginConfig conf = BBroadcast.getMainPluginConfig();
        if(random){
            RandomBroadcastObject rCast = new RandomBroadcastObject();
            rCast.setMessage(message);
            rCast.setWorld(world);
            rCast.setTitle(title);
            conf.getRandomList().add(rCast);
        } else {
            BroadcastObject cast = new BroadcastObject();
            cast.setTitle(title);
            cast.setWorld(world);
            cast.setMessage(message);
            cast.setInterval(interval);
            conf.getBroadcastList().add(cast);
        }
        BBroadcast.getConfigHandler().saveConfig(conf);
        BBroadcast.getInstance().reloadConfig();
    }

    public PaginationList broadcastList(){
        List<Text> textList = new ArrayList<>();
        int i = -1;
        for(BroadcastObject cast : BBroadcast.getMainPluginConfig().getBroadcastList()){
            i++;
            int finalI = i;
            textList.add(noPrefixSerializer(
                    "&l&8-&r&bMessage: &6\"&r" + cast.getMessage() + "&6\"&r\n"
                    + "&l&8-&r&bWorld: &6" + cast.getWorld() + "\n"
                    + "&l&8-&r&bTitle?: &6" + cast.getTitle() + "\n"
                    + "&l&8-&r&bINDEX: &6" + i + "\n"
            ).toBuilder().append(Text.builder().append(noPrefixSerializer("&l&c[REMOVE]"))
                    .onClick(TextActions.executeCallback(commandSource -> {
                        try {
                            removeCast(finalI);
                            commandSource.sendMessage(textSerializer("&bBroadcast removed."));
                            Sponge.getCommandManager().process(commandSource, "bb list timed");
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ObjectMappingException e) {
                            e.printStackTrace();
                        }
                    }))
                    .build()).build());
        }
        return PaginationList.builder()
                .title(noPrefixSerializer("&l&8[&r&aB&dBroadcast&r&l&8]"))
                .contents(textList)
                .build();
    }

    public PaginationList randomList(){
        List<Text> textList = new ArrayList<>();
        int i = -1;
        for(RandomBroadcastObject cast : BBroadcast.getMainPluginConfig().getRandomList()){
            i++;
            int finalI = i;
            textList.add(noPrefixSerializer(
                    "&l&8-&r&bMessage: &6\"&r" + cast.getMessage() + "&6\"&r\n"
                            + "&l&8-&r&bWorld: &6" + cast.getWorld() + "\n"
                            + "&l&8-&r&bTitle?: &6" + cast.getTitle() + "\n"
                            + "&l&8-&r&bINDEX: &6" + i + "\n"
            ).toBuilder().append(Text.builder().append(noPrefixSerializer("&l&c[REMOVE]"))
                    .onClick(TextActions.executeCallback(commandSource -> {
                        try {
                            removeRandom(finalI);
                            commandSource.sendMessage(textSerializer("&bBroadcast removed."));
                            Sponge.getCommandManager().process(commandSource, "bb list random");
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ObjectMappingException e) {
                            e.printStackTrace();
                        }
                    }))
                    .build()).build());
        }
        return PaginationList.builder()
                .title(noPrefixSerializer("&l&8[&r&aB&dBroadcast&r&l&8]"))
                .contents(textList)
                .build();
    }

    public Text textSerializer(String s){return TextSerializers.FORMATTING_CODE.deserialize(BBroadcast.getMainPluginConfig().getPrefix() + " " + s);}
    public Text noPrefixSerializer(String s){return TextSerializers.FORMATTING_CODE.deserialize(s);}

}
