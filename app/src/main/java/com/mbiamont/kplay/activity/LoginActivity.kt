package com.mbiamont.kplay.activity

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.irozon.sneaker.Sneaker
import com.mbiamont.kplay.R
import com.mbiamont.kplay.presenter.LoginPresenter
import com.mbiamont.kplay.view.LoginView
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), LoginView {
    override var activity: Activity = this

    @Inject
    lateinit var loginPresenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        AndroidInjection.inject(this)
        loginPresenter.loginView = this

        initView()
    }

    override fun onStart() {
        super.onStart()
        loginPresenter.onStart()
    }

    private fun initView() {
        btLogin.setOnClickListener { loginPresenter.login() }
    }

    override fun terminate() {
        finish()
    }

    override fun showErrorMessage(message: Int) {
        Sneaker.with(this)
                .setTitle(getString(R.string.error_unknown_title))
                .setMessage(getString(message))
                .sneakError()
    }
}