package dev.peopo.devroomdemo

import dev.peopo.bukkitscope.coroutine.IOScope
import dev.peopo.devroomdemo.sql.PlayerData
import dev.peopo.devroomdemo.sql.hikariCP
import dev.peopo.devroomdemo.sql.sqlTable
import dev.peopo.devroomdemo.util.disable
import dev.peopo.devroomdemo.util.hasDependencies
import dev.peopo.devroomdemo.util.plugin
import dev.peopo.skuerrel.data.SQLPairList
import kotlinx.coroutines.launch
import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID

class DevroomDemoPlugin : JavaPlugin() {
	override fun onEnable() {

		if (!plugin.hasDependencies()) this.disable()
		registerCommands()
		registerListeners()
	}

	override fun onDisable() {
		hikariCP.close()
	}

	private fun registerCommands() {

	}

	private fun registerListeners() {

	}
}