package site.encryptdev.submissionawalintermediate.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import site.encryptdev.submissionawalintermediate.data.remote.response.ErrorResponse
import site.encryptdev.submissionawalintermediate.data.remote.retrofit.ApiConfig
class RegisterViewModel : ViewModel() {

    private var _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private var _isSucces = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSucces

    private var _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun login(name: String, email: String, pass: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val message = ApiConfig.getService().register(name, email, pass).message
                _isSucces.value = true
                _isLoading.value = false
                _message.value = message ?: ""
            } catch (e: HttpException) {
                _isLoading.value = false
                _isSucces.value = false
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.message
                _message.value = errorMessage ?: ""
            }
        }
    }

}