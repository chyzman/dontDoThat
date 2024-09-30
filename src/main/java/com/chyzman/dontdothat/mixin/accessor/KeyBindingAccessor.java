package com.chyzman.dontdothat.mixin.accessor;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(KeyBinding.class)
public interface KeyBindingAccessor {

    @Accessor("timesPressed")
    int dontDoThat$getTimesPressed();
    @Accessor("timesPressed")
    void dontDoThat$setTimesPressed(int value);

    @Accessor("boundKey")
    InputUtil.Key dontDoThat$getBoundKey();

}
