package com.wenyou.yuilibrary.widget.flowlayout;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.wenyou.yuilibrary.R;


/**
 * @description 默认流标签布局适配器
 * @date: 2021/2/22 16:48
 * @author: jy
 */
public class DefaultFlowTagAdapter extends BaseTagAdapter<String, TextView> {

    public DefaultFlowTagAdapter(Context context) {
        super(context);
    }

    @Override
    protected TextView newViewHolder(View convertView) {
        return convertView.findViewById(R.id.tv_tag_item);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.yui_lv_item_default_flow_tag;
    }

    @Override
    protected void convert(TextView textView, String item, int position) {
        textView.setText(item);
    }
}
