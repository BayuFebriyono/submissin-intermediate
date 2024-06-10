package site.encryptdev.submissionawalintermediate.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import site.encryptdev.submissionawalintermediate.data.remote.response.ErrorResponse
import site.encryptdev.submissionawalintermediate.data.remote.response.LoginResult
import site.encryptdev.submissionawalintermediate.data.remote.retrofit.ApiConfig

class LoginViewModel: ViewModel() {
    private var _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(email: String, pass: String){
        _isLoading.value = true
        viewModelScope.launch {
            try{
                val result = ApiConfig.getService().login(email,pass).loginResult
                _loginResult.value = result!!
                _isLoading.value = false
                _isSuccess.value = true

            }catch (e: HttpException){
                _isLoading.value = false
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.message
                _isSuccess.value = false
            }
        }
    }
}