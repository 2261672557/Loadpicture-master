# GetPicTrue-master
根据图片的大小动态加载图片，用于购物平台详情页的动态加载图片
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
 
        if (options.outHeight<options.outWidth) {
            layoutParams.height=width1;
        }

 
        iv.setLayoutParams(layoutParams);
        iv.setImageBitmap(this.bitmap);
 
    }
