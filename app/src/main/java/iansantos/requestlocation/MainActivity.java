package iansantos.requestlocation;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final int PERMISSIONS_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Solicitar Localização");
    }

    public void setPermissions(View view) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
                askPermission();
            else
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_CODE);
        } else
            Snackbar.make(findViewById(R.id.content_activity),
                    "As permissões de acesso a localização já foram concedidas anteriormente.", Snackbar.LENGTH_LONG)
                    .setAction("Dispensar", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    })
                    .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Snackbar.make(findViewById(R.id.content_activity),
                            "As permissões de acesso a localização foram concedidas!", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Dispensar", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .show();
                else
                    askPermission();
        }
    }

    public void askPermission() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para utilizar o aplicativo é necessário aceitar as permissões de localização na próxima janela.");
        builder.setCancelable(false);
        builder.setPositiveButton("Prosseguir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_CODE);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}


