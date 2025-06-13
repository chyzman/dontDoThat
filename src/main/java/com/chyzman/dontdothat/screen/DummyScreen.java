package com.chyzman.dontdothat.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class DummyScreen extends Screen {
    public DummyScreen() {
        super(Text.empty());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {}

    @Override
    public void tick() {
        this.close();
    }
}
