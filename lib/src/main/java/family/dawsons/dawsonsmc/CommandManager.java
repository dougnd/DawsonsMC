package family.dawsons.dawsonsmc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandManager implements CommandExecutor {
  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    boolean success = false;
    if (!sender.isOp() && DawsonsMC.config.getBoolean("require_op")) {
      sender.sendMessage("You must be an op to use this command.");
      return success;
    }

    if (command.getLabel().equalsIgnoreCase("mobgriefing")) {
      if (args.length == 0) {
        sender.sendMessage("Usage: /mobgriefing <minecraft:name> <true/false>");
        sender.sendMessage("Example: /mobgriefing creeper false");
        sender.sendMessage("Example: /mobgriefing creeper");
        success = true;
      }

      String entityName = args[0];
      boolean isInConfig = DawsonsMC.config.contains(entityName + "_griefing");

      if (!isInConfig) {
        sender.sendMessage(entityName + " is not a valid entity.");
        return success;
      }

      if (args.length == 1) {
        boolean allowedToGrief = DawsonsMC.config.getBoolean(entityName + "_griefing");
        sender.sendMessage(
            entityName + " griefing is " + (allowedToGrief ? "enabled" : "disabled"));
        success = true;
      }

      if (args.length == 2) {
        boolean allowedToGrief = Boolean.parseBoolean(args[1]);
        DawsonsMC.config.set(entityName + "_griefing", allowedToGrief);
        DawsonsMC.config.options().copyDefaults(true);
        success = true;
      }
    }
    return success;
  }
}
