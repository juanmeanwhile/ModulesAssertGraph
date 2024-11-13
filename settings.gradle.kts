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

rootProject.name = "ModulesAssertGraph"
include(":app")
include(":feature:search")
include(":feature:checkout")
include(":feature:checkout:domain")
include(":feature:checkout:data")
include(":analytics")
include(":feature:exception")
include(":ui:common")
include(":ui:product")
include(":utils")
