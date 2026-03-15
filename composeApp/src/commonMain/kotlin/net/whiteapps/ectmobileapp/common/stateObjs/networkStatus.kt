package net.whiteapps.ectmobileapp.common.stateObjs

sealed class NetworkStatus {
    data object Idle: NetworkStatus()
    data object Loading: NetworkStatus()
    data object Success: NetworkStatus()
    data object Failure: NetworkStatus()
    data object NotFound: NetworkStatus()
}
