package tr.edu.yildiz.busrakuden;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CekmeceActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSION = 0 ;
    private Button cekmeceSil;
    private ImageButton kiyafetEkle;
    private TextView tvCekmeceIsmi;
    private String cekmeceIsmi;
    private ArrayList<String> mImages = new ArrayList<String>();
    private ArrayList<String> mOzellikler = new ArrayList<String>();
    private ArrayList<String> listImageOzellikler = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cekmece);
        cekmeceSil = findViewById(R.id.button);
        kiyafetEkle = findViewById(R.id.imageButton);
        tvCekmeceIsmi = findViewById(R.id.textView4);
        cekmeceIsmi = getIntent().getExtras().get("cekmece_ismi").toString();
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

        cekmeceSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> list =  getIntent().getStringArrayListExtra("list");
                list.remove(cekmeceIsmi);
                String pathname = getFilesDir() + "/CekmeceIsimleri.txt";
                String str = null;
                int i;
                try {
                    FileOutputStream fos = new FileOutputStream(pathname, false);  // true for append mode
                    for(i=0;i<list.size()-1;i++){
                        str = list.get(i)+'\n';
                        byte[] b = str.getBytes();       //converts string into bytes
                        fos.write(b);
                    }
                    str = list.get(i);
                    byte[] b = str.getBytes();       //converts string into bytes
                    fos.write(b);           //writes bytes into file
                    fos.close();

                } catch (IOException e) {
                    e.printStackTrace();    //prints exception if any
                }


                finish();
                Intent intent = new Intent(CekmeceActivity.this, CekmeceEkleSilActivity.class);
                startActivity(intent);
            }
        });
        kiyafetEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(CekmeceActivity.this, KiyafetEkleActivity.class);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.putExtra("cekmece_ismi",cekmeceIsmi);
                startActivity(intent);
            }
        });
        initImageBitmaps();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION: {
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {


                        System.out.println("Permissions --> " + "Permission Granted: " + permissions[i]);
                    } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        System.out.println("Permissions --> " + "Permission Denied: " + permissions[i]);
                    }
                }
            }
            break;
            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
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
            mOzellikler.add("Eklenmiş Soru bulunmamaktadır.");
        }
        initRecyclerView();
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerAdapterKiyafetler adapter = new RecyclerAdapterKiyafetler(this, mImages, mOzellikler,cekmeceIsmi,listImageOzellikler);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}