package net.crewco.OrchidCore.managers

import net.crewco.OrchidCore.OrchidCore.Companion.databaseManager

class creditManager {
	fun getCreditScore(playerUUID: String): Int? {
		val query = "SELECT score FROM credit_scores WHERE player_uuid = ?"
		databaseManager.connection?.prepareStatement(query)?.use { statement ->
			statement.setString(1, playerUUID)
			val result = statement.executeQuery()
			if (result.next()) {
				return result.getInt("score")
			}
		}
		return null
	}

	fun setCreditScore(playerUUID: String, score: Int) {
		val query = "INSERT OR REPLACE INTO credit_scores (player_uuid, score) VALUES (?, ?)"
		databaseManager.connection?.prepareStatement(query)?.use { statement ->
			statement.setString(1, playerUUID)
			statement.setInt(2, score)
			statement.executeUpdate()
		}
	}

	fun getMortgage(playerUUID: String): Mortgage? {
		val query = "SELECT amount, interest_rate FROM mortgages WHERE player_uuid = ?"
		databaseManager.connection?.prepareStatement(query)?.use { statement ->
			statement.setString(1, playerUUID)
			val result = statement.executeQuery()
			if (result.next()) {
				return Mortgage(
					amount = result.getDouble("amount"),
					interestRate = result.getDouble("interest_rate")
				)
			}
		}
		return null
	}

	fun setMortgage(playerUUID: String, amount: Double, interestRate: Double) {
		val query = "INSERT OR REPLACE INTO mortgages (player_uuid, amount, interest_rate) VALUES (?, ?, ?)"
		databaseManager.connection?.prepareStatement(query)?.use { statement ->
			statement.setString(1, playerUUID)
			statement.setDouble(2, amount)
			statement.setDouble(3, interestRate)
			statement.executeUpdate()
		}
	}

	fun removeMortgage(playerUUID: String) {
		val query = "DELETE FROM mortgages WHERE player_uuid = ?"
		databaseManager.connection?.prepareStatement(query)?.use { statement ->
			statement.setString(1, playerUUID)
			statement.executeUpdate()
		}
	}
}