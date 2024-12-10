package com.example.newbookshelf.presentation.view.home

import android.os.Bundle
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.newbookshelf.R
import com.example.newbookshelf.databinding.ActivityHomeBinding
import com.example.newbookshelf.presentation.view.detail.adapter.MemoAdapter
import com.example.newbookshelf.presentation.view.detail.adapter.ReviewAdapter
import com.example.newbookshelf.presentation.view.home.adapter.AttentionBestsellerAdapter
import com.example.newbookshelf.presentation.view.home.adapter.NewBestsellerAdapter
import com.example.newbookshelf.presentation.view.home.adapter.NotificationAdapter
import com.example.newbookshelf.presentation.view.home.adapter.SearchBookAdapter
import com.example.newbookshelf.presentation.view.home.adapter.SearchBookTitleAdapter
import com.example.newbookshelf.presentation.view.home.adapter.SearchMoreBookAdapter
import com.example.newbookshelf.presentation.view.home.adapter.WeekBestsellerAdapter
import com.example.newbookshelf.presentation.viewmodel.detail.DetailViewModel
import com.example.newbookshelf.presentation.viewmodel.detail.DetailViewModelFactory
import com.example.newbookshelf.presentation.viewmodel.home.HomeViewModel
import com.example.newbookshelf.presentation.viewmodel.home.HomeViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    @Inject
    lateinit var homeViewModelFactory: HomeViewModelFactory
    lateinit var homeViewModel: HomeViewModel
    @Inject
    lateinit var weekBestsellerAdapter: WeekBestsellerAdapter
    @Inject
    lateinit var newBestsellerAdapter: NewBestsellerAdapter
    @Inject
    lateinit var attentionBestseller: AttentionBestsellerAdapter
    @Inject
    lateinit var notificationAdapter: NotificationAdapter
    @Inject
    lateinit var searchBookTitleAdapter: SearchBookTitleAdapter
    @Inject
    lateinit var searchBookAdapter: SearchBookAdapter
    @Inject
    lateinit var searchMoreBookAdapter: SearchMoreBookAdapter

    @Inject
    lateinit var detailViewModelFactory: DetailViewModelFactory
    lateinit var detailViewModel: DetailViewModel
    @Inject
    lateinit var reviewAdapter: ReviewAdapter
    @Inject
    lateinit var memoAdapter: MemoAdapter

    companion object {
        const val TAG = "HomeActivity"
    }

    lateinit var navController: NavController
    var backKeyPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        homeViewModel = ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)
        detailViewModel = ViewModelProvider(this, detailViewModelFactory).get(DetailViewModel::class.java)
    }

    private fun init() = with(binding){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)

        onBackPressedDispatcher.addCallback(this@HomeActivity) {
            if (!navController.popBackStack()) {
                finish()
            }
        }
    }
}