package dev.peopo.devroomdemo.commands

import dev.peopo.devroomdemo.dungeon.Dungeon
import dev.peopo.devroomdemo.dungeon.PreDungeonCache
import dev.peopo.devroomdemo.dungeon.clear
import dev.peopo.devroomdemo.util.colorize
import org.bukkit.entity.Player


fun onStartCommand(player: Player) {

	if (Dungeon.activeInstances.containsKey(player.uniqueId)) {
		player.sendMessage("&cAlready in a dungeon!".colorize()); return
	}

	player.sendMessage("Starting the dungeon!")
	val cache = PreDungeonCache(player)
	PreDungeonCache.caches[player.uniqueId] = cache
	player.clear()

	val dungeon = Dungeon(player)
	dungeon.giveItems()
}