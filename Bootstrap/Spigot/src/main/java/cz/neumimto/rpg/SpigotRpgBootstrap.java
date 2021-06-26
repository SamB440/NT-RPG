package cz.neumimto.rpg;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.dependency.Dependency;
import org.bukkit.plugin.java.annotation.dependency.DependsOn;
import org.bukkit.plugin.java.annotation.dependency.SoftDependency;
import org.bukkit.plugin.java.annotation.dependency.SoftDependsOn;
import org.bukkit.plugin.java.annotation.plugin.*;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

@Plugin(name = "NT-RPG", version = "2.1.0-SNAPSHOT-13")
@Description("Complete combat overhaul with classes and skills")
@Author("NeumimTo")
@Website("https://github.com/Sponge-RPG-dev/NT-RPG")
@LogPrefix("NTRPG")
@ApiVersion(ApiVersion.Target.v1_14)
@SoftDependsOn(
        value = {
                @SoftDependency("PlaceholderAPI"),
                @SoftDependency("HolographicDisplays"),
                @SoftDependency("MythicMobs"),
                @SoftDependency("MMOItems"),
                @SoftDependency("RPGRegions")
        }
)
@DependsOn(
        value = {
                @Dependency("NBTAPI"),
                @Dependency("EffectLib"),
                @Dependency("ProtocolLib")
        }
)
public class SpigotRpgBootstrap extends JavaPlugin {

    private NtRpgBootstrap bootstrap;
    private final String jarName = "ntrpg-embed.jar";

    @Override
    public void onEnable() {
        try {
            bootstrap.enable(new NtRpgBootstrap.Data(this, getDataFolder()));
            bootstrap = RpgModuleLayer.getBootstrap(jarName, getClassLoader());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        if (bootstrap != null) {
            bootstrap.disable();
        }
    }
}
