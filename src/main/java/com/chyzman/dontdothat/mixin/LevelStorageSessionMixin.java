package com.chyzman.dontdothat.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.sun.jna.platform.FileUtils;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.io.IOException;
import java.nio.file.FileVisitor;
import java.nio.file.Path;

@Mixin(LevelStorage.Session.class)
public abstract class LevelStorageSessionMixin {
    @Shadow
    public abstract void close() throws IOException;

    @WrapWithCondition(method = "deleteSessionLock", at = @At(value = "INVOKE", target = "Ljava/nio/file/Files;walkFileTree(Ljava/nio/file/Path;Ljava/nio/file/FileVisitor;)Ljava/nio/file/Path;"))
    private boolean rememberThatCompoundPls(
        Path path, FileVisitor<? super Path> visitor
    ) {
        try {
            close();
            FileUtils.getInstance().moveToTrash(path.toFile());
            return false;
        } catch (IOException e) {
            return true;
        }
    }
}
