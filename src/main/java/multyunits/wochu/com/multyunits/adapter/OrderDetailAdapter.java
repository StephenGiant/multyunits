package multyunits.wochu.com.multyunits.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import multyunits.wochu.com.multyunits.R;
import multyunits.wochu.com.multyunits.ui.OrderDetailInfoAct.BeanForDetail;

public class OrderDetailAdapter extends Adapter<OrderDetailAdapter.OrderDetailViewHolder> {
    private ArrayList<BeanForDetail> data;
    private Context context;

    public OrderDetailAdapter(ArrayList<BeanForDetail> data, Context context) {
        super();
        this.context = context;
        this.data = data;

    }

    @Override
    public OrderDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_goodsinfo, null);

        return new OrderDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderDetailViewHolder holder, int position) {
        holder.tv_goodsCode.setText(data.get(position).GoodsCode);
        holder.tv_goodsName.setText(data.get(position).GoodsName);
        holder.tv_QTY.setText(String.valueOf(data.get(position).qty));

    }

    public void refresh(ArrayList<BeanForDetail> data) {
        this.data = data;
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    public static class OrderDetailViewHolder extends ViewHolder {
        public TextView tv_goodsName;
        public TextView tv_goodsCode;
        public TextView tv_QTY;

        public OrderDetailViewHolder(View itemView) {
            super(itemView);
            this.tv_goodsCode = (TextView) itemView.findViewById(R.id.tv_goodsCode);
            this.tv_goodsName = (TextView) itemView.findViewById(R.id.tv_goodsName);
            this.tv_QTY = (TextView) itemView.findViewById(R.id.tv_num);

        }

    }


}
