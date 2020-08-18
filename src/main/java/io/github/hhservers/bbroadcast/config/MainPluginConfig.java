package io.github.hhservers.bbroadcast.config;

import lombok.Data;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.Arrays;
import java.util.List;

@ConfigSerializable @Data
public class MainPluginConfig {

        @Setting(value = "broadcastList", comment = "broadcastList")
        private List<BroadcastObject> broadcastList = Arrays.asList(new BroadcastObject());


}
