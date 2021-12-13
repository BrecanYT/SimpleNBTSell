package rubrub07.SimpleNBTSell;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;
import rubrub07.SimpleNBTSell.commands.sns;
public class SimpleNBTSell extends JavaPlugin {

    public static SimpleNBTSell SimpleNBTSell;
	private Economy econ;
	PluginDescriptionFile pdffile = getDescription();
	public String rutaconf;
	public String version = pdffile.getVersion();
	public String name = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.prefix"));
	
	public void onEnable()
	{
		Bukkit.getConsoleSender().sendMessage(name + ChatColor.BLUE + " Plugin encendido (version: " + version + " )");
		registrarCommandos();
		registrarConfig();
		getEconomy();
	}
	
    public Economy getEconomy() {
        return econ;
    }
	
	public void onDisable()
	{
		Bukkit.getConsoleSender().sendMessage(name + ChatColor.BLUE + " Plugin desactivado (version: " + version + " )");
	}
	
	public void registrarCommandos()
	{
		this.getCommand("sns").setExecutor(new sns(this));
		this.getCommand("sns").setTabCompleter(new sns(this));
	}
	
	
	public void registrarConfig()
	{
		File config = new File(this.getDataFolder(),"config.yml");
		rutaconf = config.getPath();
		if(!config.exists())
		{
			this.getConfig().options().copyDefaults(true);
			saveConfig();
		}
	}

}
