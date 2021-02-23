package pl.sebcel.minecraft.gdanskcraft;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class SpigotPlugin extends JavaPlugin implements PluginMessageListener {

	public void onEnable() {
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "gdanskcraft:main");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "gdanskcraft:main", this);
        this.getServer().getScheduler().runTaskTimerAsynchronously(this, new SavePlayersLocationTask(this), 20 * 5, 5 * 60);
    }
 
    public void onDisable() {
   
    }
 
    public void sendMessage(String message) {
    	try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(stream);
            out.writeUTF(message);
     
            this.getServer().getOnlinePlayers().forEach(x -> x.sendPluginMessage(this, "gdanskcraft:main", stream.toByteArray()));
    	} catch (Exception ex) {
    		this.getLogger().severe(ex.getMessage());
    	}
    }
 
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
    	this.getLogger().info("Plugin message received from channel " + channel + " and player " + player.getName());
        if (!channel.equals("MCGdanskcraftPlugin")) {
            return;
        }
   
        try {
            ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
            DataInputStream in = new DataInputStream(stream);
            this.getLogger().info(in.readUTF());
        } catch (Exception ex) {
        	this.getLogger().severe(ex.getMessage());
        }
    }
}