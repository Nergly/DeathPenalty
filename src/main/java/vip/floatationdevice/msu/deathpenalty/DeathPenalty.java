package vip.floatationdevice.msu.deathpenalty;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class DeathPenalty extends JavaPlugin
{
    static DeathPenalty instance;
    static Logger log;
    static DPConfigManager cm;

    @Override
    public void onEnable()
    {
        saveDefaultConfig(); // Ensures the config file exists with default values if it's missing
        reloadConfig();      // Reloads the configuration to ensure all values are up-to-date

        instance = this;
        log = getLogger();
        cm = new DPConfigManager(this, 1).initialize();

        getServer().getPluginManager().registerEvents(new RespawnEventListener(), this);

        log.info("DeathPenalty loaded");
    }


    @Override
    public void onDisable()
    {
        log.info("DeathPenalty is being disabled");
    }
}
