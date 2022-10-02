package dev.peopo.devroomdemo

import dev.peopo.devroomdemo.util.disable
import dev.peopo.devroomdemo.util.hasDependencies
import dev.peopo.devroomdemo.util.plugin
import org.bukkit.plugin.java.JavaPlugin

class DevroomDemoPlugin : JavaPlugin() {
	override fun onEnable() {

		if (!plugin.hasDependencies()) this.disable()
		registerCommands()
		registerListeners()
	}

	override fun onDisable() {

	}

	private fun registerCommands() {

	}

	private fun registerListeners() {

	}
}