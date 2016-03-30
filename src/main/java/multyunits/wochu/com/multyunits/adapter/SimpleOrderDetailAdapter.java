package multyunits.wochu.com.multyunits.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import multyunits.wochu.com.multyunits.R;
import multyunits.wochu.com.multyunits.bean.CheckOrderInfo;

public class SimpleOrderDetailAdapter extends Adapter<SimpleOrderDetailAdapter.GoodsDetaiViewHolder> {
    private Context mContext;
    private List<CheckOrderInfo.CheckItemDetail> mData;

    public SimpleOrderDetailAdapter(Context context, List<CheckOrderInfo.CheckItemDetail> dataList) {
        mContext = context;
        mData = dataList;
    }

    @Override
    public GoodsDetaiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_goodsinfo, null);
        GoodsDetaiViewHolder goodsDetaiViewHolder = new GoodsDetaiViewHolder(view);
        goodsDetaiViewHolder.tv_goodsCode = (TextView) view.findViewById(R.id.tv_goodsCode);
        goodsDetaiViewHolder.tv_goodsName = (TextView) view.findViewById(R.id.tv_goodsName);
        goodsDetaiViewHolder.tv_goodsCount = (TextView) view.findViewById(R.id.tv_num);
        return goodsDetaiViewHolder;
    }

    @Override
    public void onBindViewHolder(GoodsDetaiViewHolder holder, int position) {
        holder.tv_goodsCode.setText(mData.get(position).ITEMCODE);
        holder.tv_goodsName.setText(mData.get(position).ITEMNAME);
        holder.tv_goodsCount.setText("" + mData.get(position).QTY);

    }

    public void refreshData(List<CheckOrderInfo.CheckItemDetail> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return mData.size();
    }

    public static class GoodsDetaiViewHolder extends ViewHolder {
        public TextView tv_goodsName;
        public TextView tv_goodsCode;
        public TextView tv_goodsCount;

        public GoodsDetaiViewHolder(View itemView) {
            super(itemView);


        }

    }
}
