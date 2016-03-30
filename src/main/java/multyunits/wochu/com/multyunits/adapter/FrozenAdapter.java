package multyunits.wochu.com.multyunits.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import multyunits.wochu.com.multyunits.R;
import multyunits.wochu.com.multyunits.bean.FrozenBean;
import multyunits.wochu.com.multyunits.bean.FrozenBean.FrozenDetail;

public class FrozenAdapter extends Adapter<FrozenAdapter.FrozenViewHolder> {
    private List<FrozenBean.FrozenDetail> data;
    private Context context;

    public FrozenAdapter(List<FrozenBean.FrozenDetail> dataList, Context context) {
        data = dataList;
        this.context = context;
    }

    @Override
    public FrozenViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_frozendetail, null);
        FrozenViewHolder viewHolder = new FrozenViewHolder(view);
        viewHolder.ItemCode = (TextView) view.findViewById(R.id.item_code);
        viewHolder.ItemName = (TextView) view.findViewById(R.id.item_name);
        viewHolder.ItemLocation = (TextView) view.findViewById(R.id.item_QTY);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FrozenViewHolder holder, int position) {
        holder.ItemCode.setText(data.get(position).GOODSCODE);
        holder.ItemName.setText(data.get(position).GOODSNAME);
        holder.ItemLocation.setText(String.valueOf(data.get(position).QTY));

    }

    @Override
    public int getItemCount() {

        return data.size();
    }


    public void refreshData(List<FrozenDetail> dataList) {
        this.data = dataList;
        notifyDataSetChanged();
    }

    public static class FrozenViewHolder extends ViewHolder {
        public TextView ItemName;
        public TextView ItemCode;
        public TextView ItemLocation;

        public FrozenViewHolder(View itemView) {
            super(itemView);

        }

    }
}
