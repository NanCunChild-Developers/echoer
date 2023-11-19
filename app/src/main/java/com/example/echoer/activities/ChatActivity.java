package com.example.echoer.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.example.echoer.R;
import com.example.echoer.databinding.ActivityChatBinding;
import com.example.echoer.models.ChatMessage;
import com.example.echoer.adapters.ChatMessageAdapter;
import com.example.echoer.utilities.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private ActivityChatBinding binding;
    private List<ChatMessage> chatMessages;
    private ChatMessageAdapter chatAdapter;

    private List<String> presetReplies = Arrays.asList("回复消息1", "回复消息2", "回复消息3");
    private int replyIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
        initMessageList();
        init();

        Button buttonMac = findViewById(R.id.buttonMac);
        buttonMac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNodeInfoDialog();
            }
        });
    }

    private void showNodeInfoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Node Information");

        // 设置弹框的消息内容
        // 您可以根据需要显示静态文本或动态生成的内容
        builder.setMessage("Node 1: Information\nNode 2: Information\n...");

        // 添加按钮
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // 显示弹框
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void init() {
        // 配置布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        binding.chatRecycleView.setLayoutManager(manager);

        chatMessages = new ArrayList<>();
        chatAdapter = new ChatMessageAdapter(chatMessages);
        binding.chatRecycleView.setAdapter(chatAdapter);
    }

    private void initMessageList() {
        chatMessages = new ArrayList<ChatMessage>();
        ChatMessage msg1 = new ChatMessage("20231022", "Hello, World!", "Jobs", Constants.VIEW_TYPE_RECEIVED);
        chatMessages.add(msg1);
        ChatMessage msg2 = new ChatMessage("20231026", "Beep", "Me", Constants.VIEW_TYPE_SENT);
        chatMessages.add(msg2);
        ChatMessage msg3 = new ChatMessage("20231026", "Don't stop me now", "Jobs", Constants.VIEW_TYPE_RECEIVED);
        chatMessages.add(msg3);
    }

    private void sendAutoReply() {
        if (replyIndex >= presetReplies.size()) {
            replyIndex = 0; // 如果到达列表末尾，则重置索引
        }
        String reply = presetReplies.get(replyIndex++);
        chatMessages.add(new ChatMessage("20231029", reply, "Other", Constants.VIEW_TYPE_RECEIVED));
        chatAdapter.notifyItemInserted(chatMessages.size() - 1);
        binding.chatRecycleView.smoothScrollToPosition(chatMessages.size() - 1);
    }

    private void setListeners() {
        binding.imageBack.setOnClickListener(v -> finish());

        binding.send.setOnClickListener(v -> {
            String message = binding.inputText.getText().toString();
            if (!message.equals("")) {
                // 添加您的消息到聊天界面
                chatMessages.add(new ChatMessage("20231029", message, "Me", Constants.VIEW_TYPE_SENT));
                chatAdapter.notifyItemInserted(chatMessages.size() - 1);
                binding.chatRecycleView.smoothScrollToPosition(chatMessages.size() - 1);
                binding.inputText.setText(""); // 清空输入框

                // 延迟发送自动回复
                new Handler().postDelayed(this::sendAutoReply, 3000); // 延迟3秒后回复
            }
        });

    }

}