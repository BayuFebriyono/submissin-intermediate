package site.encryptdev.submissionawalintermediate.ui.login

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import site.encryptdev.submissionawalintermediate.R
import site.encryptdev.submissionawalintermediate.databinding.ActivityLoginBinding
import site.encryptdev.submissionawalintermediate.ui.register.RegisterActivity
import site.encryptdev.submissionawalintermediate.ui.story.ListStoryActivity
import site.encryptdev.submissionawalintermediate.utils.UserPreference

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private val userPreference: UserPreference by lazy {
        UserPreference(this@LoginActivity)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cekCredentials()

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            finish()
        }

        viewModel.isLoading.observe(this){
            setLoading(it)
        }

        viewModel.isSuccess.observe(this){
            if (it){
                val token = viewModel.loginResult.value?.token.toString()
                userPreference.saveCredentials(token)
                startActivity(Intent(this@LoginActivity, ListStoryActivity::class.java))
            }else{
                Toast.makeText(this,"Email atau password salah", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnLogin.setOnClickListener {
            login()
        }


    }



    private fun login(){
        val email = binding.emailEditText.text.toString()
        val pass = binding.passwordEditText.text.toString()

        viewModel.login(email,pass)
    }

    private fun setLoading(isLoading: Boolean){
        binding.progressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
    }

    private fun cekCredentials(){
        val token = userPreference.getToken()
        if(token != null){
            startActivity(Intent(this@LoginActivity, ListStoryActivity::class.java))
            finish()
        }
    }
}