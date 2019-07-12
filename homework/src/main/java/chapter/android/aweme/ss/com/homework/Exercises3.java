package chapter.android.aweme.ss.com.homework;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import chapter.android.aweme.ss.com.homework.widget.CircleImageView;

/**
 * 大作业:实现一个抖音消息页面,所需资源已放在res下面
 */
public class Exercises3 extends AppCompatActivity {

    private final String TAG = "HomeWork3";
    public static List<News> newsList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取data.xml文件的内容
        try{
            InputStream open = getAssets().open("data.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(open);
            Element element = document.getDocumentElement();
            NodeList nodeList = element.getElementsByTagName("message");
            Log.d(TAG, "message count: " + nodeList.getLength());
            for(int i = 0; i < nodeList.getLength(); i++){
                Element msg = (Element)nodeList.item(i);
                String[] tempString = new String[4];
                tempString[0] = msg.getElementsByTagName("title").item(0).getTextContent();
                tempString[1] = msg.getElementsByTagName("hashtag").item(0).getTextContent();
                tempString[2] = msg.getElementsByTagName("time").item(0).getTextContent();
                tempString[3] = msg.getElementsByTagName("icon").item(0).getTextContent();

                News tempNews = new News(tempString);
                newsList.add(tempNews);
            }
        }catch(Exception e){
            System.out.println(e);
        }

        setContentView(R.layout.activity_news);
        ListView listView = findViewById(R.id.news_list);
        listView.setAdapter(new ListViewAdapter(this));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                News tempNews = newsList.get(position);
                Intent intent = new Intent(Exercises3.this, ChatRoom.class);
                intent.putExtra("position", position);
                intent.putExtra("friendName", tempNews.title);
                intent.putExtra("message", tempNews.hashtag);
                Toast.makeText(Exercises3.this, "title"+ position + ": " + tempNews.title, Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

    }

    public static class ListViewAdapter extends BaseAdapter{

        private Context mContext;

        public ListViewAdapter(Context context) {
            mContext = context;
        }

        @Override public int getCount() {
            return newsList.size();
        }

        @Override public Object getItem(int position) {
            return null;
        }

        @Override public long getItemId(int position) {
            return 0;
        }

        @Override public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            //View test = LayoutInflater.from(this).inflate(R.layout.activity_news, null);
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                view = inflater.inflate(R.layout.im_list_item, null);
            } else {
                view = convertView;
            }
            News tempNews = newsList.get(position);
            View userAvatar = ((ViewGroup)((ViewGroup)view).getChildAt(0)).getChildAt(0);
            switch (tempNews.icon){
                case "TYPE_ROBOT":
                    ((ImageView)userAvatar).setImageDrawable(mContext.getResources().getDrawable(R.drawable.session_robot));
                    break;
                case "TYPE_GAME":
                    ((ImageView)userAvatar).setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_micro_game_comment));
                    break;
                case "TYPE_SYSTEM":
                    ((ImageView)userAvatar).setImageDrawable(mContext.getResources().getDrawable(R.drawable.session_system_notice));
                    break;
                case "TYPE_STRANGER":
                    ((ImageView)userAvatar).setImageDrawable(mContext.getResources().getDrawable(R.drawable.session_stranger));
                    break;
                case "TYPE_USER":
                    ((ImageView)userAvatar).setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_girl));
                    break;
                default:
                    ((ImageView)userAvatar).setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_launcher_foreground));
                    break;
            }
            View newsSender = ((ViewGroup)view).getChildAt(1);
            ((TextView)newsSender).setText(tempNews.title);
            View newsSummary = ((ViewGroup)view).getChildAt(2);
            ((TextView)newsSummary).setText(tempNews.hashtag);
            View newsTime = ((ViewGroup)view).getChildAt(3);
            ((TextView)newsTime).setText(tempNews.time);

            return view;
        }
    }
    //用来装载收到的消息
    public class News{
        String title;
        String hashtag;
        String time;
        String icon;

        News(String title, String hashtag, String time, String icon){
            this.title = title;
            this.hashtag = hashtag;
            this.time = time;
            this.icon = icon;
        }

        News(String[] newsArray){
            this.title = newsArray[0];
            this.hashtag = newsArray[1];
            this.time = newsArray[2];
            this.icon = newsArray[3];
        }

        public String[] getData(){
            String[] newsArray = new String[4];
            newsArray[0] = title;
            newsArray[1] = hashtag;
            newsArray[2] = time;
            newsArray[3] = icon;
            return newsArray;
        }
    }
}
