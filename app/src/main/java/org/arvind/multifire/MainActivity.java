package org.arvind.multifire;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseOptions primaryInstance, secondaryInstance;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        primaryInstance = new FirebaseOptions.Builder()
                .setApplicationId("--intense-kek--")
                .setApiKey("--lulz--")
                .setDatabaseUrl("https://www.google.com")
                .build();

        secondaryInstance = new FirebaseOptions.Builder()
                .setApplicationId("--rofl--")
                .setApiKey("--api-key--")
                .setDatabaseUrl("https://facebook.com/")
                .build();

        FirebaseApp.initializeApp(this, secondaryInstance, "second");

        FirebaseApp.initializeApp(this, primaryInstance);

        final TextView secondaryStatusView = findViewById(R.id.secondary_status);
        final TextView primaryStatusView = findViewById(R.id.primary_status);
        final LinearLayout primaryLayout = findViewById(R.id.primary);
        final LinearLayout secondaryLayout = findViewById(R.id.secondary);

        Button primaryButton = findViewById(R.id.primary_button);
        primaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseApp primaryApp = FirebaseApp.getInstance();
                FirebaseDatabase primaryDB = FirebaseDatabase.getInstance(primaryApp);
                DatabaseReference primaryDR = primaryDB.getReference("/home");
                primaryDR.child("/someone").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue().toString().equals("meh")) {
                            primaryLayout.setBackgroundColor(Color.parseColor("#2196F3"));
                            secondaryLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            primaryStatusView.setText("Primary Status\nConnected: In Use");
                            secondaryStatusView.setText("Secondary Status\nConnected: UNUSED");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        Button secondaryButton = findViewById(R.id.secondary_button);
        secondaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseApp app = FirebaseApp.getInstance("second");
                FirebaseDatabase secondaryDB = FirebaseDatabase.getInstance(app);
                DatabaseReference secondaryDR = secondaryDB.getReference("/home");
                secondaryDR.child("/someone").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue().toString().equals("n00b")) {
                            secondaryLayout.setBackgroundColor(Color.parseColor("#FFC107"));
                            primaryLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            secondaryStatusView.setText("Secondary Status\nConnected: In Use");
                            primaryStatusView.setText("Primary Status\nConnected: UNUSED");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}
