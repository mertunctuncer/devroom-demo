package dev.peopo.devroomdemo.dungeon

import dev.peopo.devroomdemo.util.config
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import java.util.*

class Dungeon(val player: Player) {

	init {
		activeInstances[player.uniqueId] = this
	}

	val entities = mutableSetOf <Entity>()

	companion object {
		val globalEntities = mutableSetOf<Entity>()
		val activeInstances = mutableMapOf<UUID, Dungeon>()
		val Player.dungeon : Dungeon?
			get() = activeInstances[uniqueId]
	}
}