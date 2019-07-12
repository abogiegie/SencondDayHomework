package chapter.android.aweme.ss.com.homework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ChatRoom extends AppCompatActivity {

    private final static String TAG = "HomeWork3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        TextView title = findViewById(R.id.tv_with_name);
        TextView news = findViewById(R.id.tv_content_info);

        Log.d(TAG, "position: " + getIntent().getIntExtra("position", -1));
        title.setText(getIntent().getStringExtra("friendName"));
        news.setText(getIntent().getStringExtra("message"));
    }
}
