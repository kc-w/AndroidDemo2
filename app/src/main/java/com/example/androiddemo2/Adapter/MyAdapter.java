package com.example.androiddemo2.Adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatCheckBox;
import com.example.androiddemo2.Bean.History;
import com.example.androiddemo2.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends BaseAdapter {

    List<History> data = new ArrayList<>();
    private Context mContext;
    ViewHolder holder;
    //表示当前是否是多选状态。
    private boolean isShowCheckBox = false;
    //用来存放CheckBox的选中状态，true为选中,false为没有选中
    private SparseBooleanArray stateCheckedMap = new SparseBooleanArray();

    public MyAdapter(Context context, List<History> data, SparseBooleanArray stateCheckedMap) {
        mContext = context;
        this.data = data;
        this.stateCheckedMap = stateCheckedMap;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            holder = new ViewHolder();
            //选项框样式layout
            convertView = View.inflate(mContext, R.layout.fragment1_layout, null);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //选项框
        holder.checkBox = convertView.findViewById(R.id.checkboxs);
        holder.Data_number = convertView.findViewById(R.id.number);
        holder.Data_id = convertView.findViewById(R.id.id);
        holder.Data_project = convertView.findViewById(R.id.project);
        holder.Data_result = convertView.findViewById(R.id.result);
        holder.Data_time = convertView.findViewById(R.id.time);
        holder.Data_mark = convertView.findViewById(R.id.mark);

        //控制CheckBox的那个的框显示与隐藏
        showAndHideCheckBox();
        //将记录进行显示
        holder.Data_number.setText(data.get(position).getNumber());
        holder.Data_id.setText(data.get(position).getId());
        holder.Data_project.setText(data.get(position).getProject());
        holder.Data_result.setText(data.get(position).getResult());
        holder.Data_time.setText(data.get(position).getTime());
        holder.Data_mark.setText(data.get(position).getMark());
        //设置CheckBox是否选中
        holder.checkBox.setChecked(stateCheckedMap.get(position));
        return convertView;
    }

    public class ViewHolder {
        public TextView Data_number;
        public TextView Data_id;
        public TextView Data_project;
        public TextView Data_result;
        public TextView Data_time;
        public TextView Data_mark;
        public AppCompatCheckBox checkBox;
    }

    private void showAndHideCheckBox() {
        if (isShowCheckBox) {
            holder.checkBox.setVisibility(View.VISIBLE);
        } else {
            holder.checkBox.setVisibility(View.GONE);
        }
    }


    public boolean isShowCheckBox() {
        return isShowCheckBox;
    }

    public void setShowCheckBox(boolean showCheckBox) {
        isShowCheckBox = showCheckBox;
    }

}
