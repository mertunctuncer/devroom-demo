package dev.peopo.devroomdemo.commands

import dev.peopo.devroomdemo.dungeon.Dungeon
import dev.peopo.devroomdemo.util.colorize
import org.bukkit.entity.Player


fun onStartCommand(player: Player) {

	if(Dungeon.activeInstances.containsKey(player.uniqueId)) { player.sendMessage("&cAlready in a dungeon!".colorize()); return }

	val dungeon = Dungeon(player)

}