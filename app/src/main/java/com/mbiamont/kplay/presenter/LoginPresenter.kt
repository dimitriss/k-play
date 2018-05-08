package com.mbiamont.kplay.presenter

import com.mbiamont.kplay.R
import com.mbiamont.kplay.domain.usecase.LoginUsecase
import com.mbiamont.kplay.view.LoginView
import timber.log.Timber
import javax.inject.Inject


class LoginPresenter @Inject constructor(private val loginUsecase: LoginUsecase) {

    lateinit var loginView: LoginView

    fun onStart() {
        if (loginUsecase.isLogged(loginView.activity)) {
            onLogged()
        }
    }

    fun login() {
        loginUsecase.login(loginView.activity).subscribe({
                                                             onLogged()
                                                         }, {
                                                             onError(it)
                                                         }, {
                                                             onCancelled()
                                                         })
    }

    private fun onLogged() {
        loginView.terminate()
    }

    private fun onCancelled() {
        loginView.showErrorMessage(R.string.login_cancel)
    }

    private fun onError(throwable: Throwable) {
        loginView.showErrorMessage(R.string.error_unknown)
        Timber.e(throwable)
    }
}