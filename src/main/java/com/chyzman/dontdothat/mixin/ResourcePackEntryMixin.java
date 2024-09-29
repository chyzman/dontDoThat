package com.chyzman.dontdothat.mixin;

import net.minecraft.client.gui.screen.pack.PackListWidget;
import net.minecraft.resource.ResourcePackCompatibility;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PackListWidget.ResourcePackEntry.class)
public abstract class ResourcePackEntryMixin {

    @Redirect(method = "enable", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourcePackCompatibility;isCompatible()Z"))
    private boolean shutUpIKnowItsIncompatible(ResourcePackCompatibility instance) {
        return true;
    }
}
