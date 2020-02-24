package com.example.my_core.delegates;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.my_core.ui.camera.CameraImageBean;
import com.example.my_core.ui.camera.LatteCamera;
import com.example.my_core.ui.camera.RequestCodes;
import com.example.my_core.ui.scanner.ScannerDelegate;
import com.example.my_core.util.callback.CallBackManager;
import com.example.my_core.util.callback.CallBackType;
import com.example.my_core.util.callback.IGlobalCallBack;
import com.example.my_core.util.log.ToastUtil;
import com.yalantis.ucrop.UCrop;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

//引入中间层 用于权限的判断 基于Android6.0以后的动态权限判断
@RuntimePermissions
public abstract class PermissionCheckerDelegate extends BaseDelegate {

    //不是直接调用方法，仅仅是生成代码调用
    @NeedsPermission(Manifest.permission.CAMERA)
    void startCamera() {
        LatteCamera.start(this);
    }

    //这是真正调用的方法
    @NeedsPermission(Manifest.permission.CAMERA)
    public void startCameraWithCheck() {
        PermissionCheckerDelegatePermissionsDispatcher.startCameraWithPermissionCheck(this);
    }

    //扫描二维码(不直接调用)
    @NeedsPermission(Manifest.permission.CAMERA)
    void startScan(BaseDelegate delegate){
        delegate.getSupportDelegate().startChildForResult(new ScannerDelegate(),RequestCodes.SCAN);
    }

    public void startScanWithCheck(BaseDelegate delegate){
        PermissionCheckerDelegatePermissionsDispatcher.startScanWithPermissionCheck(this,delegate);
    }


    @OnPermissionDenied(Manifest.permission.CAMERA)
    void onCameraDenied() {
      ToastUtil.QuickToast("不允许拍照");
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void onCameraNever() {
        ToastUtil.QuickToast("永久拒绝权限");
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    void onCameraRationale(PermissionRequest request) {
        showRationaleDialog(request);
    }

    private void showRationaleDialog(final PermissionRequest request) {
        new AlertDialog.Builder(getContext())
                .setPositiveButton("同意使用", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("拒绝使用", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage("权限管理")
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionCheckerDelegatePermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RequestCodes.TAKE_PHOTO:
                    final Uri resultUri = CameraImageBean.getInstance().getPath();
                    UCrop.of(resultUri, resultUri)
                            .withMaxResultSize(400, 400)
                            .start(getContext(), this);
                    break;
                case RequestCodes.PICK_PHOTO:
                    if (data != null) {
                        final Uri pickPath = data.getData();
                        //从相册选择后需要有个路径存放剪裁过的图片
                        final String pickCropPath = LatteCamera.createCropFile().getPath();
                        UCrop.of(pickPath, Uri.parse(pickCropPath))
                                .withMaxResultSize(400, 400)
                                .start(getContext(), this);
                    }
                    break;
                case RequestCodes.CROP_PHOTO:
                    final Uri cropUri = UCrop.getOutput(data);
                    //拿到剪裁后的数据进行处理
                    @SuppressWarnings("unchecked")
                    final IGlobalCallBack<Uri> callback = CallBackManager
                            .getInstance()
                            .getCallback(CallBackType.ON_CROP);
                    if (callback != null) {
                        callback.executeCallBack(cropUri);
                    }
                    break;
                case RequestCodes.CROP_ERROR:
                    ToastUtil.QuickToast("剪裁出错");
                    break;
                default:
                    break;
            }
        }
    }

}
