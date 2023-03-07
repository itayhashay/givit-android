package com.example.fragmenttest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fragmenttest.model.Item;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

class ItemViewHolder extends RecyclerView.ViewHolder {
    TextView nameTv;
    TextView descriptionTv;
    ImageView imageIv;
    List<Item> itemList;
    public ItemViewHolder(@NonNull View itemView, ItemRecyclerAdapter.OnItemClickListener listener, List<Item> itemList) {
        super(itemView);
        this.itemList = itemList;
        nameTv = itemView.findViewById(R.id.itemlistrow_name_tv);
        descriptionTv = itemView.findViewById(R.id.itemlistrow_description_tv);
        imageIv = itemView.findViewById(R.id.itemlistrow_img);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                listener.onItemClick(position);
            }
        });
    }

    public void bind(Item item, int position) {
        if(item.getImageUrl()!=null)  {
            Picasso.get().load(item.getImageUrl()).placeholder(R.drawable.item).into(imageIv);
        }else {
            imageIv.setImageResource(R.drawable.item);
        }
        nameTv.setText(item.name);
        descriptionTv.setText(item.description);
    }
}

public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemViewHolder> {
    OnItemClickListener listener;

    public static interface OnItemClickListener {
        void onItemClick(int position);
    }

    LayoutInflater inflater;
    List<Item> itemList;

    public void setItemList(List<Item> data) {
        if(data == null) {
            data = new LinkedList<>();
        }
        this.itemList = data;
        notifyDataSetChanged();
    }

    public ItemRecyclerAdapter(LayoutInflater inflater, List<Item> itemList) {
        this.inflater = inflater;
        this.itemList = itemList;
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate((R.layout.item_list_row), parent,false);
        return new ItemViewHolder(view,listener, itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.bind(item,position);
    }

    @Override
    public int getItemCount() {
        if(itemList == null) return 0;
        return itemList.size();
    }

}

