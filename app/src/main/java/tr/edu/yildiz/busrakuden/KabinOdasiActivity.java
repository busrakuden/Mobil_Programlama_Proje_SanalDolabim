package tr.edu.yildiz.busrakuden;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class KabinOdasiActivity extends AppCompatActivity {
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> list = new ArrayList<String>();
    private RecyclerAdapterCekmeceKabin adapter;

    private Button bBasustuEkle;
    private Button bSuratEkle;
    private Button bUstbedenEkle;
    private Button bAltbedenEkle;
    private Button bAyakEkle;
    private ImageView iBastustu;
    private ImageView iSurat;
    private ImageView iUstbeden;
    private ImageView iAltbeden;
    private ImageView iAyak;
    private Button bKombinOlustur;
    private ArrayList<String> images = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kabin_odasi);
        bBasustuEkle = findViewById(R.id.bBasustuEkle);
        bSuratEkle = findViewById(R.id.bSuratEkle);
        bUstbedenEkle = findViewById(R.id.bUstBedenEkle);
        bAltbedenEkle = findViewById(R.id.bAltBedenEkle);
        bAyakEkle = findViewById(R.id.bAyakEkle);
        iBastustu = findViewById(R.id.ivBasustu);
        iSurat = findViewById(R.id.ivSurat);;
        iUstbeden = findViewById(R.id.ivUstBeden);
        iAltbeden = findViewById(R.id.ivAltBeden);
        iAyak = findViewById(R.id.ivAyak);
        bKombinOlustur = findViewById(R.id.bKombinOlustur);
        images = getIntent().getStringArrayListExtra("images");
        if(images!=null){
            for(int i=0;i<images.size();i++){
                String str=images.get(i);
                Uri imageUri;

                if(i==0){
                    if (str.substring(0,21).equals("content://com.android")) {
                        String [] photo_split= str.split("%3A");
                        String imageUriBasePath = "content://media/external/images/media/"+photo_split[1];
                        imageUri= Uri.parse(imageUriBasePath );
                        iBastustu.setImageURI(imageUri);
                    }
                }else if(i==1){
                    if (str.substring(0,21).equals("content://com.android")) {
                        String [] photo_split= str.split("%3A");
                        String imageUriBasePath = "content://media/external/images/media/"+photo_split[1];
                        imageUri= Uri.parse(imageUriBasePath );
                        iSurat.setImageURI(imageUri);
                    }

                }else if(i==2){
                    if (str.substring(0,21).equals("content://com.android")) {
                        String [] photo_split= str.split("%3A");
                        String imageUriBasePath = "content://media/external/images/media/"+photo_split[1];
                        imageUri= Uri.parse(imageUriBasePath );
                        iUstbeden.setImageURI(imageUri);
                    }

                }else if(i==3){
                    if (str.substring(0,21).equals("content://com.android")) {
                        String [] photo_split= str.split("%3A");
                        String imageUriBasePath = "content://media/external/images/media/"+photo_split[1];
                        imageUri= Uri.parse(imageUriBasePath );
                        iAltbeden.setImageURI(imageUri);
                    }

                }else if(i==4){
                    if (str.substring(0,21).equals("content://com.android")) {
                        String [] photo_split= str.split("%3A");
                        String imageUriBasePath = "content://media/external/images/media/"+photo_split[1];
                        imageUri= Uri.parse(imageUriBasePath );
                        iAyak.setImageURI(imageUri);
                    }

                }
            }
        }

        bBasustuEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KabinOdasiActivity.this, KabinCekmecelerActivity.class);
                intent.putStringArrayListExtra("images", (ArrayList<String>) images);
                startActivity(intent);
            }
        });
        bSuratEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KabinOdasiActivity.this, KabinCekmecelerActivity.class);
                intent.putStringArrayListExtra("images", (ArrayList<String>) images);
                startActivity(intent);

            }
        });
        bUstbedenEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KabinOdasiActivity.this, KabinCekmecelerActivity.class);
                intent.putStringArrayListExtra("images", (ArrayList<String>) images);
                startActivity(intent);

            }
        });
        bAltbedenEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KabinOdasiActivity.this, KabinCekmecelerActivity.class);
                intent.putStringArrayListExtra("images", (ArrayList<String>) images);
                startActivity(intent);

            }
        });
        bAyakEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KabinOdasiActivity.this, KabinCekmecelerActivity.class);
                intent.putStringArrayListExtra("images", (ArrayList<String>) images);
                startActivity(intent);

            }
        });

        bKombinOlustur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pathname = getFilesDir() + "/Kombinler.txt";
                File file = new File(pathname);
                boolean result;
                String str = null;
                try {
                    result = file.createNewFile();  //creates a new file
                    FileOutputStream fos = new FileOutputStream(pathname, true);  // true for append mode
                    if (result) {
                        System.out.println("file created " + file.getCanonicalPath()); //returns the path string
                        str = images.get(0)+"\t"+images.get(1)+"\t"+images.get(2)+"\t"+images.get(1)+"\t"+images.get(3)+"\t"+images.get(4);
                    } else {
                        System.out.println("File already exist at location: " + file.getCanonicalPath());
                        str = "\n" + images.get(0)+"\t"+images.get(1)+"\t"+images.get(2)+"\t"+images.get(1)+"\t"+images.get(3)+"\t"+images.get(4);

                    }
                    byte[] b = str.getBytes();       //converts string into bytes
                    fos.write(b);           //writes bytes into file
                    fos.close();

                } catch (IOException e) {
                    e.printStackTrace();    //prints exception if any
                }
                finish();
                images = new ArrayList<String>();
                Intent intent = new Intent(KabinOdasiActivity.this, KabinOdasiActivity.class);
                intent.putStringArrayListExtra("images", (ArrayList<String>) images);
                startActivity(intent);
            }
        });

    }

}