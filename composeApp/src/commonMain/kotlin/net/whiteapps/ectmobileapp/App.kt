package net.whiteapps.ectmobileapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import net.whiteapps.ectmobileapp.referral.presentation.screens.ReferralDetailScreen
import net.whiteapps.ectmobileapp.home.presentation.screens.HomeScreen
import net.whiteapps.ectmobileapp.referral.presentation.screens.ReferralScreen
import kotlinx.serialization.Serializable
import net.whiteapps.ectmobileapp.auth.presentation.screens.LoginScreen
import net.whiteapps.ectmobileapp.referral.presentation.screens.NewReferralForm
import org.jetbrains.compose.ui.tooling.preview.Preview

sealed interface Dest {
    @Serializable
    data object AuthRoot : Dest
    @Serializable
    data object Auth : Dest
    @Serializable
    data object HomeRoot : Dest
    @Serializable
    data object Home : Dest
    @Serializable
    data object ReferralRoot : Dest
    @Serializable
    data object Referral : Dest
    @Serializable
    data object NewReferral : Dest
    @Serializable
    data object NewServiceUser : Dest
    @Serializable
    data class ReferralDetail(val refId: Int) : Dest
}



@Composable
@Preview
fun App() {
    MaterialTheme {
        val navHostController = rememberNavController()
        NavHost(navController = navHostController, startDestination = Dest.AuthRoot) {

            navigation<Dest.AuthRoot>(startDestination = Dest.Auth) {
                composable<Dest.Auth> {
                    LoginScreen(
                        onLogin = {
                            navHostController.navigate(Dest.HomeRoot) {
                                popUpTo(Dest.Auth) {
                                inclusive = true }
                            }
                                  },
                        onSignUp = {},
                        onForgotPassword = {}
                    )
                }
            }
            
            navigation<Dest.HomeRoot>(startDestination = Dest.Home) {
                composable<Dest.Home> {
                    HomeScreen(
                        onLogout = {
                            navHostController.navigate(Dest.AuthRoot) {
                                popUpTo(Dest.Home) {
                                    inclusive = true
                                }
                            }
                        },
                        onNavigateReferral = {
                            navHostController.navigate(Dest.ReferralRoot)
                        },
                        onNavigateTreatment = {},
                        onNavigateNewReferral = {
                          navHostController.navigate(Dest.NewReferral)
                        },

                    ) 
                }

            }

            navigation<Dest.ReferralRoot>(startDestination = Dest.Referral) {
                composable<Dest.Referral> {
                    ReferralScreen(
                        onLogout = {
                            navHostController.navigate(Dest.AuthRoot) {
                                popUpTo(Dest.Home) {
                                    inclusive = true
                                }
                            }
                        },
                        onNavigateReferral = {},
                        onNavigateTreatment = {},
                        onNavigateHome = {
                            navHostController.navigate(Dest.Home) {
                                popUpTo(Dest.Home) {
                                    inclusive = true
                                }
                            }
                        },
                        onNavigateDetails = { refId ->
                            navHostController.navigate(Dest.ReferralDetail(refId))
                        },
                        onNavigateNewReferral = {
                          navHostController.navigate(Dest.NewReferral)
                        },
                    )
                }

                composable<Dest.ReferralDetail> {
                    val args = it.toRoute<Dest.ReferralDetail>()
                    ReferralDetailScreen(
                        refId = args.refId,
                        onLogout = {
                            navHostController.navigate(Dest.AuthRoot) {
                                popUpTo(Dest.Home) {
                                    inclusive = true
                                }
                            }
                        },
                        onNavigateReferral = {
                            navHostController.navigate(Dest.Referral) {
                                popUpTo(Dest.Referral) {
                                    inclusive = true
                                }
                            }
                        },
                        onNavigateTreatment = {},
                        onNavigateHome = {
                            navHostController.navigate(Dest.Home) {
                                popUpTo(Dest.Home) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }

                composable<Dest.NewReferral> {
                    NewReferralForm(
                        onLogout = {
                            navHostController.navigate(Dest.AuthRoot) {
                                popUpTo(Dest.Home) {
                                    inclusive = true
                                }
                            }
                        },
                        onNavigateReferral = {
                            navHostController.navigate(Dest.Referral) {
                                popUpTo(Dest.Referral) {
                                    inclusive = true
                                }
                            }
                        },
                        onNavigateTreatment = {},

                        onNavigateHome = {
                            navHostController.navigate(Dest.Home) {
                                popUpTo(Dest.Home) {
                                    inclusive = true
                                }
                            }
                        },

                    )
                }



            }


        }
    }
}



