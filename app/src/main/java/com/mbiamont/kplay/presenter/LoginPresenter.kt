package com.mbiamont.kplay.presenter

import com.mbiamont.kplay.R
import com.mbiamont.kplay.domain.usecase.LoginUsecase
import com.mbiamont.kplay.view.LoginView
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Melvin Biamont
 *
 * Presenter to manage the Login view
 */
class LoginPresenter @Inject constructor(private val loginUsecase: LoginUsecase) {

    lateinit var loginView: LoginView

    /**
     * Handle the onStart() of the activity
     */
    fun onStart() {
        if (loginUsecase.isLogged(loginView.activity)) {
            onLogged()
        }
    }

    /**
     * Calls the login usecase
     */
    fun login() {
        loginUsecase
                .login(loginView.activity)
                .subscribe({
                               onLogged()
                           }, {
                               onError(it)
                           }, {
                               onCancelled()
                           })
    }

    /**
     * Callback fired when the user is logged
     */
    private fun onLogged() {
        loginView.terminate()
    }

    /**
     * Callback fired when the user cancelled the login
     */
    private fun onCancelled() {
        loginView.showErrorMessage(R.string.login_cancel)
    }

    /**
     * Callback fired when a problem occurred
     */
    private fun onError(throwable: Throwable) {
        loginView.showErrorMessage(R.string.error_unknown)
        Timber.e(throwable)
    }
}