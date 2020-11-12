package me.minhcrafters.lprankcommand.expansion;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

public class Placeholders extends PlaceholderExpansion {
    @Override
    public String getIdentifier() {
        return "";
    }

    @Override
    public String getAuthor() {
        return "MinhCrafters";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier){

        // %example_placeholder1%
        if(identifier.equals("player")){
            return "%luckperms_%";
        }

        // %example_placeholder2%
        if(identifier.equals("placeholder2")){
            return "placeholder2 works";
        }

        // We return null if an invalid placeholder (f.e. %example_placeholder3%)
        // was provided
        return null;
    }
}
