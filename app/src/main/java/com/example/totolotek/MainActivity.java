package com.example.totolotek;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static String CHANNEL_ID = "Kanal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        EditText[] editNumber = new EditText[6];

        editNumber[0] = findViewById(R.id.editNumber1);
        editNumber[1] = findViewById(R.id.editNumber2);
        editNumber[2] = findViewById(R.id.editNumber3);
        editNumber[3] = findViewById(R.id.editNumber4);
        editNumber[4] = findViewById(R.id.editNumber5);
        editNumber[5] = findViewById(R.id.editNumber6);

        Button button = findViewById(R.id.button);

        Button result1 = findViewById(R.id.result1);
        Button result2 = findViewById(R.id.result2);
        Button result3 = findViewById(R.id.result3);
        Button result4 = findViewById(R.id.result4);
        Button result5 = findViewById(R.id.result5);
        Button result6 = findViewById(R.id.result6);

        List<Integer> userNumbers = new ArrayList<>();
        List<Integer> randomNumbers = new ArrayList<>();
        List<Integer> hits = new ArrayList<>();

        Random random = new Random();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    userNumbers.add(Integer.parseInt(editNumber[0].getText().toString()));
                    userNumbers.add(Integer.parseInt(editNumber[1].getText().toString()));
                    userNumbers.add(Integer.parseInt(editNumber[2].getText().toString()));
                    userNumbers.add(Integer.parseInt(editNumber[3].getText().toString()));
                    userNumbers.add(Integer.parseInt(editNumber[4].getText().toString()));
                    userNumbers.add(Integer.parseInt(editNumber[5].getText().toString()));
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Podaj wszystkie liczby", Toast.LENGTH_SHORT).show();
                }

                while(randomNumbers.size() < 6){
                    int number = random.nextInt(49) + 1;
                    if(!randomNumbers.contains(number)){
                        randomNumbers.add(number);
                    }
                }

                result1.setText(String.valueOf(randomNumbers.get(0)));
                result2.setText(String.valueOf(randomNumbers.get(1)));
                result3.setText(String.valueOf(randomNumbers.get(2)));
                result4.setText(String.valueOf(randomNumbers.get(3)));
                result5.setText(String.valueOf(randomNumbers.get(4)));
                result6.setText(String.valueOf(randomNumbers.get(5)));

                for(int i = 0; i < 6; i++){
                    if(randomNumbers.contains(userNumbers.get(i))){
                        editNumber[i].setBackgroundColor(Color.parseColor("#008000"));
                        hits.add(userNumbers.get(i));
                    }else{
                        editNumber[i].setBackgroundColor(Color.parseColor("#FF0000"));
                    }
                }

                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID);
                builder.setSmallIcon(android.R.drawable.stat_notify_chat).setContentTitle("Totolotek wygrana").setContentText("Trafione losy" + hits).setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM){
                        requestPermissions(new String[] {Manifest.permission.POST_NOTIFICATIONS}, 1);
                    }
                }
                NotificationManagerCompat.from(MainActivity.this).notify(1, builder.build());
            }
        });
    }
    private void createNotificationChannel(){
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "kanalik", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(notificationChannel);
    }
}