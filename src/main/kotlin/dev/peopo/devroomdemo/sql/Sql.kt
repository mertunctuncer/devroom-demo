package dev.peopo.devroomdemo.sql

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dev.peopo.bukkitscope.coroutine.IOScope
import dev.peopo.devroomdemo.util.config
import dev.peopo.skuerrel.Table
import dev.peopo.skuerrel.data.SQLPairList
import kotlinx.coroutines.launch
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.ConcurrentHashMap

val sqlTable by lazy { Table(hikariCP, PlayerData::class).also { IOScope.launch { it.create(true) } } }

val hikariCP by lazy {
	val host = config.getString("sql.host")
	val port = config.getInt("sql.port")
	val database = config.getString("sql.database")

	com.mysql.cj.jdbc.Driver()

	val hikariConfig = HikariConfig()
	hikariConfig.jdbcUrl = "jdbc:mysql://$host:$port/$database"
	hikariConfig.username = config.getString("sql.username")
	hikariConfig.password = config.getString("sql.password")
	hikariConfig.isAutoCommit = false
	hikariConfig.maximumPoolSize = config.getInt("sql.pool_size")

	return@lazy HikariDataSource(hikariConfig)
}

private val statCache = ConcurrentHashMap<UUID, PlayerData>()

fun Player.fetchDungeonData() {
	IOScope.launch {
		val where = SQLPairList()
		where.add("uuid", uniqueId.toString())
		val results = sqlTable.fetch<PlayerData>(where)
		val data = if (results.isEmpty()) {
			val playerData = PlayerData()
			playerData.uuid = uniqueId.toString()
			sqlTable.insert(playerData)
			playerData
		} else results[0]

		statCache[uniqueId] = data
	}
}

fun Player.updateDungeonData() {
	IOScope.launch {
		val where = SQLPairList()
		where.add("uuid", uniqueId.toString())
		sqlTable.update(getDungeonStats()!!, where)
		statCache.remove(uniqueId)
	}
}

fun Player.getDungeonStats() = statCache[this.uniqueId]