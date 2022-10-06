package dev.peopo.devroomdemo.packet

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.ProtocolManager
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketEvent
import dev.peopo.devroomdemo.dungeon.Dungeon
import dev.peopo.devroomdemo.dungeon.isVisible
import dev.peopo.devroomdemo.util.plugin

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

val protocolManager : ProtocolManager by lazy { ProtocolLibrary.getProtocolManager() }

fun registerPacketListeners() {
	protocolManager.addPacketListener(object : PacketAdapter(plugin, ENTITY_PACKETS.asIterable()) {
		override fun onPacketSending(event: PacketEvent) {
			val entityID: Int = event.packet.integers.read(0)

			if (!Dungeon.isVisible(entityID, event.player)) {
				event.isCancelled = true
			}
		}
	})
}