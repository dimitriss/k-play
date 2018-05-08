package com.mbiamont.kplay.view

import android.app.Activity

interface LoginView {

    var activity: Activity

    fun terminate()

    fun showErrorMessage(message: Int)

}