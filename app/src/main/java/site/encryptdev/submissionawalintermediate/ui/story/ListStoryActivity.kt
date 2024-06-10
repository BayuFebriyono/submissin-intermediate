package site.encryptdev.submissionawalintermediate.ui.story

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import site.encryptdev.submissionawalintermediate.data.remote.response.ListStoryItem
import site.encryptdev.submissionawalintermediate.databinding.ActivityListStoryBinding
import site.encryptdev.submissionawalintermediate.dto.Story
import site.encryptdev.submissionawalintermediate.ui.AddStory.AddStoryActivity
import site.encryptdev.submissionawalintermediate.ui.login.LoginActivity
import site.encryptdev.submissionawalintermediate.ui.maps.MapsActivity
import site.encryptdev.submissionawalintermediate.utils.UserPreference

class ListStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListStoryBinding
    private val viewModel: StoryViewModel by viewModels {
        ViewModelFactory(this)
    }
    private val userPreference: UserPreference by lazy {
        UserPreference(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvStory.layoutManager = LinearLayoutManager(this@ListStoryActivity)



        val token = userPreference.getToken()
        setAdapter()

        binding.btnLogout.setOnClickListener {
            userPreference.clearCredentials()
            startActivity(Intent(this@ListStoryActivity, LoginActivity::class.java))
            finish()

        }

        binding.btnMap.setOnClickListener {
            startActivity(Intent(this@ListStoryActivity, MapsActivity::class.java))
        }

        binding.btnAddStory.setOnClickListener {
            startActivity(Intent(this@ListStoryActivity, AddStoryActivity::class.java))
        }


    }

    override fun onResume() {
        super.onResume()
        val token = userPreference.getToken()

    }

    private fun setAdapter() {
        val adapter = StoryAdapter()
        binding.rvStory.adapter = adapter
        viewModel.story.observe(this, {
            adapter.submitData(lifecycle, it)
        })
    }

    private fun setLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}