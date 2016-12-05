package com.example.nastyagurskaya.lab3_app4;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;

    private Button bgButton;
    public RelativeLayout relativeLayout;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bgButton = (Button) findViewById(R.id.button1);
        bgButton.setOnClickListener(this);
        context = MainActivity.this;
        relativeLayout = (RelativeLayout)findViewById(R.id.relativelayout);
    }

    @Override
    public void onClick(View view) {
        final CharSequence[] items = {getText(R.string.red), getText(R.string.yellow),getText(R.string.green),getText(R.string.blue) ,getText(R.string.obzor)
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.message);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0: { relativeLayout.setBackgroundResource(R.color.redColor);
                        Toast.makeText(context, R.string.red, Toast.LENGTH_LONG).show();
                        break;}
                    case 1:
                    {relativeLayout.setBackgroundResource(R.color.yellowColor);
                        Toast.makeText(context, R.string.yellow,
                                Toast.LENGTH_LONG).show();
                        break;}
                    case 2:
                    {relativeLayout.setBackgroundResource(R.color.greenColor);
                        Toast.makeText(context, R.string.green, Toast.LENGTH_LONG).show();
                        break;}
                    case 3:
                    {relativeLayout.setBackgroundResource(R.color.bluColor);
                        Toast.makeText(context, R.string.blue, Toast.LENGTH_LONG).show();
                        break;}
                    case 4:
                    {
                        Toast.makeText(context, R.string.obzor, Toast.LENGTH_LONG).show();

                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
// Start the Intent
                        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);

                        break;
                    }
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                relativeLayout.setBackgroundDrawable(new BitmapDrawable(getResources(), BitmapFactory
                        .decodeFile(imgDecodableString)));


            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }
}