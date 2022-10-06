package dev.peopo.devroomdemo.dungeon

import dev.peopo.devroomdemo.util.plugin
import org.bukkit.Bukkit
import org.bukkit.entity.Player

fun hidePlayers(player: Player) {
	for (uuid in Dungeon.activeInstances.keys) {
		val dungeonPlayer = Bukkit.getPlayer(uuid)
		dungeonPlayer?.let {
			player.hidePlayer(plugin, it)
			it.hidePlayer(plugin, player)
		}
	}
}

fun showPlayers(player: Player) {
	for (uuid in Dungeon.activeInstances.keys) {
		val dungeonPlayer = Bukkit.getPlayer(uuid)
		dungeonPlayer?.let {
			player.showPlayer(plugin, it)
			it.showPlayer(plugin, player)
		}
	}
}