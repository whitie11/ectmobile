package net.whiteapps.ectmobileapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform