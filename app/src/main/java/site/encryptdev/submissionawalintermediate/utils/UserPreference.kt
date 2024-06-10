package site.encryptdev.submissionawalintermediate.utils

import android.content.Context

class UserPreference(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("user_pref", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    fun saveCredentials( token: String){
        editor.putString("token", token)
        editor.apply()
    }


    fun getToken(): String? {
        return sharedPreferences.getString("token", null)
    }

    fun clearCredentials() {
        editor.remove("token")
        editor.apply()
    }
}