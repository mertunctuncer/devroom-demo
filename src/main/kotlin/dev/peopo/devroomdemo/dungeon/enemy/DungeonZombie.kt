package dev.peopo.devroomdemo.dungeon.enemy


import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent
import dev.peopo.devroomdemo.dungeon.Dungeon
import dev.peopo.devroomdemo.dungeon.NoValidPositionException
import dev.peopo.devroomdemo.dungeon.dungeon
import dev.peopo.devroomdemo.dungeon.getMobSpawnPosition
import dev.peopo.devroomdemo.sql.getDungeonStats
import dev.peopo.devroomdemo.util.plugin
import dev.peopo.devroomdemo.util.pluginManager
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.monster.Zombie
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftZombie
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.EntityTargetLivingEntityEvent

class DungeonZombie(val player: Player) : Zombie(EntityType.ZOMBIE, (player.location.world as CraftWorld).handle) {

	init {
		val location = getMobSpawnPosition(10, 15, player) ?: throw NoValidPositionException()
		Dungeon.dungeonEntities.add(this.id)
		player.dungeon?.entityIds?.add(this.id)
		player.dungeon?.entityInstances?.add(this)
		this.setPos(location.x, location.y, location.z)
		(player.location.world as CraftWorld).handle.addFreshEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM)
	}

	companion object : Listener {
		init {
			pluginManager.registerEvents(this, plugin)
		}

		@EventHandler
		fun onTargetChange(event: EntityTargetLivingEntityEvent) {
			val entity = (event.entity as? CraftZombie)?.handle as? DungeonZombie ?: return
			if (event.target != entity.player as LivingEntity) event.isCancelled = true
		}

		@EventHandler
		fun onEntityDespawn(event: EntityRemoveFromWorldEvent) {
			val entity = (event.entity as? CraftZombie)?.handle as? DungeonZombie ?: return
			entity.player.dungeon?.entityIds?.remove(entity.id)
			Dungeon.dungeonEntities.remove(entity.id)
		}

		@EventHandler
		fun onEntityDeath(event: EntityDeathEvent) {
			val entity = (event.entity as? CraftZombie)?.handle as? DungeonZombie ?: return
			event.drops.clear()
			event.droppedExp = 0
			entity.player.getDungeonStats()!!.kills += 1
			entity.player.dungeon?.entityIds?.remove(entity.id)
			Dungeon.dungeonEntities.remove(entity.id)
		}
	}
}
