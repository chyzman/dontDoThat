package com.chyzman.dontdothat.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.resource.ResourcePackProfile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ResourcePackProfile.class)
public abstract class ResourcePackOrganizerPackMixin {

    //fun fact: required resource packs actually are required
//    @ModifyReturnValue(method = "isRequired", at = @At(value = "RETURN"))
//    private boolean unRequireResourcePacks(boolean original) {
//        return false;
//    }

    @ModifyReturnValue(method = "isPinned", at = @At(value = "RETURN"))
    private boolean unPinResourcePacks(boolean original) {
        return false;
    }
}
