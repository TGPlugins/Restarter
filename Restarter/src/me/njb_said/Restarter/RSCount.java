package me.njb_said.Restarter;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class RSCount implements Runnable {

	  public static int count = 61;
	  int id = 0;
	    public RSCount(int t) {
		      RSCount.count = t;
		    }

		    public RSCount()
		    {
		    }
			public void run()
	       {
	         count -= 1;

	         if (count < 6) {
	        	 for (Player p : Bukkit.getOnlinePlayers()) {
	        			p.playSound(p.getLocation(), Sound.LAVA_POP, 60.0F, 0.0F);
	        	 }
	         }
	         if ((count == 20) || (count == 30) || (count == 40) || (count == 50) || (count == 60) || (count == 120) || (count == 180)
	        		 || (count == 240) || (count == 300) || (count == 360) || (count == 420) || (count == 480) || (count == 540) || (count == 600)) {
					Bukkit.getServer().broadcastMessage("§9[§4§oPrison§9] §6Server is restarting in §2§l" + count + " §6seconds.");
	         }
	         
	         if (count == 10) {
		 			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "save-all");
	        	 for (Player p : Bukkit.getOnlinePlayers()) {
	        		 if (p.isOp()) {
	        			 	p.sendMessage("§9[§4§oPrison§9] §6Server has been saved.");
	        		 }	
	        	 	}
	        	 	Bukkit.broadcastMessage("§9[§4§oPrison§9] §6Server is restarting in §2§l" + count + " §6seconds.");
	         	}
	         
	 			if (count < 6 && count > 0) {
	 				Bukkit.getServer().broadcastMessage("  ");
	 				Bukkit.getServer().broadcastMessage("§9[§4§oPrison§9] §6Server is restarting in §2§l" + count + " §6seconds.");
	 			}
	 			
	 			if (count == 0) {
	 				for (Player p : Bukkit.getOnlinePlayers()) {
	 					p.playSound(p.getLocation(), Sound.ANVIL_LAND, 60.0F, 0.0F);
	 						p.kickPlayer("§9" + Main.restarter + " ordered a server restart. \n \n §6Rejoin in a few minutes.");
	 				}
	 				Bukkit.getServer().shutdown();
	 			}	
	       }
			
	}	