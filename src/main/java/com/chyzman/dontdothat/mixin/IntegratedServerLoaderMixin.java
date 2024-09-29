package com.chyzman.dontdothat.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.serialization.Lifecycle;
import net.minecraft.server.integrated.IntegratedServerLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(IntegratedServerLoader.class)
public class IntegratedServerLoaderMixin {
    @ModifyExpressionValue(method = "checkBackupAndStart", at = @At(value = "INVOKE", target = "Lcom/mojang/serialization/Lifecycle;stable()Lcom/mojang/serialization/Lifecycle;"))
    private Lifecycle shutUpIKnowThereAreDragons(Lifecycle original) {
        return Lifecycle.stable();
    }

    @ModifyExpressionValue(method = "tryLoad", at = @At(value = "INVOKE", target = "Lcom/mojang/serialization/Lifecycle;stable()Lcom/mojang/serialization/Lifecycle;"))
    private static Lifecycle shutUpIKnowThereAreDragonsButThisTimeItsStatic(Lifecycle original) {
        return Lifecycle.stable();
    }
}
