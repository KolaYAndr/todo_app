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

rootProject.name = "Todo App"
include(":app")
include(":network")
include(":network")
include(":net")
include(":net")
include(":feature_auth")
include(":feature_todo")
include(":core")
include(":networ")
include(":feature")
include(":feature:auth")
include(":feature:todo")
include(":cor")
include(":database")
