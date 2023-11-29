package de.hawk200014.serverdiscordnode;

import de.hawk200014.serverdiscordnode.Listeners.ServerStartedListener;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ServerDiscordNodeServer implements DedicatedServerModInitializer {
    public static MinecraftServer mcserver;
    public static RequestProcessor rp;
    public static SocketManager socketManager;
    public static Config config;
    public static Path filepath;
    @Override
    public void onInitializeServer() {
        String configpath = FabricLoader.getInstance().getConfigDir().toAbsolutePath() + "mods/ServerDiscordNode/config.properties";
        Path moddir = Paths.get(FabricLoader.getInstance().getConfigDir().toAbsolutePath() + "mods/ServerDiscordNode/");
        filepath = Paths.get(configpath);
        config = new Config();
        if(Files.exists(filepath)) {
            ServerLifecycleEvents.SERVER_STARTED.register(new ServerStartedListener());
        }
        else{
            try {
                Files.createDirectories(moddir);
                Files.createFile(filepath);
                config.setInitValues(new File(filepath.toUri()));
                System.out.println("Mod is Disabled, set values into the config file");
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void postWorld(){
        File configFile = new File(filepath.toUri());
        rp = new RequestProcessor();
        config.load(configFile);
        socketManager = new SocketManager(config);
        if(socketManager.initConnectionAndListener()){
            System.out.println("Mod is initialized");
        }
        else{
            System.out.println("Mod is not initialized!");
        }
    }
}
