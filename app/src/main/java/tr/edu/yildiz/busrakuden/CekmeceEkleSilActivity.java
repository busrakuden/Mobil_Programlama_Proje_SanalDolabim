package tr.edu.yildiz.busrakuden;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CekmeceEkleSilActivity extends AppCompatActivity {
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private ImageButton eCekmeceEkle;
    private ImageButton eCekmeceSil;
    private EditText cekmeceIsmi;
    private Button bEkle;
    private Button bCik;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> list = new ArrayList<String>();
    private RecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cekmece_ekle__sil);
        eCekmeceEkle = findViewById(R.id.bCekmeceEkle);

        recyclerView = findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        String pathname=getFilesDir()+"/CekmeceIsimleri.txt";
        File fileEvents = new File(pathname);
        if (fileEvents.exists()){
            try {
                BufferedReader br = new BufferedReader(new FileReader(fileEvents));
                String line;

                while ((line = br.readLine()) != null) {
                    list.add(line);
                }
                br.close();
            } catch (IOException e) {
                System.out.println("HATAAAAAA");
            }
        }else{
            list.add("Henüz Çekmece Eklenmemiştir.");
        }


        adapter = new RecyclerAdapter(this,list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);


        eCekmeceEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
            }
        });


    }
    public void createDialog(){
        dialogBuilder =new AlertDialog.Builder(this);
        final View popupView = getLayoutInflater().inflate(R.layout.popup,null);
        cekmeceIsmi = (EditText) popupView.findViewById(R.id.etIsim);
        bEkle = (Button) popupView.findViewById(R.id.bEkle);
        bCik = (Button) popupView.findViewById(R.id.bCik);
        dialogBuilder.setView(popupView);
        dialog = dialogBuilder.create();
        dialog.show();
        bEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newIsim = cekmeceIsmi.getText().toString();
                if (newIsim.equals("")){
                    Toast.makeText(CekmeceEkleSilActivity.this,"Fill in all fields!",Toast.LENGTH_SHORT).show();

                }else {
                    String pathname = getFilesDir() + "/CekmeceIsimleri.txt";
                    File file = new File(pathname);
                    boolean result;
                    String str = null;
                    try {
                        result = file.createNewFile();  //creates a new file
                        FileOutputStream fos = new FileOutputStream(pathname, true);  // true for append mode
                        if (result) {
                            System.out.println("file created " + file.getCanonicalPath()); //returns the path string


                            str = newIsim;      //str stores the string which we have entered
                        } else {
                            System.out.println("File already exist at location: " + file.getCanonicalPath());
                            str = "\n" + newIsim;      //str stores the string which we have entered

                        }
                        byte[] b = str.getBytes();       //converts string into bytes
                        fos.write(b);           //writes bytes into file
                        fos.close();

                    } catch (IOException e) {
                        e.printStackTrace();    //prints exception if any
                    }
                }
                dialog.dismiss();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        bCik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}