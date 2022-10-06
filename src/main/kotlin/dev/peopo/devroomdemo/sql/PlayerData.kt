package dev.peopo.devroomdemo.sql

import dev.peopo.skuerrel.annotation.Column
import dev.peopo.skuerrel.annotation.PrimaryKey
import dev.peopo.skuerrel.annotation.Table

@Table("dungeon_data")
class PlayerData {
	@Column(0, 36)
	@PrimaryKey
	var uuid: String = ""
	@Column(1)
	var kills: Long = 0
	@Column(2)
	var deaths: Long = 0
	@Column(3)
	var sessions: Long = 0
}