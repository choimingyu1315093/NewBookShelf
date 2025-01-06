package com.example.newbookshelf.presentation.view.chat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.model.chat.ChatList
import com.example.newbookshelf.data.model.chat.ChatListModel
import com.example.newbookshelf.data.model.chat.ChatMessageModelData
import com.example.newbookshelf.data.model.chat.ChatroomInfoModel
import com.example.newbookshelf.data.model.chat.ChatroomModel
import com.example.newbookshelf.databinding.FragmentChatBinding
import com.example.newbookshelf.presentation.view.chat.adapter.ChatListAdapter
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URISyntaxException

class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding

    companion object {
        const val TAG = "ChatFragment"
    }

    private lateinit var accessToken: String
    private lateinit var chatListAdapter: ChatListAdapter

    private var mSocket: Socket? = null

    private var chatroomList = arrayListOf<ChatList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        (activity as HomeActivity).binding.cl.visibility = View.GONE
        (activity as HomeActivity).binding.bottomNavigationView.visibility = View.GONE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChatBinding.bind(view)

        init()
        bindViews()
    }

    private fun init() = with(binding){
        accessToken = BookShelfApp.prefs.getAccessToken("accessToken", "")
        chatListAdapter = (activity as HomeActivity).chatListAdapter
        chatListAdapter.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("chatroomInfo", ChatroomInfoModel(it.chat_room_idx, it.books.book_image, it.opponent_user[0].users.user_name))
            findNavController().navigate(R.id.action_chatFragment_to_chatroomFragment, bundle)
        }
        rvChatList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = chatListAdapter
        }

        connectSocket()
    }

    private fun bindViews() = with(binding){
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun connectSocket() = with(binding){
        try {
            val opts = IO.Options()
            val headers = HashMap<String, List<String>>()
            headers["token"] = listOf(accessToken)
            opts.extraHeaders = headers
            opts.forceNew = true
            opts.reconnection = false
            mSocket =
                IO.socket(
                    "https://bookshelf-chat-prod.forbookshelf.com",
                    opts
                )
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }

        mSocket?.on("roomInfo", onRoomInfo)
        mSocket?.on("chatRoomUpdate", onChatRoomUpdate)
        mSocket?.connect()
    }

    private var onRoomInfo: Emitter.Listener = Emitter.Listener { args ->
        lifecycleScope.launch(Dispatchers.Main) {
            val data = args[0] as JSONObject
            val jsonParser = JsonParser()
            val jsonObject = jsonParser.parse(data.toString()) as JsonObject

            val gson = Gson()
            val chatListData = gson.fromJson(jsonObject, ChatListModel::class.java)

            if(chatListData.result.size != 0){
                BookShelfApp.prefs.setChatUserIdx("chatUserIdx", chatListData.result[0].me_user.users.user_idx)
                chatroomList = chatListData.result
                chatListAdapter.differ.submitList(chatroomList)
            }else {
                binding.rvChatList.visibility = View.GONE
                binding.ivEmpty.visibility = View.VISIBLE
                binding.tvEmpty.visibility = View.VISIBLE
            }
            binding.progressBar.visibility = View.GONE
        }
    }

    private var onChatRoomUpdate: Emitter.Listener = Emitter.Listener { args ->
        lifecycleScope.launch(Dispatchers.Main) {
            val data = args[0] as JSONObject
            val jsonParser = JsonParser()
            val jsonObject = jsonParser.parse(data.toString()) as JsonObject
            Log.d(TAG, "onChatRoomUpdate jsonObject $jsonObject")

            val gson = Gson()
            val chatListData = gson.fromJson(jsonObject, ChatListModel::class.java)

            chatListAdapter.differ.submitList(chatListData.result)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mSocket?.off("roomInfo", onRoomInfo)
        mSocket?.off("chatRoomUpdate ", onChatRoomUpdate)
        mSocket?.disconnect()
        (activity as HomeActivity).binding.cl.visibility = View.VISIBLE
        (activity as HomeActivity).binding.bottomNavigationView.visibility = View.VISIBLE
    }
}