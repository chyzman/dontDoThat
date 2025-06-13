package com.chyzman.dontdothat.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {
    @Shadow protected TextFieldWidget chatField;

    @ModifyArg(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/TextFieldWidget;setMaxLength(I)V"))
    private int init(int maxLength) {
        return Integer.MAX_VALUE;
    }

    @Inject(method = "onChatFieldUpdate", at = @At(value = "HEAD"))
    private void onChatFieldUpdate(String chatText, CallbackInfo ci) {
        chatField.setMaxLength(chatText.startsWith("/") || chatText.isEmpty() ? Integer.MAX_VALUE : 256);
    }

    @WrapOperation(method = "normalize", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/StringHelper;truncateChat(Ljava/lang/String;)Ljava/lang/String;"))
    private String normalize(String chatText, Operation<String> original) {
        return chatText.startsWith("/") ? chatText : original.call(chatText);
    }
}
