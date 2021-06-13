package tr.edu.yildiz.busrakuden;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KabinKiyafetlerActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSION = 0 ;
    private TextView tvCekmeceIsmi;
    private String cekmeceIsmi;
    private ArrayList<String> mImages = new ArrayList<String>();
    private ArrayList<String> mOzellikler = new ArrayList<String>();
    private ArrayList<String> listImageOzellikler = new ArrayList<String>();
    private ArrayList<String> images = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kabin_kiyafetler);
        tvCekmeceIsmi = findViewById(R.id.textView4);
        cekmeceIsmi = getIntent().getExtras().get("cekmece_ismi").toString();
        images = getIntent().getStringArrayListExtra("images");


        tvCekmeceIsmi.setText(cekmeceIsmi);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int hasWritePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int hasReadPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

            List<String> permissions = new ArrayList<String>();
            if (hasWritePermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            } else {
//              preferencesUtility.setString("storage", "true");
            }

            if (hasReadPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);

            } else {
//              preferencesUtility.setString("storage", "true");
            }

            if (!permissions.isEmpty()) {
//              requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_CODE_SOME_FEATURES_PERMISSIONS);

                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE},
                        REQUEST_PERMISSION);
            }
        }
        initImageBitmaps();
    }
    private void initImageBitmaps(){
        String pathname=getFilesDir()+"/"+cekmeceIsmi+".txt";
        File fileEvents = new File(pathname);
        if (fileEvents.exists()){
            try {
                BufferedReader br = new BufferedReader(new FileReader(fileEvents));
                String line;

                while ((line = br.readLine()) != null) {
                    System.out.println("LİSTE: "+line);
                    String[] splitString = line.split("\t");
                    String bilgiler = "Kıyafet Türü: "+splitString[1]+"\tRenk: "+splitString[2]+"\nDesen: "+splitString[3]
                            +"\tFiyat: "+splitString[4]+"\tTarih: "+splitString[5];
                    System.out.println("Bilgiler: "+bilgiler);
                    mOzellikler.add(bilgiler);
                    mImages.add(splitString[0]);

                    listImageOzellikler.add(line);
                    System.out.println("List: "+mOzellikler);

                }
                br.close();
            } catch (IOException e) {
                System.out.println("HATA");
            }
        }else{
            mOzellikler.add("Eklenmiş Kıyafet bulunmamaktadır.");
        }
        initRecyclerView();
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerAdapterKiyafetlerKabin adapter = new RecyclerAdapterKiyafetlerKabin(this, mImages, mOzellikler,cekmeceIsmi,listImageOzellikler,images);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}