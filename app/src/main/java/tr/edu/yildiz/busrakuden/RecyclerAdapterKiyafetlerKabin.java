package tr.edu.yildiz.busrakuden;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapterKiyafetlerKabin extends RecyclerView.Adapter<RecyclerAdapterKiyafetlerKabin.ViewHolder> {
    private ArrayList<String> mImages = new ArrayList<String>();
    private ArrayList<String> mOzellikler = new ArrayList<String>();
    private ArrayList<String> mListImageOzellikler = new ArrayList<String>();
    private ArrayList<String> mimages = new ArrayList<String>();
    Context mContext;
    String mCekmeceIsmi;
    public RecyclerAdapterKiyafetlerKabin(Context context,ArrayList<String> images, ArrayList<String> ozellikler,String cekmeceIsmi,ArrayList<String> listImageOzellikler,ArrayList<String> selectedimages){
        mImages = images;
        mOzellikler = ozellikler;
        mContext = context;
        mCekmeceIsmi = cekmeceIsmi;
        mListImageOzellikler = listImageOzellikler;
        mimages =selectedimages;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_kiyafetler,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        System.out.println("mOZELLİKLER:"+mOzellikler);
        if(mImages.isEmpty()){
            holder.ozellikler.setText("Henüz kıyafet yüklenmemiştir.");
        }else{
            Uri imageUri;
            if (mImages.get(position).substring(0,21).equals("content://com.android")) {
                String [] photo_split= mImages.get(position).split("%3A");
                String imageUriBasePath = "content://media/external/images/media/"+photo_split[1];
                imageUri= Uri.parse(imageUriBasePath );
                holder.image.setImageURI(imageUri);
            }
            //holder.image.setImageURI(Uri.parse(mImages.get(position)));
            holder.ozellikler.setText(mOzellikler.get(position));
        }
        holder.parenLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, KabinOdasiActivity.class);
                if(mimages == null){
                    mimages = new ArrayList<String>();
                }
                mimages.add(mImages.get(position));
                intent.putStringArrayListExtra("images", (ArrayList<String>) mimages);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mOzellikler.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView ozellikler;
        RelativeLayout parenLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            ozellikler = itemView.findViewById(R.id.kiyafetBilgileri);;
            parenLayout = itemView.findViewById(R.id.parent_layout);;
        }
    }
}
