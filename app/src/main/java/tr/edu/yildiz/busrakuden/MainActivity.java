package tr.edu.yildiz.busrakuden;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button eCekmece;
    private Button eKabin;
    private Button eEtkinlik;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eCekmece = findViewById(R.id.bCekmece);
        eKabin = findViewById(R.id.bKabin);
        eEtkinlik= findViewById(R.id.bEtkinlik);

        eCekmece.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CekmeceEkleSilActivity.class);
                startActivity(intent);
            }
        });

        eKabin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, KabinOdasiActivity.class);
                startActivity(intent);
            }
        });
    }
}