package io.github.hhservers.bbroadcast;

import com.google.inject.Inject;
import io.github.hhservers.bbroadcast.commands.Base;
import io.github.hhservers.bbroadcast.config.ConfigHandler;
import io.github.hhservers.bbroadcast.config.MainPluginConfig;
import io.github.hhservers.bbroadcast.util.BroadcastUtil;
import lombok.Getter;
import ninja.leaping.configurate.objectmapping.GuiceObjectMapperFactory;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.scheduler.Task;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Plugin(
        id = "bbroadcast",
        name = "BBroadcast",
        description = "Broadcast plugin",
        authors = {
                "blvxr"
        }
)
public class BBroadcast {

    @Getter
    private static BBroadcast instance;
    @Getter
    @Inject
    private Logger logger;
    @Getter
    private static MainPluginConfig mainPluginConfig;
    private final GuiceObjectMapperFactory factory;
    private final File configDir;
    private static ConfigHandler configHandler;
    public static List<Task> taskList = new ArrayList<>();


    @Inject
    public BBroadcast(GuiceObjectMapperFactory factory, @ConfigDir(sharedRoot = false) File configDir) {
        this.factory=factory;
        this.configDir=configDir;
        instance=this;
    }

    @Listener
    public void onGamePreInit(GamePreInitializationEvent e) throws IOException, ObjectMappingException {
        reloadConfig();
    }

    @Listener
    public void onGameInit(GameInitializationEvent e){
        instance = this;
        Sponge.getCommandManager().register(instance, Base.build(), "bbroadcast", "bb");
    }

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        //new BroadcastUtil().buildBroadcast();
    }

    @Listener
    public void onGameReload(GameReloadEvent e) throws IOException, ObjectMappingException {
        reloadConfig();
    }

    public void reloadConfig() throws IOException, ObjectMappingException {
        configHandler=new ConfigHandler(this);
        BroadcastUtil util = new BroadcastUtil();
        if (configHandler.loadConfig()) {mainPluginConfig = configHandler.getPluginConf();}
        if(!(taskList==null)) {
            for (int i = 0; i < taskList.size(); i++) {
                taskList.get(i).cancel();
            }
            util.buildBroadcast();util.buildOtherCasts();
        }
    }

    public GuiceObjectMapperFactory getFactory() {
        return factory;
    }

    public File getConfigDir() {
        return configDir;
    }
}
