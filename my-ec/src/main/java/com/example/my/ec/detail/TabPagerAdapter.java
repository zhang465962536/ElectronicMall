package com.example.my.ec.detail;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

//FragmentStatePagerAdapter 和  PagerAdapter 有本质的区别 他并不会保留Page的状态 当某页面销毁之后 该页面数据 也会销毁
//如果数据得不到及时的销毁 如果在用户快速的操作 数据会出现重复 或者 得不到更新
public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private final ArrayList<String> TAB_TITLES = new ArrayList<>();
    private final ArrayList<ArrayList<String>> PICTURES = new ArrayList<>();

    public TabPagerAdapter(@NonNull FragmentManager fm, JSONObject data) {
        super(fm);
        //获取tabs信息
        final JSONArray tabs = data.getJSONArray("tabs");
        final int size = tabs.size();
        for(int i = 0;i < size;i ++){
            final JSONObject eachTab = tabs.getJSONObject(i);
            final String name = eachTab.getString("name");
            final JSONArray pictureUrls = eachTab.getJSONArray("pictures");
            final ArrayList<String> eachTabPicturesArray = new ArrayList<>();
            //存储每个图片
            final int pictureSize = pictureUrls.size();
            for(int j = 0;j<pictureSize;j ++){
                eachTabPicturesArray.add(pictureUrls.getString(j));
            }

            TAB_TITLES.add(name);
            PICTURES.add(eachTabPicturesArray);
        }
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            //商品详情
            return ImageDelegate.create(PICTURES.get(0));
        }else if(position == 1){
            //规格参数
            return ImageDelegate.create(PICTURES.get(1));
        }
        return null;
    }

    //返回页数
    @Override
    public int getCount() {
        return TAB_TITLES.size();
    }

    //返回Type 的文字
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES.get(position);
    }
}
