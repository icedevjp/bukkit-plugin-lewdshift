package pl.icedev.mcshift;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class LewdPlugin extends JavaPlugin implements Listener {

	boolean newVersionAvailable = false;
	String newVersionString = null;

	Posuwanie posuwanie;

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);

		posuwanie = new Posuwanie(this);
	}

}
