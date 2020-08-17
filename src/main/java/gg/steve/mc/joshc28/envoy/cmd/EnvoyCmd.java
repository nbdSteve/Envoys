package gg.steve.mc.joshc28.envoy.cmd;

import gg.steve.mc.joshc28.envoy.cmd.subs.*;
import gg.steve.mc.joshc28.envoy.framework.cmd.MainCommand;
import gg.steve.mc.joshc28.envoy.framework.cmd.misc.HelpSubCmd;
import gg.steve.mc.joshc28.envoy.framework.cmd.misc.ReloadSubCmd;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class EnvoyCmd extends MainCommand {

    public EnvoyCmd() {
        addSubCommand(new TimeSubCmd(), true);
        addSubCommand(new SetSubCmd(), false);
        addSubCommand(new StartSubCmd(), false);
        addSubCommand(new StopSubCmd(), false);
        addSubCommand(new HelpSubCmd(), false);
        addSubCommand(new ReloadSubCmd(), false);
        addSubCommand(new GiveSubCmd(), false);
        addSubCommand(new AddSubCmd(), false);
        addSubCommand(new ViewSubCmd(), false);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return onCommand(sender, args);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return onTabComplete(args);
    }
}
