package io.github.hhservers.bbroadcast.config;

import io.github.hhservers.bbroadcast.util.BroadcastObject;
import io.github.hhservers.bbroadcast.util.RandomBroadcastObject;
import lombok.Data;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.Arrays;
import java.util.List;

@ConfigSerializable @Data
public class MainPluginConfig {

        @Setting(value = "interval", comment = "random broadcast interval")
        private int interval = 420;

        @Setting(value = "broadcastList", comment = "broadcastList")
        private List<BroadcastObject> broadcastList = Arrays.asList(new BroadcastObject());

        @Setting(value = "randomList", comment = "randomList")
        private List<RandomBroadcastObject> randomList = Arrays.asList(new RandomBroadcastObject());

        @Setting(value = "prefix", comment = "Broadcast Prefix")
        private String prefix = "&l&8[&r&aB&dBroadcast&l&8]&r ";


}
