package com.example.charlene.fkc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.charlene.fkc.adapter.DynamicAdapter;
import com.example.charlene.fkc.adapter.FriendAdapter;
import com.example.charlene.fkc.Utils.CharacterParser;
import com.example.charlene.fkc.listview.DynamicListView;
import com.example.charlene.fkc.listview.FriendListView;
import com.example.charlene.fkc.pickers.AddFriendsPicker;
import com.example.charlene.fkc.temporarydatastorage.FriendsListData;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MainActivity extends FragmentActivity
        implements View.OnClickListener,NavigationView.OnNavigationItemSelectedListener {

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private List<DynamicListView> dynamicListViews = new ArrayList<>();
    private List<FriendListView> mFriendListViews = new ArrayList<>();
    private FriendAdapter friendAdapter;
    private DynamicAdapter dynamicAdapter;
    private LinearLayout dynamicbutton;
    private LinearLayout friendbutton;
    private LinearLayout browserbutton;
    private ImageView add_btn;
    private ImageView openDrawerLayout ;
    private ImageView writeDynameicBtn ;
    private FriendsListData friendsListData;
    private ViewPager pager;
    private WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setView();
        initDynamic();
        initFriend();
        dynamicAdapter = new DynamicAdapter(MainActivity.this,R.layout.item_dynamic,dynamicListViews);
        friendAdapter = new FriendAdapter(MainActivity.this,R.layout.item_friend,mFriendListViews);

        initViewPager();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initViewPager(){
        pager = (ViewPager) findViewById(R.id.index_viewpager);
        final List<View> views = new ArrayList<>();
        LayoutInflater layoutInflater = getLayoutInflater();

        View pager1 = layoutInflater.inflate(R.layout.pager_1,null);
        ListView dynamic = (ListView) pager1.findViewById(R.id.index_pager1);
        dynamic.setAdapter(dynamicAdapter);

        View pager2 = layoutInflater.inflate(R.layout.pager_2,null);

        ListView friend = (ListView) pager2.findViewById(R.id.index_pager2);
        friend.setAdapter(friendAdapter);
        friend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                FriendListView friendListView = mFriendListViews.get(position);
                Toast.makeText(MainActivity.this,position+":"+id+friendListView.getName(),Toast.LENGTH_SHORT).show();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                friendListView.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte [] bitmapByte =baos.toByteArray();
                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                intent.putExtra("avatar", bitmapByte);
                intent.putExtra("name",friendListView.getName());
                startActivity(intent);
            }
        });

        View pager3 = layoutInflater.inflate(R.layout.pager_3,null);
        webView = (WebView) pager3.findViewById(R.id.index_webview);
        webView.getSettings().setJavaScriptEnabled(true);

        //WebView transfer internal and support Http&Https network protocol
        webView.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if( url.startsWith("http:") || url.startsWith("https:") ) {
                    return false;
                }else return true;
            }
        });

        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //获取y轴坐标
                float y = event.getRawY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        DisplayMetrics dm =getResources().getDisplayMetrics();
                        int width = dm.widthPixels;
                        int x = (int)event.getX();
                        if(x>300&&x<width-100){
                            webView.requestDisallowInterceptTouchEvent(true);
                        }else{
                            webView.requestDisallowInterceptTouchEvent(false);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });

        webView.loadUrl("http://wwww.baidu.com");

        views.add(pager1);
        views.add(pager2);
        views.add(pager3);

        PagerAdapter pagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return 3;
            }
            //对超出范围的资源进行销毁
            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                // TODO Auto-generated method stub
                //super.destroyItem(container, position, object);
                container.removeView(views.get(position));
            }
            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(views.get(position));
                return views.get(position);
            }
        };
        pager.setAdapter(pagerAdapter);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setPagerCurrentItem(position);
