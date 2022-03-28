package com.example.mobileoperatormanager;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.InputFilter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;
import 	android.net.Uri;

public class UserProfile extends AppCompatActivity {
    private TextView  username,operatorType,codeLenght,rechargeCommand, balanceCommand ;
    private EditText phoneNumber, rechargeCode;
    private Button callRecharge, callBalance ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        username = findViewById(R.id.UP_username);
        phoneNumber= findViewById(R.id.PhoneNb);
        operatorType = findViewById(R.id.OperatorType);
        codeLenght = findViewById(R.id.UP_CodeLenght);
        rechargeCode = findViewById(R.id.RechargeCode);
        balanceCommand = findViewById(R.id.BalanceCommand);
        rechargeCommand = findViewById(R.id.RechargeCommand);
        callRecharge = findViewById(R.id.Call_1);
        callBalance = findViewById(R.id.Call_2);

        // getting the username value from Mainactivity  intent
        String username_value =getIntent().getStringExtra("usernameValue");
        username.setText(username_value);
        //****************************************************************************************************************************
        //Phone number Listener
        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                 if(isOrangeNumber(phoneNumber.getText().toString())) {
                    operatorType.setText("Votre Ligne est Orange");
                    operatorType.setTextColor(Color.rgb(255,154,0));
                    codeLenght.setText(" (14 chiffres)");
                    rechargeCommand.setBackgroundColor(Color.rgb(255,154,0));
                    balanceCommand.setBackgroundColor(Color.rgb(255,154,0));
                    balanceCommand.setText("*111#");
                    callBalance.setEnabled(true);
                }
                else if (isOoredooNumber(phoneNumber.getText().toString())){
                    operatorType.setText("Votre Ligne est Ooredoo");
                    operatorType.setTextColor(Color.rgb(255,0,0));
                    codeLenght.setText(" (14 chiffres)");
                    rechargeCommand.setTextColor(Color.rgb(255,255,255));
                    rechargeCommand.setBackgroundColor(Color.rgb(255,0,0));
                    balanceCommand.setTextColor(Color.rgb(255,255,255));
                    balanceCommand.setBackgroundColor(Color.rgb(255,0,0));
                    balanceCommand.setText("*100#");
                    callBalance.setEnabled(true);
                }
                else if (isTunisieTelecomNumber(phoneNumber.getText().toString())){
                    operatorType.setText("Votre Ligne est Tunisie Telecom");
                    codeLenght.setText(" (13 chiffres)");
                    operatorType.setTextColor(Color.rgb(0,125,210));
                    rechargeCommand.setTextColor(Color.rgb(255,255,255));
                    rechargeCommand.setBackgroundColor(Color.rgb(0,125,210));
                    balanceCommand.setTextColor(Color.rgb(255,255,255));
                    balanceCommand.setBackgroundColor(Color.rgb(0,125,210));
                    balanceCommand.setText("*122#");
                    callBalance.setEnabled(true);
                }
                else{
                    //Resetting the Inputs when editing the phone number
                    operatorType.setTextColor(Color.rgb(255,255,255));
                    rechargeCommand.setTextColor(Color.rgb(0,0,0));
                    rechargeCommand.setBackground(null);
                    balanceCommand.setTextColor(Color.rgb(0,0,0));
                    balanceCommand.setBackground(null);
                    operatorType.setText("Le numéro saisie ne correspond à aucun operateur !!!");
                    codeLenght.setText("");
                    balanceCommand.setText("");
                    rechargeCommand.setText("");
                    rechargeCode.setText("");
                    callBalance.setEnabled(false);
                    callRecharge.setEnabled(false);
                 }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        //****************************************************************************************************************************
        // RechargeCode Listener
        rechargeCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (isOrangeNumber(phoneNumber.getText().toString()) && rechargeCode.getText().length()==14 ) {
                    callRecharge.setEnabled(true);
                } else if (isOoredooNumber(phoneNumber.getText().toString()) && rechargeCode.getText().length()==14 ) {
                    callRecharge.setEnabled(true);
                } else if  (isTunisieTelecomNumber(phoneNumber.getText().toString()) && rechargeCode.getText().length()==13 ){
                    callRecharge.setEnabled(true);
                }
                else callRecharge.setEnabled(false);

            }
            @Override
            public void afterTextChanged(Editable editable) {
                rechargeCommand.setText( formatRechargingCode( rechargeCode.getText().toString(), phoneNumber.getText().toString() ) );
            }
        });
        //****************************************************************************************************************************
        // CallBalance Button  Listener
        callBalance.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+balanceCommand.getText().toString()));
                startActivity(intent);
            }
        });
        //****************************************************************************************************************************
        // CallRecharge Button Listener
        callRecharge.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+rechargeCommand.getText().toString()));
                startActivity(intent);
            }
        });

    }
    //****************************************************************************************************************************
    //Boolean functions to check the Type of Operator
    private Boolean isOrangeNumber(String phonenumber){
        return ( phonenumber.startsWith("3") || phonenumber.startsWith("5") ) && (phoneNumber.length()==8);
    }

    private Boolean isOoredooNumber(String phonenumber){
        return  phonenumber.startsWith("2") && (phoneNumber.length()==8);
    }

    private Boolean isTunisieTelecomNumber(String phonenumber){
        return  phonenumber.startsWith("9") && (phoneNumber.length()==8);
    }
    //****************************************************************************************************************************
    // Formatting the RecharchingCode based on the Operator
    private String formatRechargingCode (String code ,String phone) {
        String cmnd = "";

            if (isOrangeNumber(phone)) {
                cmnd= "*100*" + code + "#";
            } else if (isOoredooNumber(phone)) {
                cmnd= "*101*" + code + "#";
            } else if (isTunisieTelecomNumber(phone)) {
                cmnd= "*123*" + code+ "#";
            }
            else cmnd="";

        return cmnd;
    }

}