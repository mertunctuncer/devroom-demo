package dev.peopo.devroomdemo.dungeon

import dev.peopo.devroomdemo.dungeon.enemy.DungeonZombie
import dev.peopo.devroomdemo.sql.getDungeonStats
import dev.peopo.devroomdemo.util.config
import dev.peopo.devroomdemo.util.plugin
import dev.peopo.devroomdemo.util.pluginManager
import net.minecraft.world.entity.Entity
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityTargetEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import java.util.*

class Dungeon(val player: Player) {

	init {
		activeInstances[player.uniqueId] = this
		player.getDungeonStats()!!.sessions += 1
		player.teleport(config.spawnLocation)
	}

	val spawnTask = Bukkit.getScheduler().runTaskTimer(plugin, Runnable {
		val customZombie = DungeonZombie(player)
		customZombie.setTarget((player as CraftPlayer).handle, EntityTargetEvent.TargetReason.CUSTOM, true)
	}, 20*5, 20)

	val entityIds = mutableSetOf<Int>()
	val entityInstances = mutableSetOf<Entity>()

	fun clear() {
		for(entity in entityInstances) {
			entity.remove(Entity.RemovalReason.DISCARDED)
			dungeonEntities.remove(entity.id)
		}
		activeInstances.remove(player.uniqueId)
		spawnTask.cancel()
		player.getCache()?.apply()
		player.removeCache()
	}

	fun giveItems() {
		player.inventory.setItem(0, ItemStack(Material.IRON_SWORD))
		player.inventory.setItem(1, ItemStack(Material.GOLDEN_APPLE, 3))
		player.inventory.setItem(EquipmentSlot.CHEST, ItemStack(Material.LEATHER_CHESTPLATE))
		player.inventory.setItem(EquipmentSlot.HEAD, ItemStack(Material.LEATHER_HELMET))
		player.inventory.setItem(EquipmentSlot.LEGS, ItemStack(Material.LEATHER_LEGGINGS))
		player.inventory.setItem(EquipmentSlot.FEET, ItemStack(Material.LEATHER_BOOTS))
	}

	companion object : Listener {
		init { pluginManager.registerEvents(this, plugin) }

		val dungeonEntities = mutableSetOf<Int>()
		val activeInstances = mutableMapOf<UUID, Dungeon>()
		val Player.dungeon : Dungeon?
			get() = activeInstances[uniqueId]

		@EventHandler
		fun onPlayerDamage(event: EntityDamageEvent) {
			if(event.entity !is Player) return
			val player = event.entity as Player
			val dungeon = player.dungeon ?: return

			if((player.health - event.finalDamage) <= 0) {
				event.isCancelled = true
				player.getDungeonStats()!!.deaths += 1
				player.sendMessage("You died!")
				dungeon.clear()
			}
		}

		@EventHandler
		fun onPlayerDeath(event: PlayerDeathEvent) {
			val dungeon = event.player.dungeon ?: return
			event.drops.clear()
			event.droppedExp = 0
			dungeon.clear()
		}

		@EventHandler
		fun onPlayerQuit(event: PlayerQuitEvent) {
			val dungeon = event.player.dungeon ?: return
			dungeon.clear()
		}
	}
}

