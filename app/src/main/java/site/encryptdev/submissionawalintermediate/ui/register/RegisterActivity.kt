package site.encryptdev.submissionawalintermediate.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import site.encryptdev.submissionawalintermediate.databinding.ActivityRegisterBinding
import site.encryptdev.submissionawalintermediate.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            finish()
        }

        binding.btnRegis.setOnClickListener {
            login()
        }

        viewModel.isSuccess.observe(this){
            if (it == true){
                Toast.makeText(this,"Akun berhasil dibuat silahkan login", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"Terjadi Kesalahan Silahkan Coba Lagi", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.isLoading.observe(this){
            setLoading(it)
        }
    }

    private fun login(){
        val name = binding.nameEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val pass = binding.passwordEditText.text.toString()

        viewModel.login(name,email,pass)


    }
    private fun setLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

}