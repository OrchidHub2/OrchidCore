package net.crewco.OrchidCore.managers

import net.crewco.OrchidCore.OrchidCore
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import javax.inject.Inject

class dataBaseManager @Inject constructor(private val plugin: OrchidCore) {

	var connection: Connection? = null

	fun connect() {
		try {
			Class.forName("org.sqlite.JDBC")
			connection = DriverManager.getConnection("jdbc:sqlite:${plugin.dataFolder}/data.db")
			createTables()
		} catch (e: SQLException) {
			plugin.logger.severe("Could not connect to the database!")
			e.printStackTrace()
		} catch (e: ClassNotFoundException) {
			e.printStackTrace()
		}
	}

	private fun createTables() {
		connection?.createStatement()?.use { statement ->
			statement.execute("CREATE TABLE IF NOT EXISTS credit_scores (player_uuid TEXT PRIMARY KEY, score INTEGER)")
			statement.execute("CREATE TABLE IF NOT EXISTS mortgages (player_uuid TEXT PRIMARY KEY, amount DOUBLE, interest_rate DOUBLE)")
		}
	}

	fun close() {
		connection?.close()
	}

	// Dynamic table creation
	fun createTable(tableName: String, columns: String) {
		val query = "CREATE TABLE IF NOT EXISTS $tableName ($columns)"
		connection?.createStatement()?.use { statement ->
			statement.execute(query)
		}
	}

	// Dynamic table removal
	fun removeTable(tableName: String) {
		val query = "DROP TABLE IF EXISTS $tableName"
		connection?.createStatement()?.use { statement ->
			statement.execute(query)
		}
	}
}