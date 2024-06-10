package site.encryptdev.submissionawalintermediate.di

import android.content.Context
import site.encryptdev.submissionawalintermediate.data.StoryRepository
import site.encryptdev.submissionawalintermediate.utils.UserPreference

object Injection {

    fun provideRepository(context: Context): StoryRepository {
       val userPreference = UserPreference(context)
        val token = userPreference.getToken()
        return StoryRepository(token = token!!)
    }
}