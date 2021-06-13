package tr.edu.yildiz.busrakuden;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapterCekmeceKabin extends RecyclerView.Adapter<RecyclerAdapterCekmeceKabin.MyViewHolder> {
    private ArrayList<String> list;
    private ArrayList<String> images;
    private Context mcontext;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;


    public RecyclerAdapterCekmeceKabin(Context context, ArrayList<String> images, ArrayList<String> list){
        this.mcontext = context;
        this.list = list;
        this.images = images;
    }
    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_cekmece_isimleri,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(textView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {
        holder.Cekmece.setText(list.get(position));
        holder.Cekmece.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, KabinKiyafetlerActivity.class);
                intent.putExtra("cekmece_ismi",list.get(position));
                intent.putStringArrayListExtra("images", (ArrayList<String>) images);
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Cekmece;

        public MyViewHolder(TextView itemView) {
            super(itemView);
            Cekmece = itemView;

        }
    }


}

