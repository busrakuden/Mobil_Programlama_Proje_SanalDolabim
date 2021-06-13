package tr.edu.yildiz.busrakuden;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class KiyafetEkleActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ImageView imageView;
    private Button eResimSec;
    private String cekmeceIsmi;
    private String str = null;
    private Button bEkle;
    private EditText eTarih;
    private EditText eFiyat;
    int SELECT_PICTURE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kiyafet_ekle);
        cekmeceIsmi = getIntent().getExtras().get("cekmece_ismi").toString();
        Spinner sTur = findViewById(R.id.sKiyafetTuru);
        Spinner sRenk = findViewById(R.id.sRenk);
        Spinner sDesen = findViewById(R.id.sDesen);
        imageView = findViewById(R.id.imageView);
        eResimSec = findViewById(R.id.bResimSec);
        bEkle = findViewById(R.id.bkiyafetGuncelle);
        eTarih = findViewById(R.id.etTarih);
        eFiyat = findViewById(R.id.etFiyat);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.tur, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sTur.setAdapter(adapter);
        sTur.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterRenk = ArrayAdapter.createFromResource(this,R.array.renk, android.R.layout.simple_spinner_item);
        adapterRenk.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sRenk.setAdapter(adapterRenk);
        sRenk.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterDesen = ArrayAdapter.createFromResource(this,R.array.desen, android.R.layout.simple_spinner_item);
        adapterDesen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sDesen.setAdapter(adapterDesen);
        sDesen.setOnItemSelectedListener(this);

        System.out.println("CEKMECE: "+sTur.getSelectedItem().toString());
        eResimSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
        bEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String line;
                if((str!=null)&&(eTarih!=null)&&(eFiyat!=null)){
                    String pathname = getFilesDir() + "/"+cekmeceIsmi+".txt";
                    File file = new File(pathname);
                    boolean result;
                    try {
                        result = file.createNewFile();  //creates a new file
                        FileOutputStream fos = new FileOutputStream(pathname, true);  // true for append mode
                        if (result) {
                            System.out.println("file created " + file.getCanonicalPath()); //returns the path string
                            line=str+"\t"+sTur.getSelectedItem().toString()+"\t"+
                                    sRenk.getSelectedItem().toString()+"\t"+
                                    sDesen.getSelectedItem().toString()+"\t"+
                                    eFiyat.getText().toString()+"\t"+eTarih.getText().toString();
                        } else {
                            System.out.println("File already exist at location: " + file.getCanonicalPath());
                            line="\n"+str+"\t"+sTur.getSelectedItem().toString()+"\t"+
                                    sRenk.getSelectedItem().toString()+"\t"+
                                    sDesen.getSelectedItem().toString()+"\t"+
                                    eFiyat.getText().toString()+"\t"+eTarih.getText().toString();      //str stores the string which we have entered
                        }
                        byte[] b = line.getBytes();       //converts string into bytes
                        fos.write(b);           //writes bytes into file
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();    //prints exception if any
                    }
                }
                Intent intent = getIntent();
                finish();
                intent = new Intent(KiyafetEkleActivity.this, CekmeceActivity.class);
                intent.putExtra("cekmece_ismi",cekmeceIsmi);
                startActivity(intent);
            }
        });

    }
    void imageChooser() {
        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout

                    //imageView.setImageURI(selectedImageUri);
                    str = selectedImageUri.toString();
                    imageView.setImageURI(Uri.parse(str));
                }
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}