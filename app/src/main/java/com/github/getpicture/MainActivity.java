package com.github.getpiclatitudeandlongitude;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.yanzhenjie.album.Album;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.panpf.sketch.SketchImageView;
import me.panpf.sketch.request.DisplayOptions;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bt)
    Button bt;
    @BindView(R.id.iv)
    SketchImageView iv;
    @BindView(R.id.tv)
    TextView tv;
    private ArrayList<String> pathList;
    private String picPath;
    private Bitmap bitmap;
    private float output1;
    private float output2;
    int width1;
    int height1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        WindowManager wm1 = this.getWindowManager();
        width1 = wm1.getDefaultDisplay().getWidth();

        height1 = wm1.getDefaultDisplay().getHeight();
        Log.e("Test", "11 width1 == " + width1);
        Log.e("Test", "11 Height == " + height1);

    }

    @OnClick(R.id.bt)
    public void onViewClicked() {
        Album.album(this)
                .selectCount(1) // 最多选择几张图片。
                .camera(true) // 是否有拍照功能。
                .start(999); // 999是请求码，返回时onActivityResult()的第一个参数。
        tv.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 999) {
            if (resultCode == RESULT_OK) { // Successfully.
                // 不要质疑你的眼睛，就是这么简单。
                pathList = Album.parseResult(data);
                picPath = pathList.get(0);
                bitmap = BitmapFactory.decodeFile(picPath);

                try {
                    getPhotoLocation(picPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (resultCode == RESULT_CANCELED) { // User canceled.
                // 用户取消了操作。
            }
        }
    }

    private void getPhotoLocation(String picPath) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();

        /**
         * 最关键在此，把options.inJustDecodeBounds = true;
         * 这里再decodeFile()，返回的bitmap为空，但此时调用options.outHeight时，已经包含了图片的高了
         */
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(picPath, options); // 此时返回的bitmap为null
        /**
         *options.outHeight为原始图片的高
         */
        Log.e("Test", "原始图片 高 == " + options.outHeight);
        Log.e("Test", "原始图片 宽 == " + options.outWidth);

        tv.setText("高度为:" + options.outHeight);
        ViewGroup.LayoutParams layoutParams = iv.getLayoutParams();
            layoutParams.width = width1;
//            Log.e("Test", "xx宽走2"+width1);

        if (options.outHeight > 600) {
            layoutParams.height = options.outHeight;
            Log.e("Test", "xx高走1");
        } else {
            layoutParams.height = width1;
            Log.e("Test", "xx高走2");
        }
//        int digree=0;
//        ExifInterface exif = null;
//        exif = new ExifInterface(picPath);
//        if (exif != null) {
//            // 读取图片中相机方向信息
//            int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
//                    ExifInterface.ORIENTATION_UNDEFINED);
//            // 计算旋转角度
//            Log.e("Test", "xx"+ori);
//            switch (ori) {
//                case ExifInterface.ORIENTATION_ROTATE_90:
//                    digree = 90;
//                    break;
//                case ExifInterface.ORIENTATION_ROTATE_180:
//                    digree = 180;
//                    break;
//                case ExifInterface.ORIENTATION_ROTATE_270:
//                    digree = 270;
//                    break;
//                default:
//                    digree = 0;
//                    break;
//            }
//        }
        if (options.outHeight<options.outWidth) {
            layoutParams.height=width1;
        }
        
//        iv.setRotation(digree);
        iv.setLayoutParams(layoutParams);
        iv.setImageBitmap(this.bitmap);
//4160*3120 横着拍 高*寬

        //3120*4160 竖着着拍 寬*高
//        1440*1080
    }



}
