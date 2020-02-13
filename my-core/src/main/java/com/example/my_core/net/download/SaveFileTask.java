package com.example.my_core.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.my_core.app.Latte;
import com.example.my_core.net.callback.IRequest;
import com.example.my_core.net.callback.ISuccess;
import com.example.my_core.util.file.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.Response;
import okhttp3.ResponseBody;

//异步形式保存下载文件
public class SaveFileTask extends AsyncTask<Object, Void, File> {

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;

    SaveFileTask(IRequest REQUEST, ISuccess SUCCESS) {
        this.REQUEST = REQUEST;
        this.SUCCESS = SUCCESS;
    }

    @Override
    protected File doInBackground(Object... params) {
        String downloadDir = (String) params[0];
        String extension = (String) params[1];
        final ResponseBody body = (ResponseBody) params[2];
        final String name = (String) params[3];
        final InputStream is = body.byteStream();
        if (downloadDir == null || downloadDir.equals("")) { //如果下载路径为空 或者传入空字符串 存入默认路径
            downloadDir = "down_loads";
        }
        if (extension == null || extension.equals("")) {
            extension = "";
        }
        //如果名字为空 创建一个文件名
        if (name == null) {
            return FileUtil.writeToDisk(is, downloadDir, extension.toUpperCase(), extension);
        } else {
            //如果有一个完整的文件名 直接创建文件即可
            return FileUtil.writeToDisk(is, downloadDir, name);
        }
    }

    //执行完异步 回到主线程的操作
    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if(SUCCESS!=null){
            SUCCESS.onSuccess(file.getPath());
        }
        if(REQUEST!= null){
            REQUEST.onRequestEnd();
        }
        autoInstallApk(file);
    }

    //下载完成APK文件之后 自动安装
    private void autoInstallApk(File file){
        if(FileUtil.getExtension(file.getPath()).equals("apk")){
            final Intent install = new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//新创建一个栈 因为安装程序 有可能在后台运行的
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            Latte.getApplicationContext().startActivity(install);
        }
    }
}