//                switch (position){
//                    case 0:
//                        dynamicButtonClick();
//                        break;
//                    case 1:
//                        friendButtonClick();
//                        break;
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initFriendsListData(){
        friendsListData = FriendsListData.createFriendsListDate();
        friendsListData.add(decodeSampledBitmapFromResource(getResources(),R.mipmap.img1,100,100),"刘亦菲","1605155603@qq.com");
        friendsListData.add(decodeSampledBitmapFromResource(getResources(),R.mipmap.img2,100,100),"黄晓明","123456789@qq.com");
        friendsListData.add(decodeSampledBitmapFromResource(getResources(),R.mipmap.img3,100,100),"李荣浩","1415120479@qq.com");
        friendsListData.add(decodeSampledBitmapFromResource(getResources(),R.mipmap.img5,100,100),"刘德华","234332286@qq.com");
        friendsListData.add(decodeSampledBitmapFromResource(getResources(),R.mipmap.img6,100,100),"刘洋标","1505155603@qq.com");
        friendsListData.add(decodeSampledBitmapFromResource(getResources(),R.mipmap.img7,100,100),"张国荣","949022342@qq.com");
        friendsListData.add(decodeSampledBitmapFromResource(getResources(),R.mipmap.liul1,100,100),"李小龙","949022342@qq.com");
        friendsListData.add(decodeSampledBitmapFromResource(getResources(),R.mipmap.liulei2,100,100),"徐歌阳","949022342@qq.com");
                ((TextView) findViewById(R.id.friend_temp)).setText(""+friendsListData.length);

    }

    public Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight){
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public abstract class MyOnTouchListener implements View.OnTouchListener {

        public float mDistance;
        private int mTouchShop = 10;//最小滑动距离
        private  float mFirstY;//触摸下去的位置
        private float mCurrentY;//触摸时Y的位置
        private int direction;//判断是否上滑或者下滑的标志
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN://获取按下位置
                    mFirstY = event.getY();
                    Log.i("charlene","ACTION_DOWN:   "+event.getY());
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.i("charlene","ACTION_MOVE:   "+event.getY());
                    mCurrentY = event.getY();
                    if(mCurrentY - mFirstY > mTouchShop){
                        direction = 0;
                        mDistance = mFirstY - mCurrentY;
                    }else  if(mFirstY - mCurrentY >mTouchShop){
                        mDistance = mCurrentY - mFirstY;
                        direction = 1;
                    }
            }

            if(direction == 1 ){
                onUpsideOn();
            }else if(direction ==0){
                onSlideDown();
            }
            return false;
        }

        abstract void onUpsideOn();
        abstract void onSlideDown();
    }

    private ImageView addFriends = null;

    private void onWriteDynamic(){
        Intent intent = new Intent(this, WriteDynameicActivity.class);
        startActivity(intent);
    }


    /*
    * 系统返回按键状态码
    * 0:不做任何操作
    * 1:WebView返回上一个URL
    * */
    private int backStatusCode = 0;


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if(backStatusCode== 1&& webView.canGoBack())
            webView.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }
        return false;
    }

    @Override//返回按钮关闭DrawerLayout
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.index_drawerlayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    /*
    * 实例化视图组件
    * */
    public void setView(){

        friendbutton = (LinearLayout) findViewById(R.id.friend_button);
        dynamicbutton = (LinearLayout) findViewById(R.id.dynamic_button);
        browserbutton = (LinearLayout) findViewById(R.id.browser_button);

        add_btn = (ImageView) findViewById(R.id.index_add);
        addFriends = (ImageView) findViewById(R.id.index_add_friends);
        openDrawerLayout = (ImageView) findViewById(R.id.index_opendrawerlayout);
        writeDynameicBtn = (ImageView) findViewById(R.id.index_writedynamic);

        friendbutton.setOnClickListener(this);
        dynamicbutton.setOnClickListener(this);
        browserbutton.setOnClickListener(this);
        add_btn.setOnClickListener(this);
        addFriends.setOnClickListener(this);
        openDrawerLayout.setOnClickListener(this);
        writeDynameicBtn.setOnClickListener(this);

    }

    /*
    * 给动态listView添加类容
    * */
    private void initDynamic(){
        dynamicListViews.add(new DynamicListView("中国地震台网速报",decodeSampledBitmapFromResource(getResources(),R.mipmap.d1,100,100),"17-12-19 来自 国家地震台网","中国地震台网正式测定：12月19日02时39分在辽宁鞍山市海城市（北纬40.53度，东经123.04度）发生4.4级地震，震源深度10千米","2507",BitmapFactory.decodeResource(getResources(),R.mipmap.d2)));
        dynamicListViews.add(new DynamicListView("马天宇",decodeSampledBitmapFromResource(getResources(),R.mipmap.a1,100,100),"Oct 20·3.00MP·Washington,DC ","感谢你们，感谢所有。愿平安，快乐。爱你们～","8",BitmapFactory.decodeResource(getResources(),R.mipmap.c1)));
        dynamicListViews.add(new DynamicListView("王祖蓝",decodeSampledBitmapFromResource(getResources(),R.mipmap.a2,100,100),"Oct 20·3.00MP·Washington,DC ","2018祝大家都瘦成你们想要的样子而我 希望和图里的人一样瘦","8",BitmapFactory.decodeResource(getResources(),R.mipmap.c2)));
        dynamicListViews.add(new DynamicListView("李易峰",decodeSampledBitmapFromResource(getResources(),R.mipmap.a3,100,100),"Oct 20·3.00MP·Washington,DC ","今天是真我风采的角色 浴袍歌手4.0","8",BitmapFactory.decodeResource(getResources(),R.mipmap.c3)));

        DynamicListView dynamicListView1 = new DynamicListView("刘亦菲",decodeSampledBitmapFromResource(getResources(),R.mipmap.img1,100,100),"Oct 20·3.00MP·Washington,DC ","啦啦啦，啦啦啦，我是卖报的小航家，☀ ☀ ☀","8",BitmapFactory.decodeResource(getResources(),R.mipmap.img5));
        dynamicListViews.add(dynamicListView1);
        DynamicListView dynamicListView2 = new DynamicListView("陈伟霆",decodeSampledBitmapFromResource(getResources(),R.mipmap.img2,100,100),"Oct 20·3.00MP·Washington,DC ","Thank you! Amy Addams Last night was amazing!See you soon","8",BitmapFactory.decodeResource(getResources(),R.mipmap.img1));
        dynamicListViews.add(dynamicListView2);
        DynamicListView dynamicListView3 = new DynamicListView("黄晓明",decodeSampledBitmapFromResource(getResources(),R.mipmap.img3,100,100),"Oct 20·3.00MP·Washington,DC ","Thank you! Amy Addams Last night was amazing!See you soon","8",BitmapFactory.decodeResource(getResources(),R.mipmap.img3));
        dynamicListViews.add(dynamicListView3);
    }


    public void initFriend(){
        initFriendsListData();
        characterParser = CharacterParser.getInstance();
        PinyinComparator pinyinComparator = new PinyinComparator();
        mFriendListViews = filledData();
        Collections.sort(mFriendListViews, pinyinComparator);
    }
    /*
    * 给好友listView添加类容
    * */
    private List<FriendListView> filledData(){
        List<FriendListView> friendListViews = new ArrayList<>();
        for (int i=0;i<friendsListData.length;i++){
            FriendListView friendListView =  new FriendListView(friendsListData.getAvatar(i),friendsListData.getName(i),friendsListData.getEmail(i));
            String pinyin = characterParser.getSelling(friendsListData.getName(i));
            String sortString = pinyin.substring(0, 1).toUpperCase();
            // 正则表达式，判断首字母是否是英文字母
            if(sortString.matches("[A-Z]")){
                friendListView.setSortLetters(sortString.toUpperCase());
            }else{
                friendListView.setSortLetters("#");
            }
            friendListViews.add(friendListView);
        }
        return friendListViews;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.friend_button:
//                friendButtonClick();
//                pager.setCurrentItem(1);
                setPagerCurrentItem(1);
                break;
            case R.id.dynamic_button:
//                //dynamicButtonClick();
//                pager.setCurrentItem(0);
                setPagerCurrentItem(0);
                break;
            case R.id.browser_button:
//                pager.setCurrentItem(2);
                setPagerCurrentItem(2);
                break;

            /*
            * 更多弹窗事件
            * */
            case R.id.index_add:
                break;

            case R.id.index_add_friends:
                DialogFragment fragment = new AddFriendsPicker();
                fragment.show(getSupportFragmentManager(),"AddFriendsPicker");
                break;
            case R.id.index_opendrawerlayout:
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.index_drawerlayout);
                if (!drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.openDrawer(GravityCompat.START);
                }
                break;
            case R.id.index_writedynamic:
                onWriteDynamic();
                break;
        }
    }

    private void setViewColor(TextView textView,View view,boolean b){
        if(b){
            textView.setTextColor(Color.rgb(33,150,247));
            view.setBackgroundColor(Color.rgb(33,150,247));
        }else {
            textView.setTextColor(Color.rgb(181,194,197));
            view.setBackgroundColor(Color.WHITE);
        }
    }

    private void setPagerCurrentItem(int position){
        pager.setCurrentItem(position);
        ImageView dImageView = (ImageView) findViewById(R.id.dynamic_image);
        View dView = findViewById(R.id.dynamic_line);
        TextView dTextView = (TextView) findViewById(R.id.dynamic_temp);

        ImageView fImageView = (ImageView) findViewById(R.id.friend_image);
        View fView = findViewById(R.id.friend_line);
        TextView fTextView = (TextView) findViewById(R.id.friend_temp);

        ImageView bImageView = (ImageView) findViewById(R.id.browser_image);
        View bView = findViewById(R.id.browser_line);
        TextView bTextView = (TextView) findViewById(R.id.browser_temp);

        switch (pager.getCurrentItem()){
            case 0:
                dImageView.setImageResource(R.mipmap.m1);
                setViewColor(dTextView,dView,true);

                fImageView.setImageResource(R.mipmap.friend);
                setViewColor(fTextView,fView,false);

                bImageView.setImageResource(R.mipmap.llq2);
                setViewColor(bTextView,bView,false);

                backStatusCode = 0;
                break;
            case 1:
                dImageView.setImageResource(R.mipmap.m);
                setViewColor(dTextView,dView,false);
                fImageView.setImageResource(R.mipmap.friend2);
                setViewColor(fTextView,fView,true);
                bImageView.setImageResource(R.mipmap.llq2);
                setViewColor(bTextView,bView,false);
                backStatusCode = 0;
                break;
            case 2:
                dImageView.setImageResource(R.mipmap.m);
                setViewColor(dTextView,dView,false);
                fImageView.setImageResource(R.mipmap.friend);
                setViewColor(fTextView,fView,false);
                bImageView.setImageResource(R.mipmap.llq1);
                setViewColor(bTextView,bView,true);
                backStatusCode = 1;
                break;
        }
    }

    class PinyinComparator implements Comparator<FriendListView> {

        @Override
        public int compare(FriendListView o1, FriendListView o2) {
            //这里主要是用来对ListView里面的数据根据ABCDEFG...来排序
            if (o2.getSortLetters().equals("#")) {
                return -1;
            } else if (o1.getSortLetters().equals("#")) {
                return 1;
            } else {
                return o1.getSortLetters().compareTo(o2.getSortLetters());
            }
        }
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the activity_camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            this.finish();
            this.onDestroy();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.index_drawerlayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
