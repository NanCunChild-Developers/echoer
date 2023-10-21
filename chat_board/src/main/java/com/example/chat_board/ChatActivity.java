package com.example.chat_board;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.app.R;
import com.example.app.databinding.ActivityChatBinding;

import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private List<ChatMessage> chatMessageList;
    private ChatAdapter chatAdapter;
    private ActivityChatBinding activityChatBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ImageView imgBack = findViewById(R.id.imageBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}