package io.github.hhservers.bbroadcast.util;

import lombok.Data;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable @Data
public class RandomBroadcastObject {

    @Setting("world")
    private String world = "world";

    @Setting("message")
    private String message = "placeholder";

    @Setting("title")
    private Boolean title = false;

    @Setting("subtitle")
    private String subtitle;

}
