pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Warehouse"
include(":app")
include(":core")
include(":features")
include(":core:calculate")
include(":features:documents")
include(":features:database")
include(":features:home")
include(":core:mlkit")
include(":core:database")
include(":core:database:dataserializer")
include(":features:settings")
include(":core:ui")
