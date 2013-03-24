package me.njb_said.Restarter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	public Boolean isRestarting = false;
	
	public void onEnable() {
		System.out.println(this+" loading..");
	    File configFile = new File(getDataFolder(), "config.yml");
	    if (!configFile.exists()) {
	        configFile.getParentFile().mkdirs();
	        copy(getResource("config.yml"), configFile);
	        System.out.println("File 'config.yml' didn't exist. Created it.");
	      }
		RSCount.count = getConfig().getInt("Time") + 1;
		getServer().getPluginManager().registerEvents(this, this);
		System.out.println(this+" loaded and enabled.");
	}
	
	public void onDisable() {
		stoprsCount();
		System.out.println(this+" disabled.");
	}

	public static void copy(InputStream in, File file) {
		try {
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String restarter = "";

	@EventHandler
	public void onMotdPing(ServerListPingEvent e) {
		if (isRestarting) {
			e.setMotd("§9[§4§oPrison§9]  §6Restarting in " + RSCount.count + ".");
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	   {
		if(sender instanceof Player) {
			
			Player p = (Player)sender;
	     if (cmd.getName().equalsIgnoreCase("restart")) {
	    	 if (p.isOp() || p.hasPermission("prison.warden")) {
	    	 if (args.length == 0) {
	    		 p.sendMessage("§9[§4§oPrison§9] §6You have ordered a restart.");
	    		 Main.restarter = p.getName();
	    		 Bukkit.getServer().broadcastMessage("§9[§4§oPrison§9] §2" + Main.restarter + " §3ordered a server restart.");
	    		 isRestarting = true;
	    		 stoprsCount();
	    		 RSCount.count = getConfig().getInt("Time") + 1;
	    		 startrsCount();
	    	 } else {
	    		 p.sendMessage("§9[§4§oPrison§9] §cWrong Argument(s) §bUsage: §6/restart");
	    	 }
	    	 } else {
	    		 p.sendMessage("§9[§4§oPrison§9] §cI dont trust you, so you cant do that!");
	    	 }
	     } else {
	    	 sender.sendMessage("Invalid Sender (Player Required");
	     }
		}
	    return false;
	  }
	
		   public static void startrsCount() {
		    	stoprsCount();
		    	
		    	rsCount = new RSCount();
		    	rsCount.id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(new Main(), rsCount, 0L, 20L);
		  	}
		
		  	public static void startrsCount(int i) {
		  		stoprsCount();
		  		
		  		rsCount = new RSCount(i);
		  		rsCount.id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(new Main(), rsCount, 20L, 20L);
		  	}
		  	
		  	public static void stoprsCount() {
		  		if (rsCount != null) {
		  			Bukkit.getServer().getScheduler().cancelTask(rsCount.id);
		  			rsCount = null;
		  		}
		  	}
		  	public static RSCount rsCount = null;
		  	
		  	
		  	@EventHandler
		  	public void preLogin(PlayerLoginEvent e) {
		  		if (isRestarting) {
		  			e.setKickMessage("§6Server is " + RSCount.count + " seconds from restart. \n \n §bTry in a few minutes.");
		  			e.disallow(PlayerLoginEvent.Result.KICK_OTHER, e.getKickMessage());
		  		}
		  	}
		  	
	}