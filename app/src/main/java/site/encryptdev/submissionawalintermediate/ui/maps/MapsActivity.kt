package site.encryptdev.submissionawalintermediate.ui.maps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import site.encryptdev.submissionawalintermediate.R
import site.encryptdev.submissionawalintermediate.databinding.ActivityMapsBinding
import site.encryptdev.submissionawalintermediate.ui.story.StoryViewModel
import site.encryptdev.submissionawalintermediate.utils.UserPreference

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val viewModel: MapsViewModel by viewModels()
    private val userPreference: UserPreference by lazy {
        UserPreference(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val token = userPreference.getToken()
        viewModel.getAllStory(token ?: "")
        viewModel.data.observe(this){data ->

            data?.forEach { dat->
                val latLong = LatLng(dat?.lat!!.toDouble(), dat.lon!!.toDouble())
                mMap.addMarker(
                    MarkerOptions()
                        .position(latLong)
                        .title(dat.name)
                        .snippet(dat.description)
                )
            }
        }
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


    }
}