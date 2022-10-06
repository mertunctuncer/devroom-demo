package dev.peopo.devroomdemo.dungeon

import org.bukkit.Location
import org.bukkit.entity.Player
import kotlin.random.Random


class NoValidPositionException : RuntimeException()


fun getMobSpawnPosition(tries: Int, range: Int, player: Player): Location? {
	val playerLoc = player.location
	repeat(tries) {
		val x = Random.nextInt(playerLoc.blockX - range, playerLoc.blockX + range + 1)
		val z = Random.nextInt(playerLoc.blockZ - range, playerLoc.blockZ + range + 1)
		for (y in playerLoc.blockY + 5 downTo playerLoc.blockY - 5) {
			val currentBlock = playerLoc.world.getBlockAt(x, y, z)
			val blockAbove = playerLoc.world.getBlockAt(x, y + 1, z)
			val blockTwoAbove = playerLoc.world.getBlockAt(x, y + 2, z)
			if (!currentBlock.isEmpty && blockAbove.isEmpty && blockTwoAbove.isEmpty) return Location(
				playerLoc.world,
				x.toDouble() + 0.5,
				y.toDouble() + 1,
				z.toDouble() + 0.5
			)
		}
	}

	return null
}