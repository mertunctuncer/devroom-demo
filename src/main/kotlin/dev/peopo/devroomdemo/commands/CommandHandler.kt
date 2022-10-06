package dev.peopo.devroomdemo.commands

import dev.peopo.devroomdemo.util.javaPlugin
import org.bukkit.command.CommandExecutor
import org.bukkit.entity.Player


fun registerCommandListeners() {
	javaPlugin.getCommand("start")?.setExecutor(CommandExecutor { sender, _, _, _ ->
		onStartCommand((sender as? Player) ?: return@CommandExecutor true)
		return@CommandExecutor true
	})
	javaPlugin.getCommand("stats")?.setExecutor(CommandExecutor { sender, _, _, _ ->
		onStatsCommand((sender as? Player) ?: return@CommandExecutor true)
		return@CommandExecutor true
	})
	javaPlugin.getCommand("leave")?.setExecutor(CommandExecutor { sender, _, _, _ ->
		onLeaveCommand((sender as? Player) ?: return@CommandExecutor true)
		return@CommandExecutor true
	})
}