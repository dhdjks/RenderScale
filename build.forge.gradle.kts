plugins {
    id("mod-platform")
    id("net.neoforged.moddev.legacyforge")
}

platform {
    loader = "forge"
    dependencies {
        required("minecraft") {
            forgeVersionRange = "[${prop("deps.minecraft")}]"
        }
        required("forge") {
            forgeVersionRange = "[1,)"
        }
        // 修正：依赖 ID 必须与 mods.toml 中的一致，使用 "cloth_config"
        required("cloth_config") {
            forgeVersionRange = "[${prop("deps.minecraft")}]"
        }
    }
}

legacyForge {
    version = "${property("deps.minecraft")}-${property("deps.forge")}"

    validateAccessTransformers = true

    // 只使用 META-INF/accesstransformer.cfg，暂时注释 aw 目录下的
    accessTransformers.from(
        rootProject.file("src/main/resources/META-INF/accesstransformer.cfg")
    )
    // accessTransformers.from(
    //     rootProject.file("src/main/resources/aw/${stonecutter.current.version}.cfg")
    // )

    runs {
        register("client") {
            client()
            gameDirectory = file("run/")
            ideName = "Forge Client (${stonecutter.active?.version})"
            programArgument("--username=Dev")
        }
//        register("server") {
//            server()
//            gameDirectory = file("run/")
//            ideName = "Forge Server (${stonecutter.active?.version})"
//        }
    }

    mods {
        register(prop("mod.id")) {
            sourceSet(sourceSets["main"])
        }
    }
}

mixin {
    add(sourceSets.main.get(), "${prop("mod.id")}.mixins.refmap.json")
    config("${prop("mod.id")}.mixins.json")
}

repositories {
    mavenCentral()
    strictMaven("https://api.modrinth.com/maven", "maven.modrinth") { name = "Modrinth" }
    strictMaven("https://maven.shedaniel.me/", "me.shedaniel.cloth") { name = "Shedaniel" }
}

dependencies {
    annotationProcessor("org.spongepowered:mixin:${libs.versions.mixin.get()}:processor")

    implementation(libs.moulberry.mixinconstraints)
    jarJar(libs.moulberry.mixinconstraints)

    // MixinExtras 可选，但如果你不使用 @ModifyReturnValue 可以不要
    // implementation("io.github.llamalad7:mixinextras-forge:0.4.1")
    // annotationProcessor("io.github.llamalad7:mixinextras-forge:0.4.1")

    modApi("me.shedaniel.cloth:cloth-config-forge:${property("deps.cloth_config")}") {
        exclude("net.fabricmc.fabric-api")
    }
}

sourceSets {
    main {
        resources.srcDir(
            "${rootDir}/versions/datagen/${stonecutter.current.version.split("-")[0]}/src/main/generated"
        )
    }
}

tasks.named("createMinecraftArtifacts") {
    dependsOn(tasks.named("stonecutterGenerate"))
}

stonecutter {

}
