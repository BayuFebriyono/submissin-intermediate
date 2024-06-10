package site.encryptdev.submissionawalintermediate.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import site.encryptdev.submissionawalintermediate.data.remote.response.ErrorResponse
import site.encryptdev.submissionawalintermediate.data.remote.response.ListStoryItem
import site.encryptdev.submissionawalintermediate.data.remote.response.StoryResponse
import site.encryptdev.submissionawalintermediate.data.remote.retrofit.ApiConfig

class MapsViewModel : ViewModel() {
    private var _isLoading = MutableLiveData<Boolean>();
    val isLoading : LiveData<Boolean> = _isLoading;

    private var _data = MutableLiveData<List<ListStoryItem?>?>()
    val data : LiveData<List<ListStoryItem?>?> = _data

    fun getAllStory(token : String){
        _isLoading.value = true
        viewModelScope.launch {
            try{
                val list = ApiConfig.getService().getStoriesWithLocation(token ="Bearer $token").listStory
                _data.value = list
                _isLoading.value = false
            }catch (e: HttpException){
                _isLoading.value = false
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.message
            }
        }
    }
}