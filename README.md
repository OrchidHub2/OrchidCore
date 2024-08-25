# Core Plugin for Orchid City


# Gradel Implementation
	dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}

 	dependencies {
	        implementation 'com.github.OrchidHub2:OrchidCore:Tag'
	}

# Maven Implementation
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>

 	<dependency>
	    <groupId>com.github.OrchidHub2</groupId>
	    <artifactId>OrchidCore</artifactId>
	    <version>Tag</version>
	</dependency>
