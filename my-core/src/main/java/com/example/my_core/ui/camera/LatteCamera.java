package com.example.my_core.ui.camera;

import android.net.Uri;

import com.example.my_core.delegates.PermissionCheckerDelegate;
import com.example.my_core.util.file.FileUtil;

//照相机调用类 工具类
public class LatteCamera {

    //剪裁 需要创建一个地址去存放文件 才能剪裁
    public static Uri createCropFile(){
        return Uri.parse(FileUtil.createFile("crop_image",FileUtil.getFileNameByTime("IMG","jpg")).getPath());
    }

    public static void start(PermissionCheckerDelegate delegate){
        new CameraHandler(delegate).beginCameraDialog();
    }
}
