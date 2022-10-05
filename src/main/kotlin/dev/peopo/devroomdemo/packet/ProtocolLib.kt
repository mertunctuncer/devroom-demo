package dev.peopo.devroomdemo.packet

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketEvent
import com.comphenix.protocol.events.PacketListener
import dev.peopo.devroomdemo.dungeon.Dungeon.Companion.dungeon
import dev.peopo.devroomdemo.util.plugin
import dev.peopo.devroomdemo.util.pluginManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.world.ChunkUnloadEvent


private val ENTITY_PACKETS = arrayOf(
	PacketType.Play.Server.ENTITY_EQUIPMENT,
	PacketType.Play.Server.ANIMATION,
	PacketType.Play.Server.NAMED_ENTITY_SPAWN,
	PacketType.Play.Server.COLLECT,
	PacketType.Play.Server.SPAWN_ENTITY,
	PacketType.Play.Server.SPAWN_ENTITY_LIVING,
	PacketType.Play.Server.SPAWN_ENTITY_PAINTING,
	PacketType.Play.Server.SPAWN_ENTITY_EXPERIENCE_ORB,
	PacketType.Play.Server.ENTITY_VELOCITY,
	PacketType.Play.Server.REL_ENTITY_MOVE,
	PacketType.Play.Server.ENTITY_LOOK,
	PacketType.Play.Server.ENTITY_MOVE_LOOK,
	PacketType.Play.Server.ENTITY_TELEPORT,
	PacketType.Play.Server.ENTITY_HEAD_ROTATION,
	PacketType.Play.Server.ENTITY_STATUS,
	PacketType.Play.Server.ATTACH_ENTITY,
	PacketType.Play.Server.ENTITY_METADATA,
	PacketType.Play.Server.ENTITY_EFFECT,
	PacketType.Play.Server.REMOVE_ENTITY_EFFECT,
	PacketType.Play.Server.BLOCK_BREAK_ANIMATION // ANYTHING ELSE?
)

val protocolManager = ProtocolLibrary.getProtocolManager().also {
	it.addPacketListener(object: PacketAdapter(plugin, ENTITY_PACKETS.asIterable()) {
		override fun onPacketSending(event: PacketEvent) {
			val entityID: Int = event.getPacket().getIntegers().read(0)

			if (!isVisible(event.getPlayer(), entityID)) {
				event.setCancelled(true)
			}
		}
	})
}

pluginManager.registerEvents(object: Listener {
	@EventHandler
	fun onEntityDeath(e: EntityDeathEvent) {
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

}, plugin);