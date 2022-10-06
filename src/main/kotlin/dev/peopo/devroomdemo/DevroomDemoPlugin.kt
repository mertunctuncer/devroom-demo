package dev.peopo.devroomdemo

import dev.peopo.devroomdemo.commands.registerCommandListeners
import dev.peopo.devroomdemo.packet.registerPacketListeners
import dev.peopo.devroomdemo.sql.hikariCP
import dev.peopo.devroomdemo.util.disable
import dev.peopo.devroomdemo.util.hasDependencies
import dev.peopo.devroomdemo.util.plugin
import dev.peopo.kgui.gui.registerGUIListener
import org.bukkit.plugin.java.JavaPlugin

class DevroomDemoPlugin : JavaPlugin() {
	override fun onEnable() {
		if (!plugin.hasDependencies()) this.disable()
		plugin.registerGUIListener()
		registerPacketListeners()
		registerCommandListeners()
	}

	override fun onDisable() {
		hikariCP.close()
	}
}