package io.github.timortel.kotlin_multiplatform_grpc_lib

import cocoapods.GRPCClient.GRPCCallOptions
import cocoapods.GRPCClient.GRPCCallSafetyDefault
import cocoapods.GRPCClient.GRPCDefaultTransportImplList_
import cocoapods.GRPCClient.GRPCMutableCallOptions
import cocoapods.GRPCClient.GRPCRequestOptions
import io.github.timortel.kotlin_multiplatform_grpc_lib.util.TimeUnit

actual class KMChannel(private val target: String, val callOptions: GRPCCallOptions) {

    fun buildRequestOptions(path: String) = GRPCRequestOptions(target, path, safety = GRPCCallSafetyDefault)

    fun withDeadlineAfter(duration: Long, unit: TimeUnit): KMChannel {
        val mutableOptions = callOptions.mutableCopy() as GRPCMutableCallOptions

        val millis = (duration * unit.toMilliFactor).toDouble()
        val seconds = millis / 1000.0

        mutableOptions.setTimeout(seconds)

        return KMChannel(target, mutableOptions)
    }

    fun withMetadata(metadata: KMMetadata): KMChannel {
        val mutableOptions = callOptions.mutableCopy() as GRPCMutableCallOptions
        mutableOptions.setInitialMetadata(metadata.metadataMap.toMap())

        return KMChannel(target, mutableOptions)
    }

    actual class Builder(private val target: String) {

        private val callOptions = GRPCMutableCallOptions()

        actual companion object {
            actual fun forAddress(
                name: String,
                port: Int
            ): Builder = Builder("$name:$port")

            actual fun forTarget(
                target: String
            ): Builder = Builder(target)
        }

        actual fun usePlaintext(): Builder {
            callOptions.setTransport(GRPCDefaultTransportImplList_.core_insecure)
            return this
        }

        actual fun build(): KMChannel = KMChannel(target, callOptions)
    }
}