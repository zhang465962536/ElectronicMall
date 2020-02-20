package com.example.my.ec.main.sort.content;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.my.ec.R;
import com.example.my.ec.R2;
import com.example.my_core.delegates.LatteDelegate;
import com.example.my_core.net.RestClient;
import com.example.my_core.net.callback.ISuccess;
import com.example.my_core.util.file.FileUtil;

import java.util.List;

import butterknife.BindView;

//右边内容fragment
public class ContentDelegate extends LatteDelegate {

    //标志切换的fragment
    private static final String ARG_CONTENT_ID = "CONTENT_ID";
    private int mContentId = -1;
    private List<SectionBean> mData = null;

    @BindView(R2.id.rv_list_content)
    RecyclerView mRecyclerView = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if(args != null){
            //如果有id传入
            mContentId = args.getInt(ARG_CONTENT_ID);
        }
    }

    public static ContentDelegate newInstance(int contentId){
        final Bundle args = new Bundle();
        args.putInt(ARG_CONTENT_ID,contentId);
        final ContentDelegate delegate = new ContentDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_list_content;
    }

    //初始化数据
    private void  initData(){
        String json = FileUtil.getRawFile(R.raw.sort_content_data_1);
        mData = new SectionDataConverter().convert(json);
        final SectionAdapter sectionAdapter = new SectionAdapter(R.layout.item_section_content,R.layout.item_section_header,mData);
        mRecyclerView.setAdapter(sectionAdapter);
    }

    //数据 和 UI绑定
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        //使用瀑布流布局  spanCount 左右能显示Item的数量  StaggeredGridLayoutManager.VERTICAL 垂直形态
        final StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        initData();
    }

    //有网络的情况获取 右侧内容
    private void haveNet(){
        RestClient.builder()
                .url("sort_content_data_1.json?contentId="+ mContentId)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        mData = new SectionDataConverter().convert(response);
                        final SectionAdapter sectionAdapter = new SectionAdapter(R.layout.item_section_content,R.layout.item_section_header,mData);
                        mRecyclerView.setAdapter(sectionAdapter);
                    }
                })
                .build()
                .get();
    }
}
