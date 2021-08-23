package com.navyanshkesarwani.cowinguard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<Model> center_list;
    public Adapter (List<Model>center_list){ this .center_list=center_list; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        String center_name = center_list.get(position).getCenter_name();
        String timing1 = center_list.get(position).getTiming1();
        String timing2 = center_list.get(position).getTiming2();
        String timing3 = center_list.get(position).getTiming3();
        String timing4 = center_list.get(position).getTiming4();
        int info_button = center_list.get(position).getInfo_button();
        holder.setData(center_name, timing1, timing2, timing3, timing4, info_button);

    }


    @Override
    public int getItemCount() {
        return center_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView center_name;
        private TextView timing1;
        private TextView timing2;
        private TextView timing3;
        private TextView timing4;
        private ImageButton info_button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            center_name = itemView.findViewById(R.id.center_name);
            timing1 = itemView.findViewById(R.id.timing1);
            timing2 = itemView.findViewById(R.id.timing2);
            timing3 = itemView.findViewById(R.id.timing3);
            timing4 = itemView.findViewById(R.id.timing4);
            info_button = itemView.findViewById(R.id.info);
        }

        public void setData(String center_name, String timing1, String timing2, String timing3, String timing4, int info_button) {
            
        }
    }

}
