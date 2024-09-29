package com.chyzman.dontdothat.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin {
    @Unique private final Map<String, String> unknown = new HashMap<>();

    @Shadow protected abstract void accept(GameOptions.Visitor visitor);

    @Inject(method = "load", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/GameOptions;update(Lnet/minecraft/nbt/NbtCompound;)Lnet/minecraft/nbt/NbtCompound;"))
    private void rememberThatCompoundPls(
            CallbackInfo ci,
            @Local() NbtCompound compound,
            @Share("data") LocalRef<NbtCompound> data
    ) {
        data.set(compound);
    }

    @Inject(method = "load", at = @At("TAIL"))
    private void removeTheRealOptionsFromThatCompoundFromEarlier(
            CallbackInfo ci,
            @Share("data") LocalRef<NbtCompound> data
    ) {
        var keys = data.get().getKeys();
        keys.remove("version");
        this.accept(new GameOptions.Visitor() {

            @Override
            public <T> void accept(String key, SimpleOption<T> option) {
                keys.remove(key);
            }

            @Override
            public int visitInt(String key, int current) {
                keys.remove(key);
                return current;
            }

            @Override
            public boolean visitBoolean(String key, boolean current) {
                keys.remove(key);
                return current;
            }

            @Override
            public String visitString(String key, String current) {
                keys.remove(key);
                return current;
            }

            @Override
            public float visitFloat(String key, float current) {
                keys.remove(key);
                return current;
            }

            @Override
            public <T> T visitObject(String key, T current, Function<String, T> decoder, Function<T, String> encoder) {
                keys.remove(key);
                return current;
            }
        });
        keys.forEach(s -> unknown.put(s, data.get().getString(s)));
    }

    @Inject(method = "write", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/GameOptions;accept(Lnet/minecraft/client/option/GameOptions$Visitor;)V"))
    private void putTheOptionsBackInTheFile(
            CallbackInfo ci,
            @Local() PrintWriter writer
    ) {
        unknown.forEach((key, value) -> writer.println(key + ":" + value));
    }
}
