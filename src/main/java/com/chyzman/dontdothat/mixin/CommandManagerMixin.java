package com.chyzman.dontdothat.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReceiver;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(CommandManager.class)
public class CommandManagerMixin {

    @ModifyExpressionValue(method = "execute", at = @At(value = "FIELD", target = "Lnet/minecraft/SharedConstants;isDevelopment:Z"))
    boolean enableCommandExceptions(boolean original) {
        return true;
    }

    @WrapWithCondition(method = "execute", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/command/ServerCommandSource;sendError(Lnet/minecraft/text/Text;)V", ordinal = 1))
    boolean butDontIncludeTheExtraErrorInChat(ServerCommandSource instance, Text message) {
        return false;
    }
}
