package dev.peopo.devroomdemo.dungeon

import dev.peopo.devroomdemo.config.YamlConfig
import dev.peopo.devroomdemo.util.config
import org.bukkit.Bukkit
import org.bukkit.Location


val YamlConfig.spawnLocation by lazy {
	Location(
		Bukkit.getWorld(config.getString("dungeon.spawnLocation.world")!!),
		config.getDouble("dungeon.spawnLocation.x"),
		config.getDouble("dungeon.spawnLocation.y"),
		config.getDouble("dungeon.spawnLocation.z"),
		config.getDouble("dungeon.spawnLocation.yaw").toFloat(),
		config.getDouble("dungeon.spawnLocation.pitch").toFloat()
	)
}




