package multyunits.wochu.com.multyunits.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import multyunits.wochu.com.multyunits.R;
import multyunits.wochu.com.multyunits.ui.BindZboxAct;
import multyunits.wochu.com.multyunits.ui.BoxLocationBindAct;
import multyunits.wochu.com.multyunits.ui.CheckFboxAct;
import multyunits.wochu.com.multyunits.ui.OrderDetailInfoAct;


/**
 * Created by Administrator on 2016/2/24.
 */
public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.MyViewHolder> {
    public static final int TYPE_HEAD = 0;
    public static final int TYPE_CONTENT = 1;
    public List<Integer> checkedList = new ArrayList<Integer>();
    private List<String> data;
    private Activity mActivity;
    boolean[] slected;

    public SimpleAdapter(Activity mActivity, List<String> data) {
        this.mActivity = mActivity;
        this.data = data;
        slected = new boolean[data.size()];
    }

    Class[] acts = new Class[]{BindZboxAct.class, BoxLocationBindAct.class, CheckFboxAct.class, OrderDetailInfoAct.class};

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mActivity, R.layout.item_functions, null);


        MyViewHolder myViewHolder = new MyViewHolder(view);
        myViewHolder.textView = (TextView) view.findViewById(R.id.description);
//        myViewHolder.checkBox = (CheckBox)view.findViewById(R.id.checkBox);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
//            holder.textView.setText("数据"+position);
        holder.textView.setText(data.get(position));
        holder.setOnItemClickListenner(new MyViewHolder.onItemClickListenner() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent();
                intent.setClass(mActivity, acts[position]);
                mActivity.startActivity(intent);
            }
        });
//        holder.checkBox.setTag(new Integer(position));
//        holder.checkBox.setChecked(slected[position]);
//        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                slected[position] = isChecked;
//            }
//        });
    }

    @Override
    public int getItemCount() {

        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHead(position)) {
            return TYPE_HEAD;
        } else {
            return TYPE_CONTENT;
        }
    }

    private boolean isHead(int position) {
        return position == 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;

        //        CheckBox checkBox;
        @Override
        public void onClick(View v) {

            if (listenner != null) {
                listenner.onItemClick(v, getAdapterPosition());
            }
        }

        public MyViewHolder(View itemView) {

            super(itemView);
            itemView.setOnClickListener(this);
        }

        private onItemClickListenner listenner;

        public void setOnItemClickListenner(onItemClickListenner listenner) {
            this.listenner = listenner;
        }

        public interface onItemClickListenner {
            public void onItemClick(View v, int position);
        }
    }
}
