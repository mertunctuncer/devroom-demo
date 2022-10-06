package dev.peopo.devroomdemo.dungeon

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*

class PreDungeonCache(val player: Player) {

	val inventory = mutableMapOf<Int, ItemStack>()
	val health = player.health
	val exp = player.exp
	val hunger = player.foodLevel
	val location = player.location

	init {
		for(slot in 0..40) {
			player.inventory.getItem(slot)?.let { inventory[slot] = it }
		}
	}

	fun apply() {
		player.inventory.clear()
		for(itemPair in inventory) {
			player.inventory.setItem(itemPair.key, itemPair.value)
		}
		player.health = health
		player.exp = exp
		player.foodLevel = hunger
		player.teleport(location)
	}

	companion object {
		val caches = mutableMapOf<UUID, PreDungeonCache>()
	}
}

fun Player.getCache() = PreDungeonCache.caches[uniqueId]
fun Player.removeCache() = PreDungeonCache.caches.remove(uniqueId)

fun Player.clear() {
	inventory.clear()
	health = 20.0
	exp = 0.0f
	foodLevel = 20
}