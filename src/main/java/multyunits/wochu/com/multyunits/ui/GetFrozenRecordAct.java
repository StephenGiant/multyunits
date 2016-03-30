package multyunits.wochu.com.multyunits.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import multyunits.wochu.com.multyunits.bean.FrozenBean.FrozenDetail;
import multyunits.wochu.com.multyunits.R;
import multyunits.wochu.com.multyunits.adapter.FrozenAdapter;
import multyunits.wochu.com.multyunits.baseact.MyBaseActivity;
import multyunits.wochu.com.multyunits.decorator.MyDivider;
import multyunits.wochu.com.multyunits.utils.LogUtil;

public class GetFrozenRecordAct extends MyBaseActivity {

    private RecyclerView rv_record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frozenrecord_layout);
        initView();
    }

    private void initView() {
        rv_record = (RecyclerView) findViewById(R.id.rv_record);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_record.setLayoutManager(manager);
        setData();
    }

    @Override
    public void handleCode(String code) {


    }


    private void setData() {
        ArrayList<FrozenDetail> data = (ArrayList<FrozenDetail>) getIntent().getSerializableExtra("record");
        FrozenAdapter adapter = new FrozenAdapter(data, this);
        rv_record.setAdapter(adapter);
        rv_record.addItemDecoration(new MyDivider(this, MyDivider.VERTICAL_LIST));
        LogUtil.i("测试", data.size() + "");
    }
}
