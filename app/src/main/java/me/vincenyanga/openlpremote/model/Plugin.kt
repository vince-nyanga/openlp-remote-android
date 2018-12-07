package me.vincenyanga.openlpremote.model

data class Plugin(val key:String, val displayName: String) {
    override fun toString(): String {
        return displayName
    }
}