package tr.edu.yildiz.busrakuden;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class KabinCekmecelerActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> list = new ArrayList<String>();
    private RecyclerAdapterCekmeceKabin adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kabin_cekmeceler);

        ArrayList<String> images = getIntent().getStringArrayListExtra("images");
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


        adapter = new RecyclerAdapterCekmeceKabin(this,images,list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
}