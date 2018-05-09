package com.mbiamont.kplay.view

import android.app.Activity

/**
 * Created by Melvin Biamont
 *
 * Contract to interact with the login view
 */
interface LoginView {

    /**
     * Provides the activity (required by Deezer)
     */
    var activity: Activity

    /**
     * Terminate the view (close it)
     */
    fun terminate()

    /**
     * Show an error message using its resource ID
     */
    fun showErrorMessage(message: Int)

}