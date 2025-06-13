package com.chyzman.dontdothat.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.gui.hud.ChatHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatHud.class)
public class ChatHudMixin {
    @Inject(method = "clear", at = @At("HEAD"), cancellable = true)
    private void butWhatIfWeDidntClearChatHistoryTho(boolean clearHistory, CallbackInfo ci) {
        if (clearHistory) ci.cancel();
    }

    @ModifyExpressionValue(method = {"addVisibleMessage", "addMessage(Lnet/minecraft/client/gui/hud/ChatHudLine;)V"}, at = @At(value = "CONSTANT", args = "intValue=100"))
    private int whatIfLonger(int hundred) {
        return 65536;
    }
}
