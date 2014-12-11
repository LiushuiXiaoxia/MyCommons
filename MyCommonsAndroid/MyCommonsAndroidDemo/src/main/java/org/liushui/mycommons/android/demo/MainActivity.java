package org.liushui.mycommons.android.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.liushui.mycommons.android.annotation.OnItemClick;
import org.liushui.mycommons.android.annotation.ViewInject;
import org.liushui.mycommons.android.annotation.helper.InjectHelper;
import org.liushui.mycommons.android.demo.common.CommonExtraParam;
import org.liushui.mycommons.android.demo.common.CommonFragment;
import org.liushui.mycommons.android.demo.common.CommonFragmentActivity;
import org.liushui.mycommons.android.demo.ui.MsgHelperFragment;
import org.liushui.mycommons.android.demo.ui.ViewInjectFragment;


public class MainActivity extends BaseActivity {

    static String[] items = {"View Inject", "Msg Helper"};
    static Class<? extends CommonFragment> clazz[] = new Class[]{ViewInjectFragment.class, MsgHelperFragment.class};

    @ViewInject(R.id.listview)
    ListView listview;
    ArrayAdapter<String> adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InjectHelper.init(this, this);


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        adapter.addAll(items);
        listview.setAdapter(adapter);
    }

    @OnItemClick(R.id.listview)
    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Class<? extends CommonFragment> cls = clazz[position];
            CommonExtraParam param = new CommonExtraParam(adapter.getItem(position), cls);

            CommonFragmentActivity.launch(MainActivity.this, param, position);
        }
    };
}