package com.example.newbookshelf.presentation.view.chat

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.model.chat.ChatMessageModel
import com.example.newbookshelf.data.model.chat.ChatMessageModelData
import com.example.newbookshelf.data.model.chat.ChatroomInfoModel
import com.example.newbookshelf.databinding.FragmentChatroomBinding
import com.example.newbookshelf.presentation.view.chat.adapter.ChatMessageAdapter
import com.example.newbookshelf.presentation.view.chat.dialog.ChatroomDeleteDialog
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.view.login.FindFragmentArgs
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.URISyntaxException

class ChatroomFragment : Fragment(), ChatroomDeleteDialog.OnChatroomOutListener {
    private lateinit var binding: FragmentChatroomBinding
    private lateinit var chatMessageAdapter: ChatMessageAdapter

    companion object {
        const val TAG = "ChatroomFragment"
    }

    private lateinit var accessToken: String
    private lateinit var chatroomInfoModel: ChatroomInfoModel

    private var mSocket: Socket? = null

    private var chatMessageList = arrayListOf<ChatMessageModelData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        (activity as HomeActivity).binding.cl.visibility = View.GONE
        (activity as HomeActivity).binding.bottomNavigationView.visibility = View.GONE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chatroom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChatroomBinding.bind(view)

        val args: ChatroomFragmentArgs by navArgs()
        chatroomInfoModel = args.chatroomInfo

        init()
        bindViews()
    }

    private fun init() = with(binding){
        accessToken = BookShelfApp.prefs.getAccessToken("accessToken", "")
        binding.tvName.text = chatroomInfoModel.name
        chatMessageAdapter = (activity as HomeActivity).chatMessageAdapter
        chatMessageAdapter.image = chatroomInfoModel.image ?: ""
        rvChat.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = chatMessageAdapter
        }

        touchView.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val inputMethodManager = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(etChat.windowToken, 0)
            }
            false
        }

        etChat.movementMethod = ScrollingMovementMethod()

        connectSocket()
    }

    private fun bindViews() = with(binding){
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        txtOut.setOnClickListener {
            val dialog = ChatroomDeleteDialog(chatroomInfoModel.chatroomIdx, this@ChatroomFragment)
            dialog.show(requireActivity().supportFragmentManager, "DeleteChatroomDialog")
        }

        ivSend.setOnClickListener {
            var etMsg = binding.etChat.text.toString()
            if (etMsg.isNotEmpty()) {
                var json = JSONObject()
                json.put("chat_room_idx", chatroomInfoModel.chatroomIdx)
                json.put("message_content", etMsg)

                mSocket?.emit("sendMessage", json)
                binding.etChat.setText("")
            }
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
            mSocket?.connect()
            mSocket?.on(Socket.EVENT_CONNECT) {
                CoroutineScope(Dispatchers.IO).launch {
                    delay(150)
                    val json = JSONObject()
                    json.put("chat_room_idx", chatroomInfoModel.chatroomIdx)
                    mSocket?.emit("joinRoom", json)
                }
                val json = JSONObject()
                json.put("chat_room_idx", chatroomInfoModel.chatroomIdx)
                mSocket?.emit("joinRoom", json)
            }
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }

        mSocket?.on("chatInfo", onChatInfo)
        mSocket?.on("receiveMessage", onNewMessage)
    }

    private var onChatInfo: Emitter.Listener = Emitter.Listener { args ->
        lifecycleScope.launch(Dispatchers.Main) {
            val data = args[0] as JSONObject
            val jsonParser = JsonParser()
            val jsonObject = jsonParser.parse(data.toString()) as JsonObject

            val gson = Gson()
            val chatMessageData = gson.fromJson(jsonObject, ChatMessageModel::class.java)

            chatMessageList = chatMessageData.result
            chatMessageAdapter.differ.submitList(chatMessageList)
        }
    }

    private var onNewMessage: Emitter.Listener = Emitter.Listener { args ->
        requireActivity().runOnUiThread(Runnable {
            val data = args[0] as JSONObject
            val jsonParser = JsonParser()
            val jsonObject = jsonParser.parse(data.toString()) as JsonObject

            val gson = Gson()
            val chatMessageData = gson.fromJson(jsonObject, ChatMessageModelData::class.java)

            chatMessageList.add(chatMessageData)
            chatMessageAdapter.differ.submitList(chatMessageList)

            binding.rvChat.scrollToPosition(chatMessageList.size - 1)
        })
    }

    override fun outChatroom(b: Boolean) {
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mSocket?.off("chatInfo", onChatInfo)
        mSocket?.off("receiveMessage ", onNewMessage)

        var json = JSONObject()
        json.put("chat_room_idx", chatroomInfoModel.chatroomIdx)
        json.put("chat_user_idx", BookShelfApp.prefs.getChatUserIdx("chatUserIdx", 0))
        mSocket?.emit("exitRoom", json)

        mSocket?.disconnect()
        (activity as HomeActivity).binding.cl.visibility = View.VISIBLE
        (activity as HomeActivity).binding.bottomNavigationView.visibility = View.VISIBLE
    }
}