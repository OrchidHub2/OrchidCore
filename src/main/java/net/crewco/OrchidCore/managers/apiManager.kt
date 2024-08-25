package net.crewco.OrchidCore.managers

import org.bukkit.plugin.Plugin
import net.crewco.OrchidCore.OrchidCore.Companion.creditManager
class apiManager {
	private val creditHooks = mutableMapOf<Plugin, (String) -> Int?>()
	private val mortgageHooks = mutableMapOf<Plugin, (String) -> Mortgage?>()
	private val interestRateHooks = mutableMapOf<Plugin, (String, Double) -> Unit>()


	fun registerCreditHook(plugin: Plugin, hook: (String) -> Int?) {
		creditHooks[plugin] = hook
		plugin.logger.info("${plugin.name} has registered a credit hook.")
	}

	fun registerMortgageHook(plugin: Plugin, hook: (String) -> Mortgage?) {
		mortgageHooks[plugin] = hook
		plugin.logger.info("${plugin.name} has registered a mortgage hook.")
	}

	fun registerInterestRateHook(plugin: Plugin, hook: (String, Double) -> Unit) {
		interestRateHooks[plugin] = hook
		plugin.logger.info("${plugin.name} has registered an interest rate hook.")
	}

	fun getCreditScore(playerUUID: String): Int? {
		for (hook in creditHooks.values) {
			val score = hook(playerUUID)
			if (score != null) {
				return score
			}
		}
		return creditManager.getCreditScore(playerUUID)
	}

	fun getMortgage(playerUUID: String): Mortgage? {
		for (hook in mortgageHooks.values) {
			val mortgage = hook(playerUUID)
			if (mortgage != null) {
				return mortgage
			}
		}
		return creditManager.getMortgage(playerUUID)
	}

	fun setInterestRate(playerUUID: String, interestRate: Double) {
		for (hook in interestRateHooks.values) {
			hook(playerUUID, interestRate)
		}
		creditManager.getMortgage(playerUUID)?.let {
			creditManager.setMortgage(playerUUID, it.amount, interestRate)
		}
	}

	fun unregisterHooks(plugin: Plugin) {
		creditHooks.remove(plugin)
		mortgageHooks.remove(plugin)
		interestRateHooks.remove(plugin)
		plugin.logger.info("${plugin.name} has unregistered its hooks.")
	}
}