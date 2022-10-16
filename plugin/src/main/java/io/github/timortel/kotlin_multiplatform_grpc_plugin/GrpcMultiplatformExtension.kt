package io.github.timortel.kotlin_multiplatform_grpc_plugin

import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.MapProperty
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import java.io.File

abstract class GrpcMultiplatformExtension {

    /**
     * Maps the output target to the source sets that require it.
     */
    abstract val targetSourcesMap: MapProperty<OutputTarget, List<KotlinSourceSet>>

    abstract val protoSourceFolders: ListProperty<File>

    abstract val protoDependencyFolders: ListProperty<File>

    enum class OutputTarget {
        COMMON,
        JVM,
        JS,
        IOS
    }

    init {
        targetSourcesMap.convention(emptyMap())
        protoSourceFolders.convention(emptyList())
        protoDependencyFolders.convention(emptyList())
    }

    fun setTargetSourceCommon(sourceSet: List<KotlinSourceSet>) {
        targetSourcesMap.put(OutputTarget.COMMON, sourceSet)
    }

    fun setTargetSourceJvm(sourceSet: List<KotlinSourceSet>) {
        targetSourcesMap.put(OutputTarget.JVM, sourceSet)
    }

    fun setTargetSourceJs(sourceSet: List<KotlinSourceSet>) {
        targetSourcesMap.put(OutputTarget.JS, sourceSet)
    }

    fun setTargetSourceIos(sourceSet: List<KotlinSourceSet>) {
        targetSourcesMap.put(OutputTarget.IOS, sourceSet)
    }
}