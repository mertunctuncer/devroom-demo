package dev.peopo.devroomdemo.util

import dev.peopo.devroomdemo.config.Messages
import org.bukkit.entity.Player

fun Player.checkPermission(permission: Permissions) : Boolean?{
	if(!player?.hasPermission(permission.permission)!!) {
		player?.sendMessage(messages.getColorized(Messages.NO_PERMISSION))
		return null
	}
	return true
}

fun Player.sendColorizedMessage(message: Messages) = this.sendMessage(messages.getColorized(message))

fun Player.sendColorizedMessage(message: Messages, parser: (message: String) -> String) {
	val raw = messages.getMessage(message)
	this.sendMessage(parser.invoke(raw!!).colorize())
}