package multyunits.wochu.com.multyunits.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import multyunits.wochu.com.multyunits.R;
import multyunits.wochu.com.multyunits.bean.SimpleOrderBean;
import multyunits.wochu.com.multyunits.viewholder.MySimpleViewHolder;

public class SimpleOrderAdapter extends Adapter<MySimpleViewHolder> {
    private Activity mActivity;
    private ArrayList<SimpleOrderBean> data;

    public SimpleOrderAdapter(Activity mActivity, ArrayList<SimpleOrderBean> data) {
        this.data = data;
        this.mActivity = mActivity;
    }

    @Override
    public MySimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mActivity, R.layout.simpleorderitem, null);
        MySimpleViewHolder mySimpleViewHolder = new MySimpleViewHolder(view);
        mySimpleViewHolder.tv_orderNum = (TextView) view.findViewById(R.id.tv_orderCode);
        mySimpleViewHolder.tv_Boci = (TextView) view.findViewById(R.id.bocihao);
//	mySimpleViewHolder.tv_QTY = (TextView) view.findViewById(R.id.tv_itemQTY);


        return mySimpleViewHolder;
    }

    @Override
    public void onBindViewHolder(MySimpleViewHolder holder, int position) {
        holder.tv_orderNum.setText(data.get(position).orderNum);
        holder.tv_Boci.setText(data.get(position).Boci);
//	holder.tv_QTY.setText(data.get(position).QTY+"");

    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    public void refresh(ArrayList<SimpleOrderBean> newaData) {
        data = newaData;
        this.notifyDataSetChanged();
    }

}
