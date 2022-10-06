package dev.peopo.devroomdemo.util

import dev.peopo.devroomdemo.config.YamlConfig
import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

val plugin: Plugin by lazy { Bukkit.getPluginManager().getPlugin("DevroomDemo")!! }

val javaPlugin: JavaPlugin by lazy { plugin as JavaPlugin }

val pluginManager: PluginManager by lazy { Bukkit.getPluginManager() }

val config: YamlConfig by lazy { YamlConfig(plugin, "config.yml") }

val logger: Logger by lazy { plugin.logger }

val Plugin.dependencies: List<String> by lazy {
	val input = plugin.getResource("plugin.yml")?.reader()
	val config = YamlConfiguration()
	config.load(input!!)
	return@lazy config.getStringList("depend")
}


fun Plugin.hasDependencies(): Boolean {
	for (dependency in this.dependencies) if (pluginManager.getPlugin(dependency) == null) return false
	return true
}

fun Plugin.disable() = pluginManager.disablePlugin(this)