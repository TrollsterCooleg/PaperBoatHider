package me.cooleg.paperboathider;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.BooleanArgument;
import dev.jorel.commandapi.arguments.EntitySelectorArgument;
import dev.jorel.commandapi.arguments.LocationArgument;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class PaperBoatHider extends JavaPlugin {

    private BoatListeners listeners;

    @Override
    public void onEnable() {
        registerCommands();

        listeners = new BoatListeners(this);
        Bukkit.getPluginManager().registerEvents(listeners, this);
        Bukkit.getPluginManager().registerEvents(new PersistenceListeners(this), this);
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            listeners.showAll(player);
        }
    }

    public void startRunnable() {
        listeners.setHiding(true);
    }

    public void stopRunnable() {
        listeners.setHiding(false);
    }

    public void registerCommands() {
        new CommandAPICommand("mcm.boathider")
                .withPermission(CommandPermission.OP)
                .withSubcommands(
                        new CommandAPICommand("hidingboats")
                                .withArguments(new BooleanArgument("hide"))
                                .executes((commandSender, commandArguments) -> {
                                    boolean hide = (Boolean) commandArguments.get("hide");
                                    if (hide) {startRunnable(); return;}
                                    stopRunnable();
                                }),
                        new CommandAPICommand("forceinboat")
                                .withArguments(new EntitySelectorArgument.OnePlayer("player"))
                                .executes(((commandSender, commandArguments) -> {
                                    Player player = (Player) commandArguments.get("player");
                                    Location location = player.getLocation();
                                    location.setYaw(player.getEyeLocation().getYaw());
                                    NmsJunk.spawnBoat(location).addPassenger(player);
                                })),
                        new CommandAPICommand("spawnboat")
                                .withArguments(new LocationArgument("location"))
                                .executes(((commandSender, commandArguments) -> {
                                    Location location = (Location) commandArguments.get("location");
                                    NmsJunk.spawnBoat(location);
                                }))
                ).register();
    }

}
