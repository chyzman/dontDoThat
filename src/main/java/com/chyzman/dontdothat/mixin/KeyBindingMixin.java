package com.chyzman.dontdothat.mixin;

import com.chyzman.dontdothat.mixin.accessor.KeyBindingAccessor;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

import static com.chyzman.dontdothat.WhyCantMixinsHaveConstants.REAL_KEYS_MAP;

@Mixin(KeyBinding.class)
public class KeyBindingMixin {
    @Shadow
    @Final
    private static Map<InputUtil.Key, KeyBinding> KEY_TO_BINDINGS;

    @Shadow private InputUtil.Key boundKey;

    @Shadow @Final private static Map<String, KeyBinding> KEYS_BY_ID;

    @Inject(method = "onKeyPressed", at = @At(value = "HEAD"), cancellable = true)
    private static void forwardOnKeyPressedToTheMultiMap(
            InputUtil.Key key,
            CallbackInfo ci
    ) {
        REAL_KEYS_MAP.get(key).forEach(keyBinding -> ((KeyBindingAccessor) keyBinding).dontDoThat$setTimesPressed(((KeyBindingAccessor) keyBinding).dontDoThat$getTimesPressed() + 1));
        ci.cancel();
    }

    @Inject(method = "setKeyPressed", at = @At(value = "HEAD"), cancellable = true)
    private static void forwardSetKeyPressedToTheMultiMap(
            InputUtil.Key key,
            boolean pressed,
            CallbackInfo ci
    ) {
        REAL_KEYS_MAP.get(key).forEach(keyBinding -> keyBinding.setPressed(pressed));
        ci.cancel();
    }

    @Inject(method = "updateKeysByCode", at = @At(value = "TAIL"))
    private static void forwardUpdateByCodeToMultiMap(CallbackInfo ci) {
        REAL_KEYS_MAP.clear();
        for (KeyBinding keyBinding : KEYS_BY_ID.values()) {
            REAL_KEYS_MAP.put(((KeyBindingAccessor)keyBinding).dontDoThat$getBoundKey(), keyBinding);
        }
    }

    @Inject(method = "<init>(Ljava/lang/String;Lnet/minecraft/client/util/InputUtil$Type;ILjava/lang/String;)V", at = @At(value = "TAIL"))
    private void forwardPutToMultiMap(
            String translationKey,
            InputUtil.Type type,
            int code,
            String category,
            CallbackInfo ci
    ) {
        REAL_KEYS_MAP.put(boundKey, (KeyBinding) (Object) this);
    }
}
