package dev.peopo.devroomdemo.commands

import dev.peopo.devroomdemo.sql.getDungeonStats
import dev.peopo.devroomdemo.util.colorize
import dev.peopo.kgui.gui.GUIPage
import dev.peopo.kgui.gui.button.ButtonBuilder
import dev.peopo.kgui.item.builder.editor
import dev.peopo.kgui.item.head.UUIDHead
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemStack

fun onStatsCommand(player: Player) {
	val statPage = GUIPage("&4Player Stats".colorize(), InventoryType.CHEST, 1)
	statPage.setButton(1,
		ButtonBuilder { UUIDHead(it.uniqueId, 1).editor.setName("&3Player: &f${it.name}".colorize()).item }.build()
	)
	statPage.setButton(4,
		ButtonBuilder { ItemStack(Material.GOLDEN_SWORD).editor.setName("&3Kills: &f${it.getDungeonStats()!!.kills}".colorize()).item }.build()
	)
	statPage.setButton(5,
		ButtonBuilder { ItemStack(Material.NETHERITE_SWORD).editor
			.setName("&3Kills Per Session: &f%.1f".colorize().format(it.getDungeonStats()!!.kills.toDouble() / it.getDungeonStats()!!.deaths)).item }.build()
	)
	statPage.setButton(6,
		ButtonBuilder { ItemStack(Material.IRON_DOOR).editor.setName("&3Sessions: &f${it.getDungeonStats()!!.sessions}".colorize()).item }.build()
	)
	statPage.setButton(7,
		ButtonBuilder { ItemStack(Material.FERMENTED_SPIDER_EYE).editor.setName("&3Deaths: &f${it.getDungeonStats()!!.deaths}".colorize()).item }.build()
	)
	statPage.setNotEmpty(0..8, ButtonBuilder(ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE).editor.setName(" ").item).build())
	statPage.open(player)
}