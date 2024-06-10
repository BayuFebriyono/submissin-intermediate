package site.encryptdev.submissionawalintermediate.ui.story

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import site.encryptdev.submissionawalintermediate.R
import site.encryptdev.submissionawalintermediate.databinding.ActivityDetailStoryBinding
import site.encryptdev.submissionawalintermediate.dto.Story

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val story = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<Story>(EXTRA_STORY, Story::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Story>(EXTRA_STORY)
        }

        if(story !=null){
            binding.tvName.text = story.name
            binding.tvDescription.text = story.description
            Glide.with(this@DetailStoryActivity)
                .load(story.imgUrl)
                .into(binding.imageView)
        }
    }

    companion object{
        const val EXTRA_STORY = "extra_story"
    }
}