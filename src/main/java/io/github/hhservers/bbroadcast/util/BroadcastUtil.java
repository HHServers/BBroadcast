package io.github.hhservers.bbroadcast.util;

import io.github.hhservers.bbroadcast.BBroadcast;
import io.github.hhservers.bbroadcast.config.BroadcastObject;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.api.text.title.Title;
import org.spongepowered.api.world.World;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BroadcastUtil {

    public void buildBroadcast(){
        List<BroadcastObject> broadcasts = BBroadcast.getMainPluginConfig().getBroadcastList();
        for (int i = 0; i < broadcasts.size() ; i++) {
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
            } else { /*ELSE IF NOT GLOBAL, THEN*/
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

    public Text textSerializer(String s){return TextSerializers.FORMATTING_CODE.deserialize("&l&8[&r&dB&aBroadcast&r&l&8]&r " + s);}

}
