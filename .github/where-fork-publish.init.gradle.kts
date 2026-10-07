// Interim publishing for the henrik242/where app: adds a GitHub Packages Maven repo to every
// module that applies maven-publish, so the forked build (0.19.0 + connectivity override #1735)
// can be consumed without Maven Central. Applied only via --init-script from the where-fork-publish
// workflow; it does not affect the upstream release/snapshot flows. Credentials come from the
// Actions environment (GITHUB_ACTOR / GITHUB_TOKEN).
import org.gradle.api.publish.PublishingExtension

gradle.beforeProject {
    pluginManager.withPlugin("maven-publish") {
        extensions.configure(PublishingExtension::class.java) {
            repositories.maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/henrik242/maplibre-compose")
                credentials {
                    username = System.getenv("GITHUB_ACTOR")
                    password = System.getenv("GITHUB_TOKEN")
                }
            }
        }
    }
}
