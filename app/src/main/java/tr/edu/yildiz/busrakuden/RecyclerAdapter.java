package tr.edu.yildiz.busrakuden;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private ArrayList<String> list;
    private Context mcontext;
    public RecyclerAdapter(Context context, ArrayList<String> list){
        this.mcontext = context;
        this.list = list;
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
                System.out.println("TIKLANDI "+position);
                Intent intent = new Intent(mcontext, CekmeceActivity.class);
                intent.putExtra("cekmece_ismi",list.get(position));
                intent.putStringArrayListExtra("list", (ArrayList<String>) list);
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

