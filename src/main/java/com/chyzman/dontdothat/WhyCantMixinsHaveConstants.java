package com.chyzman.dontdothat;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class WhyCantMixinsHaveConstants {
    public static final Multimap<InputUtil.Key, KeyBinding> REAL_KEYS_MAP = HashMultimap.create();

}
