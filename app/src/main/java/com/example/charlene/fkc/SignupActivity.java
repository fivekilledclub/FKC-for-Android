package com.example.charlene.fkc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.charlene.fkc.Utils.Utils;
import com.example.charlene.fkc.network.RegisteredClient;
import com.example.charlene.fkc.pickers.CityPicker;
import com.example.charlene.fkc.pickers.CountryPicker;
import com.example.charlene.fkc.pickers.DatePickerFragment;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Charlene on 2017/10/18.
 * 作者：刘洋标
 * QQ：1605155603
 * QQ公众号：五杀俱乐部
 * 邮箱：1605155603@qq.com
 * 微信：ruimengshen
 * 新浪微博：http://weibo.com/u/5686401277
 * Android API：https://developer.android.google.cn/guide/index.html
 */

public class SignupActivity extends FragmentActivity implements View.OnClickListener{

    ViewPager pager = null;

    //头像上传
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    protected static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;


    ArrayList<String> arr = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        pager = (ViewPager) findViewById(R.id.signup_ViewPager);
        setViewPager();
    }

    //给ViewPager适配
    public void setViewPager(){
        final List<View> views = new  ArrayList<>();

        views.add(createPagerView(R.layout.activity_signup1,new int[]{R.id.signup1_next,R.id.signup1_gvc}));
        views.add(createPagerView(R.layout.activity_signup2,new int[]{R.id.signup2_next,R.id.signup2_back,R.id.signup2_male,R.id.signup2_female,R.id.signup2_birthday,R.id.signup2_avatar}));
        views.add(createPagerView(R.layout.activity_signup3,new int[]{R.id.signup3_next,R.id.signup3_back,R.id.signup3_country,R.id.signup3_city}));
        PagerAdapter adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                // TODO Auto-generated method stub
                //super.destroyItem(container, position, object);
                container.removeView(views.get(position));
            }
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(views.get(position));
                return views.get(position);
            }
        };
        pager.setAdapter(adapter);

        /*
        * ViewPager滑动事件：
        * 1.改变底部导航条颜色（已完成）
        * 1.判断当前Edit类容和需要选择的Button是否合法（未完成）
        * */
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //改变底部导航条颜色
                changeDownNavigationColor(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /*
    * 改变底部导航条颜色
    * @position:ViewPager当前CurrentItem;
    * */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void changeDownNavigationColor(int position){

        View line1 = findViewById(R.id.signup_Down_Navigation1);
        View line2 = findViewById(R.id.signup_Down_Navigation2);
        View line3 = findViewById(R.id.signup_Down_Navigation3);
        switch (position){
            case 0:
                line1.setBackground(getDrawable(R.drawable.button_fillet_yellow));
                line2.setBackground(getDrawable(R.drawable.button_fillet_blue));
                line3.setBackground(getDrawable(R.drawable.button_fillet_blue));
                break;
            case 1:
                line1.setBackground(getDrawable(R.drawable.button_fillet_white));
                line2.setBackground(getDrawable(R.drawable.button_fillet_yellow));
                line3.setBackground(getDrawable(R.drawable.button_fillet_blue));
                break;
            case 2:
                line1.setBackground(getDrawable(R.drawable.button_fillet_white));
                line2.setBackground(getDrawable(R.drawable.button_fillet_white));
                line3.setBackground(getDrawable(R.drawable.button_fillet_yellow));
                break;
        }
    }


    private EditText email = null;//电子邮件EditText
    private EditText password = null;//密码EditText
    private EditText verificationCode = null;//验证码EditText
    private EditText username = null;//用户名
    private EditText birthday = null;//生日
    private EditText country = null;//国家
    private EditText city = null;//城市
    private ImageView avatarImage = null;//头像

    Boolean gender = true;
    /*
    * 1.创建一个用于Pager加载的视图
    * 2.为Button控件绑定Click事件
    * 3.获取实例化EditText
    *
    * @LayoutId:视图ID
    * @viewId:控件ID
    * */
    public View createPagerView(int LayoutId,int[] viewId){
         View view =  getLayoutInflater().inflate(LayoutId,null);
        for(int i = 0;i<viewId.length;i++) {
            view.findViewById(viewId[i]).setOnClickListener(this);
        }
        switch (LayoutId){
            case R.layout.activity_signup1:
                email = (EditText) view.findViewById(R.id.signup1_email);
                password = (EditText) view.findViewById(R.id.signup1_password);
                verificationCode = (EditText) view.findViewById(R.id.signup1_vc);
                break;
            case R.layout.activity_signup2:
                username = (EditText) view.findViewById(R.id.signup2_username);
                birthday = (EditText) view.findViewById(R.id.signup2_birthday);
                avatarImage = (ImageView) findViewById(R.id.signup2_avatar);
                break;
            case R.layout.activity_signup3:
                country = (EditText) view.findViewById(R.id.signup3_country);
                city = (EditText) view.findViewById(R.id.signup3_city);
                break;
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signup1_next:
                Toast.makeText(this,email.getText()+":"+password.getText()+":"+verificationCode.getText(),Toast.LENGTH_SHORT).show();
                pager.setCurrentItem(pager.getCurrentItem()+1);
                break;
            case R.id.signup2_next:
                Toast.makeText(this,username.getText(),Toast.LENGTH_SHORT).show();
                pager.setCurrentItem(pager.getCurrentItem()+1);
                break;
            case R.id.signup3_next:
                arr.add("1605155603");
                arr.add("1836102230");
                new Thread(){
                    public void run(){
                        RegisteredClient registered = new RegisteredClient("192.168.43.178",1998);
                        try {
                            registered.connectServer();
                        } catch (IOException e) {
                            Toast.makeText(SignupActivity.this,"连接服务器失败",Toast.LENGTH_SHORT).show();
                        }
                        try {
                            Toast.makeText(SignupActivity.this,registered.run(arr),Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            registered.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

                break;
            case R.id.signup2_back:
                pager.setCurrentItem(pager.getCurrentItem()-1);
                break;
            case R.id.signup3_back:
                pager.setCurrentItem(pager.getCurrentItem()-1);
                break;
            case R.id.signup1_gvc:
                Toast.makeText(this,"获取验证码",Toast.LENGTH_SHORT).show();
                break;
            //性别选择
            case R.id.signup2_male:
                setMale();
                break;
            case R.id.signup2_female:
                setFemale();
                break;
            case R.id.signup2_birthday:
                //创建时间选择器对象
                DialogFragment newFragment = new DatePickerFragment(birthday);
                newFragment.show(getSupportFragmentManager(),"datePicker");
                break;
            case R.id.signup2_avatar:
                ActivityCompat.requestPermissions(this,new String[] {"android.permission.WRITE_EXTERNAL_STORAGE"},222);
                showChoosePicDialog();
                break;
            case R.id.signup3_country:
                DialogFragment fragment = new CountryPicker(country);
                fragment.show(getSupportFragmentManager(),"CountryPicker");
                break;
            case R.id.signup3_city:
                DialogFragment fragmentCity = new CityPicker(country.getText().toString(),city);
                fragmentCity.show(getSupportFragmentManager(),"CountryPicker");
                break;
        }
    }
    public void setMale(){
        gender = true;
        findViewById(R.id.signup2_male).setBackgroundResource(R.drawable.button_fillet_white);
        ((Button)findViewById(R.id.signup2_male)).setTextColor(Color.rgb(3,150,255));
        findViewById(R.id.signup2_female).setBackgroundResource(R.drawable.button_fillet_blue);
        ((Button)findViewById(R.id.signup2_female)).setTextColor(Color.WHITE);
    }
    public void setFemale(){
        gender = false;
        findViewById(R.id.signup2_female).setBackgroundResource(R.drawable.button_fillet_white);
        ((Button)findViewById(R.id.signup2_female)).setTextColor(Color.rgb(60,170,255));
        findViewById(R.id.signup2_male).setBackgroundResource(R.drawable.button_fillet_blue);
        ((Button)findViewById(R.id.signup2_male)).setTextColor(Color.WHITE);
    }
    /*
* 显示修改头像的对话框
* */
    protected void showChoosePicDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Set Avatar");
        String [] items = {"Select local photo","Take pictures"};
        builder.setNegativeButton("取消",null);

        builder.setItems(items,new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case CHOOSE_PICTURE://选择本地照片
                        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        startActivityForResult(openAlbumIntent,CHOOSE_PICTURE);
                        break;
                    case TAKE_PICTURE://拍照
                        Intent openCameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        tempUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"image.jpg"));
                        openCameraintent.putExtra(MediaStore.EXTRA_OUTPUT,tempUri);
                        startActivityForResult(openCameraintent,TAKE_PICTURE);
                        break;
                }
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != RESULT_OK){//返回代码是可用的
            switch (requestCode){
                case TAKE_PICTURE://开始对图像进行剪切
                    startPhotoZomm(tempUri);
                    break;
                case CHOOSE_PICTURE:
                    startPhotoZomm(data.getData());
                    break;
                case CROP_SMALL_PICTURE:
                    if(data != null){
                        setImageToView(data);
                    }
                    break;
            }
        }else{
            Toast.makeText(this,"onActivitResult return error!!",Toast.LENGTH_SHORT).show();
        }
    }
    /*
* 剪裁图像方法实现
* */
    protected void startPhotoZomm(Uri uri){
        if(uri == null){
            Toast.makeText(this, "The uri is not exist", Toast.LENGTH_SHORT).show();
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.activity_camera.action.CROP");
        intent.setDataAndType(uri,"image/*");
        //设置裁剪
        intent.putExtra("crop","true");
        //aspect 是宽高的比例
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        //output是裁剪图片的宽高
        intent.putExtra("outputX",150);
        intent.putExtra("outputY",150);
        intent.putExtra("return-data",true);
        startActivityForResult(intent,CROP_SMALL_PICTURE);
    }
    /*
    * 保存裁剪过后的图片数据
    *
    * */
    protected void setImageToView(Intent data){
        Bundle extras = data.getExtras();
        if(extras != null){
            Bitmap photo =  extras.getParcelable("data");
            photo = Utils.toRoundBitmap(photo);
            avatarImage.setImageBitmap(photo);
        }
    }
}
