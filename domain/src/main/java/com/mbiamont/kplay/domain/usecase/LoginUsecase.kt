package com.mbiamont.kplay.domain.usecase

import android.app.Activity
import android.content.Context
import android.os.Bundle
import com.deezer.sdk.model.Permissions
import com.deezer.sdk.network.connect.DeezerConnect
import com.deezer.sdk.network.connect.SessionStore
import com.deezer.sdk.network.connect.event.DialogListener
import io.reactivex.Maybe
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by Melvin Biamont
 *
 * Usecase to connect the user to its Deezer account
 */
class LoginUsecase @Inject constructor(var deezerConnect: DeezerConnect) {

    /**
     * Returns true if the user is already logged to its Deezer account
     */
    fun isLogged(context: Context): Boolean {
        return SessionStore().restore(deezerConnect, context)
    }

    /**
     * Display the Deezer login popup and manage its return state
     */
    fun login(activity: Activity): Maybe<Unit> {
        return Maybe.create({ subscriber ->
                                deezerConnect.authorize(activity, arrayOf(Permissions.BASIC_ACCESS),
                                                        object : DialogListener {

                                                            override fun onComplete(p0: Bundle?) {
                                                                SessionStore().save(deezerConnect,
                                                                                    activity)
                                                                return subscriber.onSuccess(Unit)
                                                            }

                                                            override fun onCancel() = subscriber.onComplete()

                                                            override fun onException(p0: Exception?) = subscriber.onError(
                                                                    p0!!)

                                                        })
                            })
    }
}