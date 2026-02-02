package com.phase.bookpin.state

import androidx.lifecycle.viewModelScope
import com.phase.bookpin.common.BaseViewModel
import com.phase.bookpin.domain.session.SessionRepository
import com.phase.bookpin.model.SessionEvent
import kotlinx.coroutines.launch

class RootViewModel(
    private val sessionRepository: SessionRepository,
) : BaseViewModel<RootState, RootSideEffect>() {
    override fun createInitialState(): RootState = RootState()

    init {
        observeSessionEvents()
    }

    private fun observeSessionEvents() {
        viewModelScope.launch {
            sessionRepository.events.collect { event ->
                when (event) {
                    SessionEvent.SessionExpired,
                    SessionEvent.InvalidRefreshToken,
                    -> {
                        sessionRepository.clearSession()
                        postSideEffect(RootSideEffect.ShowSnackbar("세션이 만료되었습니다. 다시 로그인해주세요."))
                        // TODO: Navigation 구현 후 AuthScreen으로 이동
                    }
                }
            }
        }
    }

    fun handleShowSnackbarEvent(message: String) {
        postSideEffect(RootSideEffect.ShowSnackbar(message))
    }
}
