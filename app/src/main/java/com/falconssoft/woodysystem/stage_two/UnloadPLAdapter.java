package com.falconssoft.woodysystem.stage_two;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.falconssoft.woodysystem.R;
import com.falconssoft.woodysystem.models.PlannedPL;

import java.util.List;

public class UnloadPLAdapter extends RecyclerView.Adapter<UnloadPLAdapter.SuppliersViewHolder> {

    private UnloadPackingList plannedPL;
    private List<PlannedPL> PlannedPL;

    public UnloadPLAdapter(Context plannedPL, List<PlannedPL> PlannedPL) {

        this.plannedPL = (UnloadPackingList) plannedPL;
        this.PlannedPL = PlannedPL;
    }

    @NonNull
    @Override

    public SuppliersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.unload_row, viewGroup, false);
        return new SuppliersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuppliersViewHolder holder, int i) {

        Double CBM =  (PlannedPL.get(i).getThickness() * PlannedPL.get(i).getWidth() * PlannedPL.get(i).getLength() * PlannedPL.get(i).getNoOfPieces() * PlannedPL.get(i).getNoOfCopies());

        holder.serial.setText("" + (i + 1));
        holder.cust.setText(PlannedPL.get(i).getCustName());
        holder.pl.setText(PlannedPL.get(i).getPackingList());
        holder.dest.setText(PlannedPL.get(i).getDestination());
        holder.order.setText(PlannedPL.get(i).getOrderNo());
        holder.supplier.setText(PlannedPL.get(i).getSupplier());
        holder.grade.setText(PlannedPL.get(i).getGrade());
//        holder.width.setText("" + (int) PlannedPL.get(i).getWidth());
//        holder.length.setText("" + (int) PlannedPL.get(i).getLength());
        holder.pieces.setText("" + (int) PlannedPL.get(i).getNoOfPieces());
        holder.copies.setText("" + PlannedPL.get(i).getNoOfCopies());
        holder.cubic.setText("" + String.format("%.3f", (PlannedPL.get(i).getCubic() )));

        if(PlannedPL.get(i).getIsChecked())
            holder.checkBox.setChecked(true);

        int index = i;
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    PlannedPL.get(index).setIsChecked(true);
                else
                    PlannedPL.get(index).setIsChecked(false);
            }
        });

    }

    @Override
    public int getItemCount() {
        return PlannedPL.size();
    }

    class SuppliersViewHolder extends RecyclerView.ViewHolder {

        TextView serial, cust, pl, dest, order, supplier,grade , width, length, pieces, copies, isExist, edit, delete, copy, cubic;
        LinearLayout linearLayout;
        CheckBox checkBox;

        public SuppliersViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linear);

            serial = itemView.findViewById(R.id.serial);
            cust = itemView.findViewById(R.id.cust);
            pl = itemView.findViewById(R.id.pl);
            dest = itemView.findViewById(R.id.dest);
            order = itemView.findViewById(R.id.order_no);
            supplier = itemView.findViewById(R.id.supplier);
            grade = itemView.findViewById(R.id.grade);
//            width = itemView.findViewById(R.id.width);
//            length = itemView.findViewById(R.id.length);
            pieces = itemView.findViewById(R.id.pieces);
            copies = itemView.findViewById(R.id.copies);
            isExist = itemView.findViewById(R.id.is_exist);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            copy = itemView.findViewById(R.id.copy);
            cubic = itemView.findViewById(R.id.cubic);
            checkBox = itemView.findViewById(R.id.check_button);
        }
    }
}