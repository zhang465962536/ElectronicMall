package com.example.my_core.ui.camera;

import android.app.AlertDialog;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.FileProvider;

import com.blankj.utilcode.util.FileUtils;
import com.example.my_core.R;
import com.example.my_core.delegates.PermissionCheckerDelegate;
import com.example.my_core.util.file.FileUtil;

import java.io.File;

//照片处理类 处理图片的核心类
public class CameraHandler implements View.OnClickListener {

    private final AlertDialog DIALOG;
    private final PermissionCheckerDelegate DELEGATE;

    public CameraHandler( PermissionCheckerDelegate delegate) {
        this.DELEGATE = delegate;
        DIALOG = new AlertDialog.Builder(delegate.getContext()).create();
    }

    //从上向下弹出 panel 选择事件 从 相机 或者 图库 选择图片
    final void beginCameraDialog(){
        DIALOG.show();
        final Window window = DIALOG.getWindow();
        if (window != null) {
            window.setContentView(R.layout.dialog_camera_panel);
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.anim_panel_up_from_bottom);  //从下向上的动画
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //设置属性
            final WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;  //全屏
            params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            params.dimAmount = 0.5f; //半值
            window.setAttributes(params);

            window.findViewById(R.id.photodialog_btn_take).setOnClickListener(this);
            window.findViewById(R.id.photodialog_btn_native).setOnClickListener(this);
            window.findViewById(R.id.photodialog_btn_cancel).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.photodialog_btn_native) {
            pickPhoto();
            DIALOG.cancel();
        } else if (id == R.id.photodialog_btn_take) {
            takePhoto();
            DIALOG.cancel();
        } else if (id == R.id.photodialog_btn_cancel) {
            DIALOG.cancel();
        }
    }

    //返回选择的图片名字
    private String getPhotoName(){
        return FileUtil.getFileNameByTime("IMG","jpg");
    }

    //拍照
    private void takePhoto(){
        final String currentPhotoName = getPhotoName();
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //创建一个临时文件
        final File tempFile = new File(FileUtil.CAMERA_PHOTO_DIR,currentPhotoName);

        //Android7.0以上  强制使用 FileProvider 进行应用外文件的访问  但是可以使用 ContentProvider 进行额外处理
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){    //如果手机版本是Android 7.0以上
            final ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.Images.Media.DATA,tempFile.getPath());
            final Uri uri = DELEGATE.getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
            //需要将uri路径转为实际路径  ContentProvider 获取的目录带有 content// 需要将它去除 获取真实的路径
            final File realFile = FileUtils.getFileByPath(FileUtil.getRealFilePath(DELEGATE.getContext(),uri));
            final Uri realUri = Uri.fromFile(realFile);
            CameraImageBean.getInstance().setPath(realUri);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        }else {  //Android 7.0 以下调用相机
            final Uri fileUri = Uri.fromFile(tempFile);
            CameraImageBean.getInstance().setPath(fileUri);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);
        }
        DELEGATE.startActivityForResult(intent,RequestCodes.TAKE_PHOTO);
    }

    //从图库选择图片
    private void pickPhoto(){
        final Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        DELEGATE.startActivityForResult(Intent.createChooser(intent,"选择获取图片的方式"),RequestCodes.PICK_PHOTO);
    }
}
