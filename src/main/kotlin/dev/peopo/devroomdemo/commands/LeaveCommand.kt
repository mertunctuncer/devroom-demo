package dev.peopo.devroomdemo.commands

import dev.peopo.devroomdemo.dungeon.Dungeon
import dev.peopo.devroomdemo.dungeon.dungeon
import dev.peopo.devroomdemo.util.colorize
import org.bukkit.entity.Player


fun onLeaveCommand(player: Player) {
	if (!Dungeon.activeInstances.containsKey(player.uniqueId)) {
		player.sendMessage("&cNot in a dungeon!".colorize()); return
	}
	player.sendMessage("Ending dungeon!")
	player.dungeon?.clear()
}