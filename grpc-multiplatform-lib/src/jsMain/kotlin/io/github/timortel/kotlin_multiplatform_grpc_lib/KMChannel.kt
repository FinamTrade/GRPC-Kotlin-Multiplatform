package io.github.timortel.kotlin_multiplatform_grpc_lib

actual class KMChannel private constructor(target: String, usePlainText: Boolean) {

    val connectionString = (if (usePlainText) "http://" else "https://") + target

    actual data class Builder(val target: String) {

        private var usePlainText: Boolean = false

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
            usePlainText = true
            return this
        }

        actual fun build(): KMChannel =
            io.github.timortel.kotlin_multiplatform_grpc_lib.KMChannel(target, usePlainText)
    }
}