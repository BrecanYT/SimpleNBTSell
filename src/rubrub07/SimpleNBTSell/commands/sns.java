package rubrub07.SimpleNBTSell.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.nbtapi.NBTItem;

import rubrub07.SimpleNBTSell.SimpleNBTSell;

@SuppressWarnings({ "deprecation", "unused" })
public class sns implements CommandExecutor{
	
	private SimpleNBTSell plugin;
	
	public sns(SimpleNBTSell plugin)
	{
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender player, Command cmd, String label, String[] args) {

		Player jugador = (Player) player;
		
		if(Bukkit.getPluginManager().getPlugin("NBTAPI") == null)
		{
			jugador.sendMessage(plugin.name + ChatColor.translateAlternateColorCodes('&', "&c A ocurrido un error no se encuentra la dependencia &aNBTAPI"));
			return false;
		}
		if(Bukkit.getPluginManager().getPlugin("Vault") == null)
		{
			jugador.sendMessage(plugin.name + ChatColor.translateAlternateColorCodes('&', "&c A ocurrido un error no se encuentra la dependencia &aVault"));
			return false;
		}
		
		if(!(player instanceof Player))
		{
			Bukkit.getConsoleSender().sendMessage(plugin.name + ChatColor.BLUE + "No puedes enviar un comando por consola");
			return false;
		}
		else
		{
			String nbttag = plugin.getConfig().getString("config.NBTTAG");
			if(args.length <= 0)
			{
			jugador.sendMessage(plugin.name + ChatColor.translateAlternateColorCodes('&', "&a&m----------------->"));
			jugador.sendMessage(plugin.name + ChatColor.translateAlternateColorCodes('&', "&7Comandos:"));
			jugador.sendMessage(plugin.name + ChatColor.translateAlternateColorCodes('&', "&7sns: muestra este mensaje"));
			jugador.sendMessage(plugin.name + ChatColor.translateAlternateColorCodes('&', "&7sns sellhand: vende el item de tu mano"));
			jugador.sendMessage(plugin.name + ChatColor.translateAlternateColorCodes('&', "&7sns sellallinv: vende todos los items de tu inventario"));
			jugador.sendMessage(plugin.name + ChatColor.translateAlternateColorCodes('&', "&6!!&cAtencion vende todos los items con la opcion de vender&6!!"));
			jugador.sendMessage(plugin.name + ChatColor.translateAlternateColorCodes('&', "&a&m----------------->"));
			return true;
			}
			else if(args.length > 0)
			{
				if(args[0].equalsIgnoreCase("sellallinv"))
				{
					if(jugador.getItemInHand().equals(null)) {jugador.sendMessage(plugin.name + ChatColor.translateAlternateColorCodes('&', " &cEl item no existe o no se puede usar")); return false;}
					if(jugador.getItemInHand().getAmount() <= 0) {jugador.sendMessage(plugin.name + ChatColor.translateAlternateColorCodes('&', " &cEl item no existe o no se puede usar")); return false;}
					ItemStack item = jugador.getItemInHand();
					NBTItem nbt = new NBTItem(item);
					if(nbt.hasKey(nbttag))
					{
						int money = 0;
				        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
				        for(int i = 0; i < 35; i++)
				        {
							if(jugador.getInventory().getItem(i) != null) {
				        		if(jugador.getInventory().getItem(i).getAmount() > 0);{
						        	ItemStack islot = jugador.getInventory().getItem(i);
									NBTItem nbti = new NBTItem(islot);
						        	if(nbti.hasKey(nbttag))
						        	{
							        		int temp = nbti.getInteger(nbttag);
							        		int stacksize = jugador.getInventory().getItem(i).getAmount();
							        		jugador.getInventory().getItem(i).setAmount(0);
							        		money += temp * stacksize;
						        	}
				        		}
							}
				        }
			        	
						
						jugador.sendMessage(plugin.name + ChatColor.translateAlternateColorCodes('&', "&a&m----------------->"));
						jugador.sendMessage(plugin.name + ChatColor.translateAlternateColorCodes('&', "&7Has vendido todos tus items vendibles" ));
						jugador.sendMessage(plugin.name + ChatColor.translateAlternateColorCodes('&', "&7Total ganado : " + String.valueOf(money).toString() ));
						jugador.sendMessage(plugin.name + ChatColor.translateAlternateColorCodes('&', "&a&m----------------->"));
						Bukkit.dispatchCommand(console, plugin.getConfig().getString("config.ECOCMD").replace("%player%", jugador.getName().toString()).replace("%money%", String.valueOf(money)));
						return true;
					} else {return false;}
						

				}
				else if(args[0].equalsIgnoreCase("sellhand"))
				{
					if(jugador.getItemInHand().equals(null)) {jugador.sendMessage(plugin.name + ChatColor.translateAlternateColorCodes('&', " &cEl item no existe o no se puede usar")); return false;}
					if(jugador.getItemInHand().getAmount() <= 0) {jugador.sendMessage(plugin.name + ChatColor.translateAlternateColorCodes('&', " &cEl item no existe o no se puede usar")); return false;}
					org.bukkit.inventory.ItemStack item = jugador.getItemInHand();
					NBTItem nbt = new NBTItem(item);
					if(nbt.hasKey(nbttag))
					{
						int i = nbt.getInteger(nbttag);
						int money = i * jugador.getItemInHand().getAmount(); 
				        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
						
						jugador.sendMessage(plugin.name + ChatColor.translateAlternateColorCodes('&', "&a&m----------------->"));
						jugador.sendMessage(plugin.name + ChatColor.translateAlternateColorCodes('&', "&7Has vendido (" + item.getItemMeta().getDisplayName() + "&7) por un valor unitario de (&6" + String.valueOf(i).toString() + "&7)" ));
						jugador.sendMessage(plugin.name + ChatColor.translateAlternateColorCodes('&', "&7Total ganado : " + String.valueOf(money).toString() ));
						jugador.sendMessage(plugin.name + ChatColor.translateAlternateColorCodes('&', "&a&m----------------->"));
						jugador.getItemInHand().setAmount(0);
						Bukkit.dispatchCommand(console, plugin.getConfig().getString("config.ECOCMD").replace("%player%", jugador.getName().toString()).replace("%money%", String.valueOf(money)));
						return true;
					}
					else
						{
							jugador.sendMessage(plugin.name + ChatColor.translateAlternateColorCodes('&', "&cEl item no tiene un NBT para vender"));
							return false;
						}
					}
				else 
				{
					jugador.sendMessage(plugin.name + ChatColor.translateAlternateColorCodes('&', "&cEl comando no existe"));
					return false;
				}
				} 
				else 
				{
					jugador.sendMessage(plugin.name + ChatColor.translateAlternateColorCodes('&', "&cEl comando no existe"));
					return false;
				}
			}
		}
	}

