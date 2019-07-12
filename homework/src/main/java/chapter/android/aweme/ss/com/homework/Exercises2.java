package chapter.android.aweme.ss.com.homework;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * 作业2：一个抖音笔试题：统计页面所有view的个数
 * Tips：ViewGroup有两个API
 * {@link android.view.ViewGroup #getChildAt(int) #getChildCount()}
 * 用一个TextView展示出来
 *
 */
public class Exercises2 extends AppCompatActivity {

    private View root;
    private final String TAG = "HomeWork2";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.im_list_item);
        root = findViewById(R.id.root);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //不包含最外面的
        Log.d(TAG, "viewCount:" + getViewCount(root));
    }

    public static int getViewCount(View view) {
        //todo 补全你的代码
        if(view != null && view instanceof ViewGroup){
            int count = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < count; i++){
                View childI = ((ViewGroup) view).getChildAt(i);
                if(childI instanceof ViewGroup){
                    count += getViewCount(childI);
                }
            }
            return count;
        }
        else{
            return 0;
        }

    }
}
