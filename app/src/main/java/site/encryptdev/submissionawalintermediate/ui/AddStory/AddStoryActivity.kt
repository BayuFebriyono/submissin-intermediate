package site.encryptdev.submissionawalintermediate.ui.AddStory

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import site.encryptdev.submissionawalintermediate.R
import site.encryptdev.submissionawalintermediate.databinding.ActivityAddStoryBinding
import site.encryptdev.submissionawalintermediate.utils.UserPreference
import site.encryptdev.submissionawalintermediate.utils.getImageUri
import site.encryptdev.submissionawalintermediate.utils.reduceFileImage
import site.encryptdev.submissionawalintermediate.utils.uriToFile

class AddStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddStoryBinding
    private val viewModel: AddStoryViewModel by viewModels()
    private val userPreference: UserPreference by lazy {
        UserPreference(this)
    }
    private var currentImageUri: Uri? = null


    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }
        viewModel.isLoading.observe(this){
            showLoading(it)
        }

        viewModel.isSuccess.observe(this){
            if (it){
                Toast.makeText(this,viewModel.statusMessage.value.toString(), Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this,viewModel.statusMessage.value.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnGallery.setOnClickListener { startGallery() }
        binding.btnCamera.setOnClickListener { startCamera() }
        binding.btnUpload.setOnClickListener { uploadStory() }

    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if(uri != null){
            currentImageUri = uri
            showImage()
        }else{
            Toast.makeText(this@AddStoryActivity, "Tidak ada gambar dipilih", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startCamera(){
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri!!)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ){isSuccess ->
        if (isSuccess){
            showImage()
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            binding.imgStory.setImageURI(it)
        }
    }

    private fun uploadStory(){
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()
            val description = binding.deskripsiEditText.text.toString()

            val requestBody = description.toRequestBody("text/plain".toMediaType())
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "photo",
                imageFile.name,
                requestImageFile
            )

            val token = userPreference.getToken()
            viewModel.addStory(token!!, multipartBody, requestBody)
        }
    }

    private fun showLoading(isLoading: Boolean){
        binding.progressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}
