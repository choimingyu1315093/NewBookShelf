package com.example.newbookshelf.presentation.view.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.ActivityHomeBinding
import com.example.newbookshelf.presentation.view.chat.adapter.ChatListAdapter
import com.example.newbookshelf.presentation.view.chat.adapter.ChatMessageAdapter
import com.example.newbookshelf.presentation.view.detail.adapter.MemoAdapter
import com.example.newbookshelf.presentation.view.detail.adapter.ReviewAdapter
import com.example.newbookshelf.presentation.view.home.adapter.AttentionBestsellerAdapter
import com.example.newbookshelf.presentation.view.home.adapter.NewBestsellerAdapter
import com.example.newbookshelf.presentation.view.home.adapter.NotificationAdapter
import com.example.newbookshelf.presentation.view.home.adapter.SearchBookAdapter
import com.example.newbookshelf.presentation.view.home.adapter.SearchBookTitleAdapter
import com.example.newbookshelf.presentation.view.home.adapter.SearchMoreBookAdapter
import com.example.newbookshelf.presentation.view.home.adapter.WeekBestsellerAdapter
import com.example.newbookshelf.presentation.view.map.adapter.NearBookAdapter
import com.example.newbookshelf.presentation.view.post.adapter.KakaoAdapter
import com.example.newbookshelf.presentation.view.profile.adapter.MyBookListAdapter
import com.example.newbookshelf.presentation.view.profile.adapter.ProfileActiveAdapter
import com.example.newbookshelf.presentation.view.profile.adapter.ProfileMemoAdapter
import com.example.newbookshelf.presentation.view.setting.adapter.ChargeLogAdapter
import com.example.newbookshelf.presentation.viewmodel.chat.ChatViewModel
import com.example.newbookshelf.presentation.viewmodel.chat.ChatViewModelFactory
import com.example.newbookshelf.presentation.viewmodel.detail.DetailViewModel
import com.example.newbookshelf.presentation.viewmodel.detail.DetailViewModelFactory
import com.example.newbookshelf.presentation.viewmodel.home.HomeViewModel
import com.example.newbookshelf.presentation.viewmodel.home.HomeViewModelFactory
import com.example.newbookshelf.presentation.viewmodel.map.MapViewModel
import com.example.newbookshelf.presentation.viewmodel.map.MapViewModelFactory
import com.example.newbookshelf.presentation.viewmodel.post.PostViewModel
import com.example.newbookshelf.presentation.viewmodel.post.PostViewModelFactory
import com.example.newbookshelf.presentation.viewmodel.profile.ProfileViewModel
import com.example.newbookshelf.presentation.viewmodel.profile.ProfileViewModelFactory
import com.example.newbookshelf.presentation.viewmodel.setting.SettingViewModel
import com.example.newbookshelf.presentation.viewmodel.setting.SettingViewModelFactory
import com.google.android.gms.maps.MapView
import com.kakao.sdk.common.json.IntEnum
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

    @Inject
    lateinit var profileViewModelFactory: ProfileViewModelFactory
    lateinit var profileViewModel: ProfileViewModel
    @Inject
    lateinit var profileActiveAdapter: ProfileActiveAdapter
    @Inject
    lateinit var profileMemoAdapter: ProfileMemoAdapter
    @Inject
    lateinit var myBookListAdapter: MyBookListAdapter

    @Inject
    lateinit var mapViewModelFactory: MapViewModelFactory
    lateinit var mapViewModel: MapViewModel
    @Inject
    lateinit var nearBookAdapter: NearBookAdapter

    @Inject
    lateinit var chatViewModelFactory: ChatViewModelFactory
    lateinit var chatViewMode: ChatViewModel
    @Inject
    lateinit var chatListAdapter: ChatListAdapter
    @Inject
    lateinit var chatMessageAdapter: ChatMessageAdapter

    @Inject
    lateinit var postViewModelFactory: PostViewModelFactory
    lateinit var postViewModel: PostViewModel
    @Inject
    lateinit var kakaoAdapter: KakaoAdapter

    @Inject
    lateinit var settingViewModelFactory: SettingViewModelFactory
    lateinit var settingViewModel: SettingViewModel
    @Inject
    lateinit var chargeLogAdapter: ChargeLogAdapter

    companion object {
        const val TAG = "HomeActivity"
    }

    lateinit var navController: NavController
    lateinit var accessToken: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        bindViews()
        observeViewModel()
    }

    private fun init() = with(binding){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)

        accessToken = BookShelfApp.prefs.getAccessToken("accessToken", "")

        homeViewModel = ViewModelProvider(this@HomeActivity, homeViewModelFactory).get(HomeViewModel::class.java)
        detailViewModel = ViewModelProvider(this@HomeActivity, detailViewModelFactory).get(DetailViewModel::class.java)
        profileViewModel = ViewModelProvider(this@HomeActivity, profileViewModelFactory).get(ProfileViewModel::class.java)
        mapViewModel = ViewModelProvider(this@HomeActivity, mapViewModelFactory).get(MapViewModel::class.java)
        chatViewMode = ViewModelProvider(this@HomeActivity, chatViewModelFactory).get(ChatViewModel::class.java)
        postViewModel = ViewModelProvider(this@HomeActivity, postViewModelFactory).get(PostViewModel::class.java)
        settingViewModel = ViewModelProvider(this@HomeActivity, settingViewModelFactory).get(SettingViewModel::class.java)

        onBackPressedDispatcher.addCallback(this@HomeActivity) {
            if (!navController.popBackStack()) {
                finish()
            }
        }
    }

    private fun bindViews() = with(binding){
        ivSearch.setOnClickListener {
            cl.visibility = View.GONE
            findNavController(R.id.fragmentContainerView).navigate(R.id.action_homeFragment_to_searchBookFragment)
        }

        ivChat.setOnClickListener {
            cl.visibility = View.GONE
            bottomNavigationView.visibility = View.GONE
            findNavController(R.id.fragmentContainerView).navigate(R.id.action_homeFragment_to_chatFragment)
        }

        ivBell.setOnClickListener {
            cl.visibility = View.GONE
            bottomNavigationView.visibility = View.GONE
            findNavController(R.id.fragmentContainerView).navigate(R.id.action_homeFragment_to_notificationFragment)
        }
    }

    private fun observeViewModel() = with(binding){
        homeViewModel.alarmCount(accessToken).observe(this@HomeActivity){ response ->
            when(response){
                is Resource.Success -> {
                    response.data?.let {
                        if(it.data == 0){
                            tvNotifyCount.visibility = View.GONE
                        }else {
                            tvNotifyCount.visibility = View.VISIBLE
                            tvNotifyCount.text = "${it.data}"
                        }
                    }
                }
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
            }
        }
    }
}