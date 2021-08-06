package com.cocomon3448.blockch.blockchallenge;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class LoadMain extends JavaPlugin implements Listener{
    @Override
    public void onEnable() {
        // TODO Auto-generated method stub
//		super.onEnable();
        System.out.println("플러그인이 활성화되었습니다.");
        Bukkit.getPluginManager().registerEvents((Listener) new EventLoader(), this);

    }
    @Override
    public void onDisable() {
        // TODO Auto-generated method stub
//		super.onDisable();
        System.out.println("플러그인이 비활성화되었습니다.");
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // TODO Auto-generated method stub
        if(command.getName().equalsIgnoreCase("me")) {
            Player p = (Player) sender;
            String name = p.getName().toString();
            p.sendMessage(name);
        }

        return false;
    }

}
