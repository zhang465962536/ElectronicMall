package com.example.my.ec.main.cart;

//计算Item总价格
//如果在外部点击全选 需要传Item的总价 就需要使用接口形式返回数据
public interface ICartItemListener {
    void onItemClick(double itemTotalPrice);
}
