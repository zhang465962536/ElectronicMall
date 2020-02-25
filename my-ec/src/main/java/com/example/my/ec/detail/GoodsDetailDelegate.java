package com.example.my.ec.detail;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.androidanimations.library.YoYo;
import com.example.my.ec.R;
import com.example.my.ec.R2;
import com.example.my_core.delegates.LatteDelegate;
import com.example.my_core.net.RestClient;
import com.example.my_core.net.callback.ISuccess;
import com.example.my_core.ui.animation.BezierAnimation;
import com.example.my_core.ui.animation.BezierUtil;
import com.example.my_core.ui.banner.HolderCreator;
import com.example.my_core.ui.widget.CircleTextView;
import com.example.my_core.util.file.FileUtil;
import com.example.my_core.util.log.ToastUtil;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class GoodsDetailDelegate extends LatteDelegate implements AppBarLayout.OnOffsetChangedListener, BezierUtil.AnimationListener {

    @BindView(R2.id.goods_detail_toolbar)
    Toolbar mToolbar = null;
    @BindView(R2.id.tab_layout)
    TabLayout mTabLayout = null;
    @BindView(R2.id.view_pager)
    ViewPager mViewPager = null;
    @BindView(R2.id.detail_banner)
    ConvenientBanner<String> mBanner = null;
    @BindView(R2.id.collapsing_toolbar_detail)
    CollapsingToolbarLayout mCollapsingToolbarLayout = null;
    @BindView(R2.id.app_bar_detail)
    AppBarLayout mAppBar = null;

    //底部
    @BindView(R2.id.icon_favor)
    IconTextView mIconFavor = null;
    @BindView(R2.id.tv_shopping_cart_amount)
    CircleTextView mCircleTextView = null;
    @BindView(R2.id.rl_add_shop_cart)
    RelativeLayout mRlAddShopCart = null;
    @BindView(R2.id.icon_shop_cart)
    IconTextView mIconShopCart = null;

    private static final String ARG_GOODS_ID = "ARG_GOODS_ID";
    private int mGoodsId = -1;

    //目标图片Url
    private String mGoodsThumbUrl = null;
    //购物车里面商品数量
    private int mShopCount = 0;

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate()
            .override(100, 100);

    @OnClick(R2.id.rl_add_shop_cart)
    void onClickAddShopCart(){
        final CircleImageView animImg = new CircleImageView(getContext());
        Glide.with(getContext())
                .load(mGoodsThumbUrl)
                .apply(OPTIONS)
                .into(animImg);
        //添加动画
        BezierAnimation.addCart(this, mRlAddShopCart, mIconShopCart, animImg, this);
    }

    private void setShopCartCount(JSONObject data){
        mGoodsThumbUrl = data.getString("thumb");
        if(mShopCount == 0){
            mCircleTextView.setVisibility(View.GONE);
        }
    }

    //传入商品id
    public static GoodsDetailDelegate create(@NonNull int goodsId){
        final Bundle args = new Bundle();
        args.putInt(ARG_GOODS_ID,goodsId);
        final GoodsDetailDelegate delegate = new GoodsDetailDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    //从oncreate  去除 bundle 的 goodsId数值
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if(args!= null){
            mGoodsId = args.getInt(ARG_GOODS_ID);
            ToastUtil.QuickToast("商品Id 为 " + mGoodsId);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_goods_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        //伸缩变化的颜色
        mCollapsingToolbarLayout.setContentScrimColor(Color.WHITE);
        mCircleTextView.setCircleBackground(Color.RED);
        mAppBar.addOnOffsetChangedListener(this);
        initNoNetData();
        initTabLayout();
    }

    private void initNoNetData() {
        String json = FileUtil.getRawFile(R.raw.goods_detail_data);
        final JSONObject data = JSON.parseObject(json).getJSONObject("data");
        initBanner(data);
        initGoodsInfo(data);
        initPager(data);
        setShopCartCount(data);
    }

    //返回数据
    private void initData(){
        RestClient.builder()
                .url("goods_detail.php")
                .params("goods_id",mGoodsId)
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject data = JSON.parseObject(response).getJSONObject("data");
                        initBanner(data);
                        initGoodsInfo(data);
                    }
                })
                .build()
                .get();
    }

    private void initPager(JSONObject data){
        final PagerAdapter adapter =  new TabPagerAdapter(getFragmentManager(),data);
        mViewPager.setAdapter(adapter);
    }

    private void initTabLayout(){
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(),R.color.app_main));
        mTabLayout.setBackgroundColor(Color.WHITE);
        mTabLayout.setTabTextColors(ColorStateList.valueOf(Color.BLACK));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initGoodsInfo(JSONObject data){
        final String goodsData = data.toJSONString();
        getSupportDelegate().loadRootFragment(R.id.frame_goods_info,GoodsInfoDelegate.create(goodsData));
    }

    private void initBanner(JSONObject data){
        final JSONArray array = data.getJSONArray("banners");
        final List<String> images = new ArrayList<>();
        final int size = array.size();
        for(int i = 0;i < size ; i ++){
            images.add(array.getString(i));
        }

        mBanner.setPages(new HolderCreator(),images)
                .setPageIndicator(new int[]{R.drawable.dot_normal,R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setPageTransformer(new DefaultTransformer())
                .startTurning(3000)
                .setCanLoop(true);
    }

    //跳转该页面时候 添加水平动画
    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {

    }

    @Override
    public void onAnimationEnd() {
        YoYo.with(new ScaleUpAnimator())
                .duration(500)
                .playOn(mIconShopCart);
        //动画结束后购物车数量+1
        mShopCount++;
        mCircleTextView.setVisibility(View.VISIBLE);
        mCircleTextView.setText(String.valueOf(mShopCount));
    }

    private void haveNet(){
        RestClient.builder()
                .url("add_shop_cart_count")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        mCircleTextView.setVisibility(View.VISIBLE);
                        mCircleTextView.setText(String.valueOf(mShopCount));
                    }
                })
                .params("count",mShopCount)
                .build()
                .post();
    }
}
