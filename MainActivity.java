package com.example.sehyog_ekpehel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.concurrent.TimeUnit;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class MainActivity extends AppCompatActivity
{
    public static final  String TAG="TAG";
    FirebaseAuth firebaseAuth;
    Button nextBtn;
    EditText codeEnter,phone;
    TextView textView2,resendOtpBtn,state;
    String verificationid;
    ProgressBar progressBar;
    PhoneAuthProvider.ForceResendingToken Token;
    GridView gridView;
    CountryCodePicker countryCodePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth=FirebaseAuth.getInstance();
        nextBtn= this.<Button>findViewById(R.id.nextBtn);
        textView2= this.<TextView>findViewById(R.id.textView2);
        resendOtpBtn= this.<TextView>findViewById(R.id.resendOtpBtn);
        state= this.<TextView>findViewById(R.id.state);
        codeEnter= this.<EditText>findViewById(R.id.codeEnter);
        phone= this.<EditText>findViewById(R.id.phone);
        progressBar= this.<ProgressBar>findViewById(R.id.progressBar);
        countryCodePicker= this.<CountryCodePicker>findViewById(R.id.ccp);
        nextBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String str=phone.getText().toString();
                if( str.length()==10 )
                {
                    String phonenum="+"+countryCodePicker.getSelectedCountryCode()+phone.getText().toString();
                    Log.d(TAG,"on click"+str);
                    progressBar.setVisibility(View.VISIBLE);
                    state.setText("sending otp...");
                    state.setVisibility(View.VISIBLE);
                    requestOtp(str);
                }
                else {
                    phone.setError("please enter valid number");
                }


            }


        });
}
    private void requestOtp(String str) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(str, 60L, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                progressBar.setVisibility(View.GONE);
                state.setVisibility(View.GONE);
                codeEnter.setVisibility(View.VISIBLE);
                verificationid=s;
                Token=forceResendingToken;


            }

            @Override
            public void onCodeAutoRetrievalTimeOut(String s) {
                super.onCodeAutoRetrievalTimeOut(s);
            }

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential)
            {
               /*
                Intent intent=new Intent(MainActivity.this,Verify_number.class);
                startActivity(intent);*/

            }

            @Override
            public void onVerificationFailed(FirebaseException e)
            {
                Toast.makeText(MainActivity.this,"accont not created" + e.getMessage(),Toast.LENGTH_SHORT).show();;

            }
        });
    }
 /*   public  void openDialog()
    {
       Verify_number verify_number=new Verify_number();
       verify_number.show(getSupportFragmentManager(),"Verify number");
    }*/


}
