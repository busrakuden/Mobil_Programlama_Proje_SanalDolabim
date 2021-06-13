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
import java.util.ArrayList;
import java.util.Arrays;

public class KiyafetGuncelleSilActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private String cekmeceIsmi;
    private ImageView imageView;
    private Button eResimSec;
    private String str = null;
    private Button bGuncelle;
    private EditText eTarih;
    private EditText eFiyat;
    private Button bSil;
    private ArrayList<String> mListImageOzellikler;
    int SELECT_PICTURE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kiyafet_guncelle_sil);

        ArrayList<String> mOzellikler =  getIntent().getStringArrayListExtra("ozellikler");
        ArrayList<String> mImages =  getIntent().getStringArrayListExtra("images");
        cekmeceIsmi = getIntent().getExtras().get("cekmece_ismi").toString();
        int position = Integer.parseInt(getIntent().getExtras().get("position").toString());
        mListImageOzellikler =  getIntent().getStringArrayListExtra("list");

        Spinner sTur = findViewById(R.id.sKiyafetTuru);
        Spinner sRenk = findViewById(R.id.sRenk);
        Spinner sDesen = findViewById(R.id.sDesen);
        imageView = findViewById(R.id.imageView);
        eResimSec = findViewById(R.id.bResimSec);
        bGuncelle = findViewById(R.id.bkiyafetGuncelle);
        eTarih = findViewById(R.id.etTarih);
        eFiyat = findViewById(R.id.etFiyat);
        bSil = findViewById(R.id.bKiyafetSil);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.tur, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sTur.setAdapter(adapter);

        String[] turs = getResources().getStringArray(R.array.tur);

        ArrayList<String> tur_names = new ArrayList<String>(Arrays.asList(turs));
        String[] splitString = mListImageOzellikler.get(position).split("\t");
        sTur.setSelection(tur_names.indexOf(splitString[1]));
        sTur.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterRenk = ArrayAdapter.createFromResource(this,R.array.renk, android.R.layout.simple_spinner_item);
        adapterRenk.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sRenk.setAdapter(adapterRenk);
        sRenk.setOnItemSelectedListener(this);
        String[] renkler = getResources().getStringArray(R.array.renk);
        ArrayList<String> renk_names = new ArrayList<String>(Arrays.asList(renkler));
        sRenk.setSelection(renk_names.indexOf(splitString[2]));

        ArrayAdapter<CharSequence> adapterDesen = ArrayAdapter.createFromResource(this,R.array.desen, android.R.layout.simple_spinner_item);
        adapterDesen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sDesen.setAdapter(adapterDesen);
        sDesen.setOnItemSelectedListener(this);
        String[] desenler = getResources().getStringArray(R.array.desen);
        ArrayList<String> desen_names = new ArrayList<String>(Arrays.asList(desenler));
        sRenk.setSelection(desen_names.indexOf(splitString[3]));
        str=splitString[0];
        Uri imageUri;
        if (str.substring(0,21).equals("content://com.android")) {
            String [] photo_split= str.split("%3A");
            String imageUriBasePath = "content://media/external/images/media/"+photo_split[1];
            imageUri= Uri.parse(imageUriBasePath );
            imageView.setImageURI(imageUri);
        }
        //imageView.setImageURI(Uri.parse(str));
        eFiyat.setText(splitString[4]);
        eTarih.setText(splitString[5]);



        eResimSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
        bGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String line;

                System.out.println("SELECTED:" +str);
                if((str!=null)&&(eTarih!=null)&&(eFiyat!=null)){
                    mListImageOzellikler.remove(mListImageOzellikler.get(position));
                    System.out.println("SELECTED:" +sTur.getSelectedItem().toString());
                    mListImageOzellikler.add(position,str+"\t"+sTur.getSelectedItem().toString()+"\t"+
                            sRenk.getSelectedItem().toString()+"\t"+
                            sDesen.getSelectedItem().toString()+"\t"+
                            eFiyat.getText().toString()+"\t"+eTarih.getText().toString());
                    String pathname = getFilesDir() + "/"+cekmeceIsmi+".txt";
                    String str = null;
                    int i;
                    try {
                        FileOutputStream fos = new FileOutputStream(pathname, false);  // true for append mode
                        for(i=0;i<mListImageOzellikler.size()-1;i++){
                            str = mListImageOzellikler.get(i)+'\n';
                            byte[] b = str.getBytes();       //converts string into bytes
                            fos.write(b);
                        }
                        str = mListImageOzellikler.get(i);
                        byte[] b = str.getBytes();       //converts string into bytes
                        fos.write(b);           //writes bytes into file
                        fos.close();

                    } catch (IOException e) {
                        e.printStackTrace();    //prints exception if any
                    }
                }
                Intent intent = getIntent();
                finish();
                intent = new Intent(KiyafetGuncelleSilActivity.this, CekmeceActivity.class);
                intent.putExtra("cekmece_ismi",cekmeceIsmi);
                startActivity(intent);
            }
        });
        bSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pathname = getFilesDir() + "/"+cekmeceIsmi+".txt";
                mListImageOzellikler.remove(mListImageOzellikler.get(position));
                if(mListImageOzellikler.isEmpty()){
                    File file = new File(pathname);
                    file.delete();
                }else{
                    String str = null;
                    int i;
                    try {
                        FileOutputStream fos = new FileOutputStream(pathname, false);  // true for append mode
                        for(i=0;i<mListImageOzellikler.size()-1;i++){
                            str = mListImageOzellikler.get(i)+'\n';
                            byte[] b = str.getBytes();       //converts string into bytes
                            fos.write(b);
                        }
                        str = mListImageOzellikler.get(i);
                        byte[] b = str.getBytes();       //converts string into bytes
                        fos.write(b);           //writes bytes into file
                        fos.close();

                    } catch (IOException e) {
                        e.printStackTrace();    //prints exception if any
                    }
                }




                finish();
                Intent intent = new Intent(KiyafetGuncelleSilActivity.this, CekmeceActivity.class);
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