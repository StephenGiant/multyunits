package multyunits.wochu.com.multyunits.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import multyunits.wochu.com.multyunits.R;
import multyunits.wochu.com.multyunits.bean.OrderFboxEntity;

public class PostFboxCacheAdapter extends Adapter<PostFboxCacheAdapter.SimpleViewholder> {

    private ArrayList<OrderFboxEntity> mData;
    private Context mContext;

    public PostFboxCacheAdapter(Context context, ArrayList<OrderFboxEntity> data) {
        mData = data;
        mContext = context;
    }

    @Override
    public SimpleViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_postentity, null);
        SimpleViewholder viewholder = new SimpleViewholder(view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(SimpleViewholder holder, int position) {
        holder.tv_fbox.setText(mData.get(position).Fbox);
        holder.tv_order.setText(mData.get(position).OrderNum);

    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return mData.size();
    }

    public void refreshData(ArrayList<OrderFboxEntity> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public static class SimpleViewholder extends ViewHolder {

        public TextView tv_fbox;
        public TextView tv_order;

        public SimpleViewholder(View itemView) {
            super(itemView);
            tv_fbox = (TextView) itemView.findViewById(R.id.fboxCode);
            tv_order = (TextView) itemView.findViewById(R.id.tv_orderCode);

        }

    }
}
