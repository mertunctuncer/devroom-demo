package dev.peopo.devroomdemo.dungeon.enemy


import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.monster.Zombie
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityTargetEvent

class DungeonZombie(location: Location, player: Player) : Zombie(EntityType.ZOMBIE, (location.world as CraftWorld).handle) {

	init {
		this.setPos(location.x, location.y, location.z)
		this.setTarget((player as CraftPlayer).handle, EntityTargetEvent.TargetReason.CUSTOM, true)
	}
}
