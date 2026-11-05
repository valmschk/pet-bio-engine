plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "bio-engine"

include("common-dto")
include("api-gateway")
include("telemetry-service")
include("analytics-service")