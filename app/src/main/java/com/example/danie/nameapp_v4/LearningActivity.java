package com.example.danie.nameapp_v4;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;



public class LearningActivity extends AppCompatActivity {

    private int score = 0;
    ArrayList<String> nameArray;
    private String nameRND;
    private Uri uriRND;
    private int rand;
    
    //widgtes
    ImageView learningImageView;
    EditText learningInputEditeTxt;
    TextView learningScoreTxtView;
    Button learningCheckButton;
    ImageView imgvw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);
        nameArray = ((GlobalClass) this.getApplication()).getNames();
        score = 0;

        restAllFields();
        setActionlistner();


    }

    public void restAllFields(){
        rand = uniqueRand(rand);
        nameRND = nameArray.get(rand);
        uriRND = ((GlobalClass) getApplication()).getUri(nameRND);

        learningImageView = (ImageView) findViewById(R.id.learningImageView);
        learningImageView.setImageURI(uriRND);

        learningInputEditeTxt = (EditText) findViewById(R.id.learningInputEditeTxt);
        learningInputEditeTxt.setText("");


        learningScoreTxtView = (TextView) findViewById(R.id.learningScoreTxtView);
        learningScoreTxtView.setText(""+score);
    }

    public void checkAnswer(){
        learningInputEditeTxt = (EditText) findViewById(R.id.learningInputEditeTxt);
        String answer = learningInputEditeTxt.getText().toString();

        boolean msg_code = false;
        learningScoreTxtView = (TextView) findViewById(R.id.learningScoreTxtView);

        if(answer!=null && answer.equalsIgnoreCase(nameRND)){
            score ++;
            msg_code = true;
        }else{
            score --;
            msg_code = false;
        }

        showMsg(msg_code);
        restAllFields();
    }

    public int uniqueRand(int oldRand){

        int newRand = ThreadLocalRandom.current().nextInt(0,nameArray.size());
        System.out.println("Old: "+ oldRand + " , New:" + newRand);
        if (newRand == oldRand)
            newRand = uniqueRand(oldRand);
        return newRand;
    }

    public void setActionlistner(){
        learningCheckButton =(Button)findViewById( R.id.learningCheckButton);
        learningCheckButton.setOnClickListener((view)->{
            checkAnswer();
        });

        learningInputEditeTxt = (EditText) findViewById(R.id.learningInputEditeTxt);
        learningInputEditeTxt.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)
                checkAnswer();
            return false;
        });
    }

    public void showMsg(boolean msg_code){
        String msg="";
        Drawable d;
        if (msg_code){
            msg="Right!";
            d = getResources().getDrawable(R.drawable.right);

        }else{
            msg="Wrong!";
            d = getResources().getDrawable(R.drawable.wrong);
        }

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_container));

        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(msg);

        imgvw = (ImageView) layout.findViewById(R.id.toastIcon);
        imgvw.setImageDrawable(d);
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }

}
