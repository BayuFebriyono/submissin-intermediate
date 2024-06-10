package site.encryptdev.submissionawalintermediate.ui.story

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import site.encryptdev.submissionawalintermediate.data.StoryPagingSource
import site.encryptdev.submissionawalintermediate.data.StoryRepository
import site.encryptdev.submissionawalintermediate.data.remote.response.ErrorResponse
import site.encryptdev.submissionawalintermediate.data.remote.response.ListStoryItem
import site.encryptdev.submissionawalintermediate.data.remote.retrofit.ApiConfig
import site.encryptdev.submissionawalintermediate.data.remote.retrofit.ApiService
import site.encryptdev.submissionawalintermediate.di.Injection

class StoryViewModel(private val storyRepository: StoryRepository): ViewModel() {


    val story : LiveData<PagingData<ListStoryItem>> =storyRepository.getStory().cachedIn(viewModelScope)

}

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StoryViewModel(Injection.provideRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}