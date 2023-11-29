package de.hawk200014.serverdiscordnode.Listeners;

import de.hawk200014.serverdiscordnode.ServerDiscordNodeServer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

public class ServerStartedListener implements ServerLifecycleEvents.ServerStarted{
    @Override
    public void onServerStarted(MinecraftServer server) {
        ServerDiscordNodeServer.mcserver = server;
        ServerDiscordNodeServer.postWorld();
    }

}
