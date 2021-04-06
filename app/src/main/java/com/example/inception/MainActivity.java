package com.example.inception;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
   Button up,down,a;
   AudioManager audioManager;
   Context context;
   EditText fill_text_et;
   TextView output_resutl_tv;
   String fileName,filePath,fileContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;

        up = findViewById(R.id.btnUp);
        down = findViewById(R.id.btnDown);
        fill_text_et = findViewById(R.id.type_text_here_et);
        output_resutl_tv = findViewById(R.id.read_results_tv);

        fileName = "Myfile.txt";
        filePath = "MyFileDir";
        fileContent="";

        audioManager = (AudioManager) context.getSystemService(AUDIO_SERVICE);

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
            }
        });

        fileName = "Myfile2.txt";
        filePath = "MyFileDir";
        fileContent="";
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                output_resutl_tv.setText("");
                fileContent = fill_text_et.getText().toString().trim();
                File myExtenalFile = new File((getExternalFilesDir(filePath)),fileName);
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(myExtenalFile);
                    fos.write(fileContent.getBytes());
                    fill_text_et.setText("");

                    myExtenalFile.delete();
                    Toast.makeText(context, "saved", Toast.LENGTH_SHORT).show();
                    return;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(context, "mission failed", Toast.LENGTH_SHORT).show();
            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:{
                if(requestCode==1 && data!=null){
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    fill_text_et.setText(fill_text_et.getText().toString()+text.get(0));
                }
            }
        }
    }

    public void func(View view) {
        Toast.makeText(context, ""+audioManager.getStreamVolumeDb(AudioManager.STREAM_MUSIC,10000, AudioDeviceInfo.TYPE_BUILTIN_SPEAKER),Toast.LENGTH_SHORT).show();
    }
}