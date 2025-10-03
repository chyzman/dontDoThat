package com.chyzman.dontdothat.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.client.gui.screen.ChatInputSuggestor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Comparator;
import java.util.List;

@Mixin(ChatInputSuggestor.class)
public class ChatInputSuggestorMixin {

    @Unique
    private static final Comparator<Pair<Suggestion, Integer>> SUGGESTION_COMPARATOR = Comparator
        .<Pair<Suggestion, Integer>,Integer>comparing(Pair::right, Comparator.nullsLast(Comparator.naturalOrder()))
        .thenComparingInt(p -> p.left().getText().length())
        .thenComparing(p -> p.left().getText());

    @WrapMethod(method = "getStartOfCurrentWord")
    private static int removeSlash(String input, Operation<Integer> original) {
        var index = original.call(input);
        return index != 0 || !input.startsWith("/") ? index : 1;
    }

    @Inject(
        method = "sortSuggestions",
        at = @At(
            value = "INVOKE",
            target = "Lcom/mojang/brigadier/suggestion/Suggestions;getList()Ljava/util/List;"
        ),
        cancellable = true
    )
    void suggestBetter(
        Suggestions suggestions,
        CallbackInfoReturnable<List<Suggestion>> cir,
        @Local(ordinal = 1) String input
    ) {
        if (input.isEmpty()) return;
        cir.setReturnValue(
            suggestions
                .getList()
                .stream()
                .map(s -> {
                    var index = s.getText().indexOf(input);
                    return Pair.of(s, index < 0 ? null : index);
                })
                .sorted(SUGGESTION_COMPARATOR)
                .map(Pair::left)
                .toList()
        );
    }
}
