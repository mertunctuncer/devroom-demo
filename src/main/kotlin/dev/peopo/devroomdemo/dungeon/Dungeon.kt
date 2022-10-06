package dev.peopo.devroomdemo.dungeon

import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import java.util.*

class Dungeon(val player: Player) {

	init {
		activeInstances[player.uniqueId] = this

	}

	val entityInstances = mutableSetOf <Entity>()
	val entityIds = mutableSetOf<Int>()

	companion object {
		/*
		init {
			pluginManager.registerEvents(object: Listener {
				@EventHandler
					removeEntity(e.getEntity(), true)
				}

				@EventHandler
				fun onChunkUnload(e: ChunkUnloadEvent) {
					for (entity in e.getChunk().getEntities()) {
						removeEntity(entity, false)
					}
				}

				@EventHandler
				fun onPlayerQuit(e: PlayerQuitEvent) {
					removePlayer(e.getPlayer())
				}

			}, plugin)
		}
		 */
		val globalEntities = mutableSetOf<Int>()
		val activeInstances = mutableMapOf<UUID, Dungeon>()
		val Player.dungeon : Dungeon?
			get() = activeInstances[uniqueId]
	}
}

