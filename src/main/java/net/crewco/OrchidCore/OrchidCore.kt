package net.crewco.OrchidCore

import net.crewco.OrchidCore.managers.apiManager
import net.crewco.OrchidCore.managers.creditManager
import net.crewco.OrchidCore.managers.dataBaseManager
import net.crewco.common.CrewCoPlugin

class OrchidCore : CrewCoPlugin() {
	companion object{
		lateinit var plugin:OrchidCore
			private set
		lateinit var databaseManager: dataBaseManager
			private set
		lateinit var creditManager: creditManager
		lateinit var apiManager: apiManager
	}
	override suspend fun onEnableAsync() {
		super.onEnableAsync()

		//Inits
		plugin = this

		//Config
		plugin.config.options().copyDefaults()
		plugin.saveDefaultConfig()

		//MySql
		databaseManager = databaseManager
		databaseManager.connect()

		//Credit Manager
		creditManager = creditManager()

		//Api Manager
		apiManager = apiManager()


	}

	override suspend fun onDisableAsync() {
		super.onDisableAsync()
	}
}