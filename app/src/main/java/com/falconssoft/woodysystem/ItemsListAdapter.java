package com.falconssoft.woodysystem;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.falconssoft.woodysystem.models.BundleInfo;

import java.util.ArrayList;
import java.util.List;

import static com.falconssoft.woodysystem.LoadingOrder.bundles;
import static com.falconssoft.woodysystem.LoadingOrder.filteredList;

public class ItemsListAdapter extends BaseAdapter {

    private Context context;
    private List<BundleInfo> mOriginalValues;
    private static List<BundleInfo> itemsList;
    private static List<BundleInfo> selectedBundles ;
    static LoadingOrder obj = new LoadingOrder();
    private boolean changeColor;

    public ItemsListAdapter(Context context, List<BundleInfo> itemsList, boolean changeColor) {
        this.context = context;
        this.mOriginalValues = itemsList;
        this.itemsList = itemsList;
        selectedBundles = new ArrayList<>();
        this.changeColor = changeColor;
    }

    public ItemsListAdapter() {

    }

    public void setItemsList(List<BundleInfo> itemsList) {
        this.itemsList = itemsList;
    }

    @Override
    public int getCount() {
        return itemsList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        CheckBox checkBox;
        TextView th, w, l, grade, pcs, bundle, location, area, packingList;
        LinearLayout linearLayout;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder = new ViewHolder();
        view = View.inflate(context, R.layout.item_row, null);

        holder.checkBox = (CheckBox) view.findViewById(R.id.checkbox);
        holder.th = (TextView) view.findViewById(R.id.th);
        holder.w = (TextView) view.findViewById(R.id.w);
        holder.l = (TextView) view.findViewById(R.id.l);
        holder.grade = (TextView) view.findViewById(R.id.grade);
        holder.pcs = (TextView) view.findViewById(R.id.pcs);
        holder.bundle = (TextView) view.findViewById(R.id.bundle);
        holder.location = (TextView) view.findViewById(R.id.location);
        holder.area = (TextView) view.findViewById(R.id.area);
        holder.packingList = (TextView) view.findViewById(R.id.loadingOrder_packingList);


        holder.th.setText("" + itemsList.get(i).getThickness());
        holder.w.setText("" + itemsList.get(i).getWidth());
        holder.l.setText("" + itemsList.get(i).getLength());
        holder.grade.setText("" + itemsList.get(i).getGrade());
        holder.pcs.setText("" + itemsList.get(i).getNoOfPieces());
        holder.bundle.setText("" + itemsList.get(i).getBundleNo());
        holder.location.setText("" + itemsList.get(i).getLocation());
        holder.area.setText("" + itemsList.get(i).getArea());
        holder.packingList.setText("" + itemsList.get(i).getBackingList());

        holder.linearLayout= view.findViewById(R.id.linear);
        if (itemsList.get(i).getChecked())
            holder.checkBox.setChecked(true);

        if (changeColor) // activated when remove barcode
            itemsList.get(i).setFoucoseColor("0");


        if(itemsList.get(i).getFoucoseColor().equals("1")){
            holder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.alpha_black));
        }else  if(itemsList.get(i).getFoucoseColor().equals("0")){
            holder.linearLayout.setBackground(context.getResources().getDrawable(R.drawable.border_background));

        }

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    itemsList.get(i).setChecked(true);
                    holder.linearLayout.setBackground(context.getResources().getDrawable(R.drawable.border_background));
                }
                else {
                    itemsList.get(i).setChecked(false);
                    holder.linearLayout.setBackground(context.getResources().getDrawable(R.drawable.border_background));
                }
                bundles.get(i).setFoucoseColor("0");

                obj.notifyAdapter(itemsList.get(i) , context);
            }
        });

        return view;
    }

    public List<BundleInfo> getSelectedItems() {

        selectedBundles.clear();
        for(int i = 0 ; i< itemsList.size() ; i++)
            if(itemsList.get(i).getChecked())
                selectedBundles.add(itemsList.get(i));

        return selectedBundles;
    }

    public List<BundleInfo> getItems() {
        return itemsList;
    }


}
