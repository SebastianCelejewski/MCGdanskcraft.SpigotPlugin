package pl.sebcel.minecraft.gdanskcraft;

import java.util.Collection;
import java.util.Date;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SavePlayersLocationTask implements Runnable {

	private final SpigotPlugin plugin;

    public SavePlayersLocationTask(SpigotPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
    	plugin.getLogger().info("Scheduled task starting");
    	
        Date now = new Date();

        Collection<? extends Player> players = plugin.getServer().getOnlinePlayers();
        for (Player player : players) {
            Location playerLocation = player.getLocation();
            String playerName = player.getDisplayName();
            int x = playerLocation.getBlockX();
            int y = playerLocation.getBlockY();
            int z = playerLocation.getBlockZ();
            int dimension = playerLocation.getWorld().getEnvironment().ordinal();
            sendMessageToBungeeCord(playerName, now, dimension, x, y, z);
        }
    }

    private void sendMessageToBungeeCord(String playerName, Date date, int dimension, int x, int y, int z) {
    	String message = playerName + "," + dimension + "," + x + "," + y + "," + z;
    	plugin.getLogger().info("Sending message to BungeeCord: " + message);
    	this.plugin.sendMessage(message);
    }

}