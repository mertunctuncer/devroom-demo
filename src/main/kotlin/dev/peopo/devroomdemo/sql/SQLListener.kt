package dev.peopo.devroomdemo.sql

import dev.peopo.devroomdemo.util.plugin
import dev.peopo.devroomdemo.util.pluginManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent


fun registerSQLListeners() = pluginManager.registerEvents(object : Listener {
	@EventHandler
	fun onJoin(event: PlayerJoinEvent) {
		event.player.fetchDungeonData()
	}

	@EventHandler
	fun onQuit(event: PlayerQuitEvent) {
		event.player.updateDungeonData()
	}
}, plugin)