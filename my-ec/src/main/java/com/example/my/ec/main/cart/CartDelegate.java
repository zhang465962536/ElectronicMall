package com.example.my.ec.main.cart;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my.ec.R;
import com.example.my.ec.R2;
import com.example.my.ec.main.index.IndexDelegate;
import com.example.my_core.delegates.bottom.BottomItemDelegate;
import com.example.my_core.net.RestClient;
import com.example.my_core.net.callback.ISuccess;
import com.example.my_core.ui.recycler.MultipleItemEntity;
import com.example.my_core.util.file.FileUtil;
import com.example.my_core.util.log.ToastUtil;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import butterknife.BindView;
import butterknife.OnClick;

//购物车
public class CartDelegate extends BottomItemDelegate implements ISuccess,ICartItemListener {

    @BindView(R2.id.rv_shop_cart)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.icon_shop_cart_select_all)
    IconTextView mIconSelectAll = null;
    @BindView(R2.id.stub_no_item)
    ViewStub mStubNoItem = null;
    @BindView(R2.id.tv_shop_cart_total_price)
    TextView mTvTotalPrice = null;

    private ShopCartAdapter mAdapter = null;
    private int mCurrentCount = 0; //记录当前选择框的数量
    private int mTotalCount = 0;  //购物车 商品 Item总数
    private double mTotalPrice = 0.00 ;

    @OnClick(R2.id.icon_shop_cart_select_all)
    void onClickSelectAll() {
        //使用 IconTextView 自带的Tag 进行判断
        final int tag = (int) mIconSelectAll.getTag();
        if (tag == 0) {
            mIconSelectAll.setTextColor(ContextCompat.getColor(getContext(), R.color.app_main));
            mIconSelectAll.setTag(1);
            mAdapter.setIsSelectedAll(true);
            //更新RecyclerView 的显示状态
            mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
        } else {
            mIconSelectAll.setTextColor(Color.GRAY);
            mIconSelectAll.setTag(0);
            mAdapter.setIsSelectedAll(false);
            //mAdapter.notifyDataSetChanged(); 一次性更新所有UI 内存消耗较大
            mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount()); //选择更新范围
        }
    }

    @OnClick(R2.id.tv_top_shop_cart_remove_selected)
    void onClickRemoveSelectedItem() {
        final List<MultipleItemEntity> data = mAdapter.getData();
        //要删除的数据
        final List<MultipleItemEntity> deleteEntities = new ArrayList<>();
        for (MultipleItemEntity entity : data) {
            final boolean isSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
            if (isSelected) {
                deleteEntities.add(entity);
            }
        }
        for (MultipleItemEntity entity : deleteEntities) {
            int removePosition;
            final int entityPosition = entity.getField(ShopCartItemFields.POSITION);
            if (entityPosition > mCurrentCount - 1) {
                removePosition = entityPosition - (mTotalCount - mCurrentCount);
            } else {
                removePosition = entityPosition;
            }
            if (removePosition <= mAdapter.getItemCount()) {
                mAdapter.remove(removePosition);
                mCurrentCount = mAdapter.getItemCount();
                //更新数据
                mAdapter.notifyItemRangeChanged(removePosition, mAdapter.getItemCount());
            }
        }
        checkItemCount();

    }

    @OnClick(R2.id.tv_top_shop_cart_clear)
    void onClickClear() {
        mAdapter.getData().clear();
        mAdapter.notifyDataSetChanged();
        checkItemCount();
    }

    @OnClick(R2.id.tv_shop_cart_pay)
    void onClickPay(){

    }


    //创建订单
    private void createOrder(){
        final String orderUrl = "";
        final WeakHashMap<String,Object> orderParams = new WeakHashMap<>();
        orderParams.put("userid",123);
        orderParams.put("amount",0.01);
        orderParams.put("comment","测试支付");
        orderParams.put("type",1);
        orderParams.put("ordertype",0);
        orderParams.put("isanonymous",true);
        orderParams.put("followeduser",0);
        RestClient.builder()
                .url(orderUrl)
                .params(orderParams)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        //进行具体支付
                    }
                })
        .build()
        .post();
    }

    //检查Item数量
    private void checkItemCount(){
        final int count = mAdapter.getItemCount();
        //如果购物车没有添加商品
        if(count == 0){
            final View stubView = mStubNoItem.inflate();
            final TextView tvToBuy = stubView.findViewById(R.id.tv_stub_to_buy);
            tvToBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.QuickToast("您该购物了!");
                    start(new IndexDelegate());
                }
            });
            mRecyclerView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_cart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mIconSelectAll.setTag(0);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        NoNet();
        checkItemCount();
    }

    @Override
    public void onSuccess(String response) {
        final ArrayList<MultipleItemEntity> data = new ShopCartDataConverter().setJsonData(response).convert();
        mAdapter = new ShopCartAdapter(data);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);

    }

    //有网络连接
    private void haveNet() {
        RestClient.builder()
                .url("shop_cart_data.json")
                .loader(getContext())
                .success(this)
                .build();
    }

    //没有网络连接
    private void NoNet() {
        String json = FileUtil.getRawFile(R.raw.shop_cart_data);
        final ArrayList<MultipleItemEntity> data = new ShopCartDataConverter().setJsonData(json).convert();
        mAdapter = new ShopCartAdapter(data);
        mAdapter.setCartItemListener(this);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mTotalPrice  = mAdapter.getTotalPrice();
        mTvTotalPrice.setText(String.valueOf(mTotalPrice));
        checkItemCount();
    }

    @Override
    public void onItemClick(double itemTotalPrice) {
         final double price = mAdapter.getTotalPrice();
        mTvTotalPrice.setText(String.valueOf(price));
    }
}
