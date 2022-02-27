package com.app.undoing.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.undoing.PdfDocument;
import com.app.undoing.R;
import com.app.undoing.model.EducationItem;

import java.util.List;

public class EducationAdapter extends RecyclerView.Adapter<EducationAdapter.EducationViewHolder> {
    private Context context;
    private List<EducationItem> list;

    public EducationAdapter(Context context,List<EducationItem> list){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public EducationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.education_list_item,parent,false);
        return new EducationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EducationViewHolder holder, int position) {
        holder.setData(position);
        holder.setListener(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class EducationViewHolder extends RecyclerView.ViewHolder{

        public EducationViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setData(int position) {
            itemView.findViewById(R.id.education_item_background).setBackgroundResource(list.get(position).imageSource);
            TextView textView= (TextView) itemView.findViewById(R.id.education_item_text);
            textView.setText(list.get(position).title);
        }

        public  void setListener(int position){
            itemView.setOnClickListener(view -> {
                Intent intent = new Intent();
                intent.putExtra("pdfName",list.get(position).pdfName);
                intent.setClass(context, PdfDocument.class);
                context.startActivity(intent);
            });
        }
    }


}
