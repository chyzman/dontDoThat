package com.chyzman.dontdothat.mixin;

import com.chyzman.dontdothat.screen.DummyConnectionScreen;
import com.chyzman.dontdothat.screen.DummyScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen;
import net.minecraft.client.gui.screen.ReconfiguringScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.util.Window;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Shadow public abstract @Nullable ClientPlayNetworkHandler getNetworkHandler();

    @ModifyVariable(method = "setScreen", at = @At(value = "HEAD"), argsOnly = true)
    private Screen immediatelyCloseLoadingScreen(Screen screen) {
        if (screen instanceof DownloadingTerrainScreen) return new DummyScreen();
        if (screen instanceof ReconfiguringScreen) return new DummyConnectionScreen(getNetworkHandler().getConnection());
        return screen;
    }
}
