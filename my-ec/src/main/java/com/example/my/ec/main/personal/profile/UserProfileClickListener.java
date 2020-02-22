package com.example.my.ec.main.personal.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.example.my.ec.R;
import com.example.my.ec.main.personal.list.ListBean;
import com.example.my_core.delegates.LatteDelegate;
import com.example.my_core.net.RestClient;
import com.example.my_core.net.callback.ISuccess;
import com.example.my_core.ui.camera.RequestCodes;
import com.example.my_core.ui.date.DateDialogUtil;
import com.example.my_core.ui.loader.LatteLoader;
import com.example.my_core.util.callback.CallBackManager;
import com.example.my_core.util.callback.CallBackType;
import com.example.my_core.util.callback.IGlobalCallBack;
import com.example.my_core.util.log.LatteLogger;
import com.example.my_core.util.log.ToastUtil;

public class UserProfileClickListener extends SimpleClickListener {

    private final LatteDelegate DELEGATE;
    private String[] mGenders = new String[]{"男","女","保密"};

    public UserProfileClickListener(LatteDelegate delegate) {
        this.DELEGATE = delegate;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final ListBean bean = (ListBean) baseQuickAdapter.getData().get(position);
        //通过ID 获取 点击的Item条目
        final int id = bean.getId();
        switch (id) {
            case 1:
                //开始照相 或者选择 图片
                CallBackManager.getInstance().addCallback(CallBackType.ON_CROP, new IGlobalCallBack<Uri>() {
                    @Override
                    public void executeCallBack(Uri args) {
                        LatteLogger.d("ON_CROP",args);
                        final ImageView avatar = view.findViewById(R.id.img_arrow_avatar);
                        Glide.with(DELEGATE)
                                .load(args)
                                .into(avatar);

                        //头像上传到服务器
                       /* RestClient.builder()
                                .url("")
                                .loader(DELEGATE.getContext())
                                .file(args.getPath())
                                .success(new ISuccess() {
                                    @Override
                                    public void onSuccess(String response) {
                                        final String path = JSON.parseObject(response).getJSONObject("result").getString("path");

                                        //通知服务器更新信息
                                        RestClient.builder()
                                                .url("")
                                                .params("avatar",path)
                                                .loader(DELEGATE.getContext())
                                                .success(new ISuccess() {
                                                    @Override
                                                    public void onSuccess(String response) {
                                                        //获取更新后的用户信息 然后更新到本地数据库
                                                        //没有本地数据库的app 每次打开APP都要请求API ,获取信息
                                                    }
                                                })
                                                .build()
                                                .post();
                                    }
                                })
                                .build();*/
                    }
                });
                DELEGATE.startCameraWithCheck();
                break;
            case 2:
                //姓名
                final LatteDelegate nameDelegate = bean.getDelegate();
                DELEGATE.getSupportDelegate().start(nameDelegate);
                break;
            case 3:
                //性别
                getGenerDialog(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final TextView textView = view.findViewById(R.id.tv_arrow_value);
                        textView.setText(mGenders[which]);
                        dialog.cancel();
                    }
                });
                break;
            case 4:
                //出生日期
                final DateDialogUtil dateDialogUtil = new DateDialogUtil();
                dateDialogUtil.setDateListener(new DateDialogUtil.IDateListener() {
                    @Override
                    public void onDateChange(String date) {
                        final TextView textView = view.findViewById(R.id.tv_arrow_value);
                        textView.setText(date);
                    }
                });
                dateDialogUtil.showDialog(DELEGATE.getContext());
                break;
            default:
                break;
        }
    }

    private void getGenerDialog(DialogInterface.OnClickListener listener){
        final AlertDialog.Builder builder = new AlertDialog.Builder(DELEGATE.getContext());
        builder.setSingleChoiceItems(mGenders,0,listener);
        builder.show();
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
