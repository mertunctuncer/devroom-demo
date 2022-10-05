package dev.peopo.devroomdemo.sql

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dev.peopo.bukkitscope.coroutine.IOScope
import dev.peopo.devroomdemo.util.config
import dev.peopo.skuerrel.Table
import kotlinx.coroutines.launch

val sqlTable by lazy { Table(hikariCP, PlayerData::class).also { IOScope.launch { it.create(true) } } }

val hikariCP by lazy {
	val host = config.getString("sql.host")
	val port = config.getInt("sql.port")
	val database = config.getString("sql.database")

	com.mysql.jdbc.Driver()

	val hikariConfig = HikariConfig()
	hikariConfig.jdbcUrl = "jdbc:mysql://$host:$port/$database"
	hikariConfig.username = config.getString("sql.username")
	hikariConfig.password = config.getString("sql.password")
	hikariConfig.isAutoCommit = false
	hikariConfig.maximumPoolSize = config.getInt("sql.pool_size")

	return@lazy HikariDataSource(hikariConfig)
}