package com.example.newbookshelf.presentation.view.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.model.chat.ChatroomInfoModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentMapBinding
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.view.login.LoginActivity
import com.example.newbookshelf.presentation.view.login.LoginActivity.Companion
import com.example.newbookshelf.presentation.view.map.adapter.NearBookAdapter
import com.example.newbookshelf.presentation.view.map.dialog.NearBookDialog
import com.example.newbookshelf.presentation.viewmodel.login.LoginViewModel
import com.example.newbookshelf.presentation.viewmodel.map.MapViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class MapFragment : Fragment(), OnMapReadyCallback, NearBookDialog.OnDialogClose {
    private lateinit var binding: FragmentMapBinding
    private lateinit var mapViewModel: MapViewModel

    companion object {
        const val TAG = "MapFragment"
    }

    private lateinit var accessToken: String
    private lateinit var nearBookAdapter: NearBookAdapter

    private lateinit var map: GoogleMap
    private lateinit var currentLocation: LatLng
    private var marker = MarkerOptions()
    private var image: String? = null

    private val rvNearBook: RecyclerView by lazy {
        requireActivity().findViewById(R.id.rvNearBook)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        (activity as HomeActivity).binding.cl.visibility = View.GONE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMapBinding.bind(view)

        init()
        observeViewModel()
    }

    private fun init() = with(binding){
        currentLocation = LatLng(BookShelfApp.prefs.getLatitude("latitude", 0.0F).toDouble(), BookShelfApp.prefs.getLongitude("longitude", 0.0F).toDouble())
        accessToken = BookShelfApp.prefs.getAccessToken("accessToken", "")
        mapViewModel = (activity as HomeActivity).mapViewModel
        nearBookAdapter = (activity as HomeActivity).nearBookAdapter
        nearBookAdapter.setOnClickListener {
            val clickLocation = LatLng(it.current_latitude.toDouble(), it.current_longitude.toDouble())
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(clickLocation, 13f)
            map.animateCamera(cameraUpdate)
        }
        rvNearBook.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = nearBookAdapter
        }

        setupGoogleMap()
    }

    @SuppressLint("PotentialBehaviorOverride")
    private fun observeViewModel() = with(binding){
        mapViewModel.wishBookHaveUser(accessToken).observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    if(response.data!!.data.isNotEmpty()){
                        nearBookAdapter.differ.submitList(response.data.data)

                        lifecycleScope.launch(Dispatchers.IO) {
                            for(i in 0 until response.data.data.size){
                                val imageUrl = URL(response.data.data[i].book_image)
                                val connection = imageUrl.openConnection()
                                val iconStream = connection.getInputStream()
                                val bitmap = BitmapFactory.decodeStream(iconStream)
                                val desiredWidth = 100
                                val desiredHeight = 150
                                val resizedBitmap = Bitmap.createScaledBitmap(bitmap, desiredWidth, desiredHeight, true)

                                val nearBookLocation = LatLng(response.data.data[i].current_latitude.toDouble(), response.data.data[i].current_longitude.toDouble())
                                withContext(Dispatchers.Main){
                                    marker = MarkerOptions().apply {
                                        position(nearBookLocation)
                                        title("${response.data.data[i].book_name}^^${response.data.data[i].book_image}^^${response.data.data[i].user_name}^^${response.data.data[i].user_idx}^^${response.data.data[i].book_isbn}")
                                        icon(BitmapDescriptorFactory.fromBitmap(resizedBitmap))
                                    }

                                    map.addMarker(marker)
                                    map.setOnMarkerClickListener { marker ->
                                        val title = marker.title!!.split("^^")[0]
                                        val img = marker.title!!.split("^^")[1]
                                        val name = marker.title!!.split("^^")[2]
                                        val userIdx = marker.title!!.split("^^")[3]
                                        val isbn = marker.title!!.split("^^")[4]
                                        image = img
                                        val dialog = NearBookDialog(title, img, name, userIdx.toInt(), isbn, this@MapFragment)
                                        dialog.show(requireActivity().supportFragmentManager, "NearBookDialog")
                                        true
                                    }

                                    val cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentLocation, 13f)
                                    map.animateCamera(cameraUpdate)
                                    map.apply {
                                        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                            return@withContext
                                        }
                                        isMyLocationEnabled = true
                                        uiSettings.isTiltGesturesEnabled = true
                                        uiSettings.isMyLocationButtonEnabled = false
                                        uiSettings.isCompassEnabled = false
                                    }
                                }
                            }
                        }
                    }
                }
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
            }
        }
    }

    private fun setupGoogleMap() = with(binding){
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this@MapFragment)
    }

    override fun isClose(b: Boolean, chatroomIdx: Int, name: String) {
        val bundle = Bundle().apply {
            putParcelable("chatroomInfo", ChatroomInfoModel(chatroomIdx, image, name))
        }
        findNavController().navigate(R.id.action_mapFragment_to_chatroomFragment, bundle)
        (activity as HomeActivity).binding.bottomNavigationView.visibility = View.GONE
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentLocation, 13f)
        map.animateCamera(cameraUpdate)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as HomeActivity).binding.cl.visibility = View.VISIBLE
    }
}