package com.example.my.ec.main.personal.address;

import android.view.View;
import android.widget.TextView;

import com.example.my.ec.R;
import com.example.my_core.net.RestClient;
import com.example.my_core.net.callback.ISuccess;
import com.example.my_core.ui.recycler.MultipleFields;
import com.example.my_core.ui.recycler.MultipleItemEntity;
import com.example.my_core.ui.recycler.MultipleRecyclerAdapter;
import com.example.my_core.ui.recycler.MultipleViewHolder;

import java.util.List;

public class AddressAdapter extends MultipleRecyclerAdapter {

    protected AddressAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(AddressItemType.ITEM_ADDRESS, R.layout.item_address);
    }

    @Override
    protected void convert(final MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()) {
            case AddressItemType.ITEM_ADDRESS:
                final String name = entity.getField(MultipleFields.NAME);
                final String phone = entity.getField(AddressItemFields.PHONE);
                final String address = entity.getField(AddressItemFields.ADDRESS);
                final boolean isDefault = entity.getField(MultipleFields.TAG);
                final int id = entity.getField(MultipleFields.ID);

                final TextView nameText = holder.getView(R.id.tv_address_name);
                final TextView phoneText = holder.getView(R.id.tv_address_phone);
                final TextView addressText = holder.getView(R.id.tv_address_address);
                final TextView deleteTextView = holder.getView(R.id.tv_address_delete);
                deleteTextView.setOnClickListener(new View.OnClickListener() {
                    //请求服务器删除地址
                    @Override
                    public void onClick(View v) {
                        remove(holder.getLayoutPosition());
                    }
                });

                nameText.setText(name);
                phoneText.setText(phone);
                addressText.setText(address);
                break;
            default:
                break;
        }
    }

    private void haveNet(){
      /*  RestClient.builder()
                .url("address.php")
                .params("id", id)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        remove(holder.getLayoutPosition());
                    }
                })
                .build()
                .post();*/
    }
}
