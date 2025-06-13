package com.chyzman.dontdothat.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.network.ClientConnection;
import net.minecraft.text.Text;

public class DummyConnectionScreen extends Screen {
    private final ClientConnection connection;

    public DummyConnectionScreen(ClientConnection connection) {
        super(Text.empty());
        this.connection = connection;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {}

    @Override
    public void tick() {
        if (connection.isOpen()) connection.tick();
        else connection.handleDisconnection();
    }
}
