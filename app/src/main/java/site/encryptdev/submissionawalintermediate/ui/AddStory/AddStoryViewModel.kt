package site.encryptdev.submissionawalintermediate.ui.AddStory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import site.encryptdev.submissionawalintermediate.data.remote.response.ErrorResponse
import site.encryptdev.submissionawalintermediate.data.remote.response.FileUploadResponse
import site.encryptdev.submissionawalintermediate.data.remote.retrofit.ApiConfig

class AddStoryViewModel : ViewModel() {
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _uploadResponse = MutableLiveData<FileUploadResponse>()
    val uploadResponse : LiveData<FileUploadResponse> = _uploadResponse

    private var _statusMessage = MutableLiveData<String>()
    val statusMessage: LiveData<String> = _statusMessage

    private var _isSuccess = MutableLiveData<Boolean>()
    val isSuccess :LiveData<Boolean> = _isSuccess

    fun addStory(token: String, file: MultipartBody.Part, description: RequestBody){
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiConfig.getService().addStory("Bearer $token", file, description)
                _uploadResponse.value = response
                _isLoading.value = false
                _statusMessage.value = response.message
                _isSuccess.value = true
            }catch (e: HttpException){
                _isLoading.value = false
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.message
                _isSuccess.value = false
                _statusMessage.value = errorMessage!!
            }
        }
    }
}