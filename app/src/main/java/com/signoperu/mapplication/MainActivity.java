package com.signoperu.mapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import static android.Manifest.permission.CAMERA;

public class MainActivity extends AppCompatActivity {
    RelativeLayout l;
    ImageButton imgBtn;
    Snackbar s1,s2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgBtn= findViewById(R.id.imageButton);
        l=findViewById(R.id.milayout);
        imgBtn.setEnabled(false);
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent camara=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camara,200);
            }
        });
        s1=Snackbar.make(l,"camara lista",Snackbar.LENGTH_LONG);
        s2=Snackbar.make(l,"Sin permiso",Snackbar.LENGTH_LONG).setAction("Solicitar", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requerirPermiso();
            }
        });
        // solicitud por defecto
        if(Permiso()){
            iniciarCamara();
        }else{
            justificarSolicitud();
        }
    }

    private boolean Permiso() {
        // revisar si esta solicitud ya fue otorgado o si la version no necesita o no puede solicitar el permiso
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return true;
        }
        if(ActivityCompat.checkSelfPermission(this,CAMERA)== PackageManager.PERMISSION_GRANTED){
            return true;
        }
        return false;
    }

    private void justificarSolicitud() {
        //cuando se rechaza varias veces
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,CAMERA)){
            s2.show();
        }else{
            requerirPermiso();
        }
    }

    private void iniciarCamara() {
        s1.show();
        imgBtn.setEnabled(true);
    }


    private void requerirPermiso(){
        //muestra la solicitud para el uso de la camara
            ActivityCompat.requestPermissions(this,new String[]{CAMERA},100);
        }

    //Recibir respuesta



    //Respuesta de la solicitud de permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //recibimos la respuesta denuestra peticion si fue aceptado granted sino denied

        if(requestCode==100){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                s1.show();
                imgBtn.setEnabled(true);
            }else{
                s2.show();
                imgBtn.setEnabled(false);
            }
        }
    }

    // respuesta de la camara
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //aqui recibimos la foto y que hacer con la foto
    }
}
