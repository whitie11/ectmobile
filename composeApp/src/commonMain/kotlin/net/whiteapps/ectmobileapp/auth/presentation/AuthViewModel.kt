package net.whiteapps.ectmobileapp.auth.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import auth.domain.repository.AuthRepository
import auth.presentation.events.LoginUIEvent
import auth.presentation.stateObjs.LogInState
import auth.presentation.stateObjs.TokenRequestUIState
import auth.presentation.stateObjs.TokenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import net.whiteapps.ectmobileapp.auth.data.dto.AuthResponse
import net.whiteapps.ectmobileapp.auth.data.dto.TokenRequestDTO
import net.whiteapps.ectmobileapp.auth.domain.use_case.get_token.GetTokenUseCase
import net.whiteapps.ectmobileapp.common.domain.models.IsLoggedIn
import net.whiteapps.ectmobileapp.common.domain.models.JwtPayload
import net.whiteapps.ectmobileapp.common.domain.repository.LocalSettingsRepository
import net.whiteapps.ectmobileapp.util.Resource
import net.whiteapps.ectmobileapp.util.decodeJWT


class AuthViewModel(
    private val repository: AuthRepository,
    private val localSettingsRepository: LocalSettingsRepository
) : ViewModel(

) {

    val getTokenUseCase: GetTokenUseCase = GetTokenUseCase(
        authRepository = repository,
        localSettingsRepository = localSettingsRepository
    )

    private val _jwtPayload = mutableStateOf(JwtPayload())
    val jwtPayload: State<JwtPayload> = _jwtPayload

    private var _tokenState = MutableStateFlow(TokenState())
    val tokenState =_tokenState.asStateFlow()

    private val _loginState = MutableStateFlow<LogInState>(LogInState.SignedOut)
    val loginState = _loginState.asStateFlow()

    var tokenRequestUIState = mutableStateOf(TokenRequestUIState())

    fun onLoginEvent(event: LoginUIEvent) {
        when (event) {
            is LoginUIEvent.UsernameChanged -> {
                tokenRequestUIState.value = tokenRequestUIState.value.copy(
                    username = event.username
                )
            }

            is LoginUIEvent.PasswordChanged -> {
                tokenRequestUIState.value = tokenRequestUIState.value.copy(
                    password = event.password
                )
            }

            is LoginUIEvent.DoLogin -> {
                val tr = TokenRequestDTO(
                    name = tokenRequestUIState.value.username,
                    password = tokenRequestUIState.value.password
                )
                getToken(tr)
            }

            is LoginUIEvent.DoLogout -> {
                logout()
            }

        }
    }

    fun logout() {
        _tokenState.value = TokenState(
            refresh = "",
            access = "",
            isLoading = false,
            error = ""
        )
        _loginState.value = LogInState.SignedOut



//        Log.d("MyTag", "logout $loginState")
    }


    private fun getToken(tr: TokenRequestDTO)
    {
        getTokenUseCase(tr).onEach<Resource<AuthResponse>> { result: Resource<AuthResponse> ->
            when (result) {
                is Resource.Success -> {
                    _tokenState.value = TokenState(
                        refresh = result.data?.refreshToken?: "",
                        access = result.data?.accessToken?: ""
                    )
                    _jwtPayload.value = decodeJWT(_tokenState.value.access)
                    if (_jwtPayload.value.userID != null) {
                        val userId = _jwtPayload.value.userID
                        if (userId != null) {

                            val isLoggedIn = IsLoggedIn(
                                userID = userId.toInt(),
                                username = _jwtPayload.value.username ?: "",
                                role = _jwtPayload.value.role ?: "",
                                state = true
                            )

                            localSettingsRepository.setIsLoggedInState(isLoggedIn)
                            _loginState.value = LogInState.SignedIn

                      }
                    } else {
                        val x = 1
                        // TODO logout if no userId & set error message
                    }

                }

                is Resource.Error -> {
                    _loginState.value = LogInState.Error
                    _tokenState.value = TokenState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }

                is Resource.Loading -> {
                    _tokenState.value = TokenState(isLoading = true)
                }

                is Resource.Nothing<*> -> {
                    _tokenState.value = TokenState(isLoading = false)
                }
            }
        }.launchIn(viewModelScope)
    }
}