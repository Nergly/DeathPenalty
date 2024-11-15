package vip.floatationdevice.msu.deathpenalty;

import org.bukkit.plugin.java.JavaPlugin;
import vip.floatationdevice.msu.ConfigManager;

public class DPConfigManager extends ConfigManager
{
    boolean setHp, setFood, setXp, setMoney, hpIsPercent;
    int hp, food;
    double hpPercent, xpPercent, moneyPercent;

    public DPConfigManager(JavaPlugin plugin, int version)
    {
        super(plugin, version);
    }

    @Override
    public DPConfigManager initialize()
    {
        super.initialize();

        // Check if deduct hp is enabled, default to false if null
        Boolean hpEnabled = get(Boolean.class, "hp.enabled");
        if (setHp = (hpEnabled != null ? hpEnabled : false))
        {
            String hpSetTo;
            // hp.setTo is a positive integer
            if (is(Integer.class, "hp.setTo") && ((hp = get(Integer.class, "hp.setTo")) > 0))
            {
                hpIsPercent = false;
            }
            // hp.setTo is a String representation between "1%" and "100%"
            else if (is(String.class, "hp.setTo") && (hpSetTo = get(String.class, "hp.setTo")).matches("^(100|[1-9][0-9]?)%$"))
            {
                hpIsPercent = true;
                hpPercent = Integer.parseInt(hpSetTo.substring(0, hpSetTo.length() - 1)) / 100.0;
            }
            else
            {
                throw new RuntimeException("'hp.setTo' must be a positive integer or a string between \"1%\" and \"100%\"");
            }
        }

        // Check if deduct food is enabled, default to false if null
        Boolean foodEnabled = get(Boolean.class, "food.enabled");
        if (setFood = (foodEnabled != null ? foodEnabled : false))
        {
            food = get(Integer.class, "food.setTo") != null ? get(Integer.class, "food.setTo") : 0;
            if (food < 0 || food > 20)
            {
                throw new RuntimeException("'food.setTo' must be an integer between 0 and 20");
            }
        }

        // Check if deduct xp is enabled, default to false if null
        Boolean xpEnabled = get(Boolean.class, "xp.enabled");
        if (setXp = (xpEnabled != null ? xpEnabled : false))
        {
            Number xpSetToPercent = get(Number.class, "xp.setToPercent");
            xpPercent = xpSetToPercent != null ? xpSetToPercent.doubleValue() / 100.0 : 0.0;
            if (xpPercent < 0.0 || xpPercent > 1.0)
            {
                throw new RuntimeException("'xp.setTo' must be between 0 and 100");
            }
        }

        // Check if deduct money is enabled, default to false if null
        Boolean moneyEnabled = get(Boolean.class, "money.enabled");
        if (setMoney = (moneyEnabled != null ? moneyEnabled : false))
        {
            if (EconomyManager.initialize())
            {
                Number moneySetToPercent = get(Number.class, "money.setToPercent");
                moneyPercent = moneySetToPercent != null ? moneySetToPercent.doubleValue() / 100.0 : 0.0;
                if (moneyPercent < 0.0 || moneyPercent > 1.0)
                {
                    throw new RuntimeException("'money.setTo' must be between 0 and 100");
                }
            }
        }

        return this;
    }
}
