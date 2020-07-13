package com.homie.psychq.main.settings;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.homie.psychq.R;
import com.homie.psychq.utils.SharedPreferences;

import java.util.Map;

public class ChangeInfoActivity extends AppCompatActivity {

    private EditText editText;
    private Button ok;

    private SharedPreferences sharedPreferences;
    String username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeinfo);

        editText=findViewById(R.id.username_et);
        ok = findViewById(R.id.ok);
        sharedPreferences=new SharedPreferences(this);


        String userInfoString =   sharedPreferences.getString(getString(R.string.user_info_map_pref),"No Info Found");
        Map<String,String> userInfomap= sharedPreferences.getMapFromString(userInfoString);

        if(userInfomap != null){
            username = userInfomap.get("Username");


            editText.setText(username);

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!editText.getText().toString().equals("") && !editText.getText().toString().equals(username)){
                        String newUsername = editText.getText().toString();

                        userInfomap.put("Username",newUsername);

                        String userInfoString = sharedPreferences.getStringFromMap(userInfomap);

                        sharedPreferences.saveStringPref(getString(R.string.user_info_map_pref),userInfoString);

                        Toast.makeText(ChangeInfoActivity.this, "Username updated", Toast.LENGTH_LONG).show();

                    }else {
                        Toast.makeText(ChangeInfoActivity.this, "Username cannot be updated", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }



    }
}
