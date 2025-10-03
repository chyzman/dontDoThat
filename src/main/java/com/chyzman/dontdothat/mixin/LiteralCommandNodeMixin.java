package com.chyzman.dontdothat.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.brigadier.tree.LiteralCommandNode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LiteralCommandNode.class)
public class LiteralCommandNodeMixin {

    @WrapOperation(
        method = "listSuggestions",
        at = @At(
            value = "INVOKE",
            target = "Ljava/lang/String;startsWith(Ljava/lang/String;)Z"
        ),
        remap = false
    )
    boolean makeSuggestionsMoreLenient(
        String literalLowerCase,
        String remaining,
        Operation<Boolean> original
    ) {
        if (original.call(literalLowerCase, remaining)) return true;
        return literalLowerCase.contains(remaining);
    }
}
