package za.ac.cput.decappfinal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.OutputStream;

/**
 * Created by User on 2016/05/16.
 */
public class DecappActivity extends AppCompatActivity {

//    ImageView result;
 private static final int CAMERA_REQUEST_CODE = 1;
//
//    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
//    DatabaseReference mImageRef = mRootRef.child("image");
    private StorageReference mStorage;
    private static final int GALLERY_INTENT = 2;


    private ProgressDialog mProgressDialog;

//    private Uri fileUri;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_decapp);

            Button cancelButton = (Button) findViewById(R.id.cancelButtonDecapp);
            Button snapButton = (Button)findViewById(R.id.btnSnapFace);

            mProgressDialog = new ProgressDialog(this);

            mStorage = FirebaseStorage.getInstance().getReference();

            //check if the phone has a camera
            if(!hasCamera())
                snapButton.setEnabled(false);

            snapButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    {
                        if(view.getId()==R.id.btnSnapFace)
                        {
                            PopupMenu popupMenu = new PopupMenu(DecappActivity.this,view);

                            MenuInflater menuInflater = popupMenu.getMenuInflater();

                            popupMenu.inflate(R.menu.menu_snap);
                            popupMenu.show();

                            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {


                                    switch (item.getItemId())
                                    {
                                        case R.id.selectFromGallery:
//                                            Intent scanIntent = new Intent(DecappActivity.this,WantedActivity.class);
//                                            startActivity(scanIntent);
                                            Intent intent = new Intent(Intent.ACTION_PICK);
                                            intent.setType("image/*");
                                            startActivityForResult(intent,GALLERY_INTENT);
                                            break;

                                        case R.id.captureImage:

                                            Intent intents = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                                            startActivityForResult(intents,CAMERA_REQUEST_CODE);

                                            break;

                                     }
                                    return false;
                                }


                            });
                        }
                    }
                }
            });

        }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            mProgressDialog.setMessage("Uploading ......");
            mProgressDialog.show();
            Uri uri = data.getData();

            StorageReference filepath = mStorage.child("Photos").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    WantedActivity wantedActivity = new WantedActivity();


                    mProgressDialog.dismiss();
                    Uri downloadUri = taskSnapshot.getDownloadUrl();

                    Picasso.with(DecappActivity.this).load(downloadUri).fit().centerCrop().into(wantedActivity.imageView);

                    Toast.makeText(DecappActivity.this, "Uploading Done..", Toast.LENGTH_LONG).show();


                    Intent searchIntent = new Intent(DecappActivity.this, WantedActivity.class);
                    startActivity(searchIntent);
                }
            });
        } if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {

            Uri uri = data.getData();

            StorageReference filepath = mStorage.child("Photos").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap;
                    OutputStream output;



                    Toast.makeText(DecappActivity.this,"Image save to the SD Card",Toast.LENGTH_LONG);


                }
            });
        }
    }

    private boolean hasCamera() {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            return true;
        } else {
            return false;
        }
    }
    // THIS IS THE METHOD THAT LAUNCH THE IMAGE CAPTURE INTENT
    //this method must correspond to the onlick method in your xml file
    // with your button or something




    public void onSuspectButtonClick(View v)
            {
                if(v.getId()==R.id.btnSuspects)
                {
                    PopupMenu popupMenu = new PopupMenu(DecappActivity.this,v);

                    MenuInflater menuInflater = popupMenu.getMenuInflater();

                    popupMenu.inflate(R.menu.menu_decapp);
                    popupMenu.show();

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {


                            switch (item.getItemId())
                            {
                                case R.id.scanSuspects:
                                    Intent scanIntent = new Intent(DecappActivity.this,WantedActivity.class);
                                    startActivity(scanIntent);
                                    break;

                                case R.id.searchSuspects:
                                    Intent searchIntent = new Intent(DecappActivity.this,WantedActivity.class);
                                    startActivity(searchIntent);
                                    break;

                                case R.id.removeSuspects:
                                    Intent removeIntent = new Intent(DecappActivity.this,RemoveActivity.class);
                                    startActivity(removeIntent);
                                    break;

                                case R.id.editSuspects:
                                    Intent editIntent = new Intent(DecappActivity.this,UpdateActivity.class);
                                    startActivity(editIntent);
                                    break;


                            }
                            return false;
                        }


                    });
            }
            }
    public void onLawClick(View law)
    {
        if(law.getId()==R.id.btnLawUpdate)
        {
            Intent intent = new Intent(this,LawActivity.class);
            startActivity(intent);
        }
    }
    public void CancelDecappMethod(View can)
    {
        if(can.getId()==R.id.cancelButtonDecapp)
        {
        Intent cancelintent = new Intent(this,MainActivity.class);
        startActivity(cancelintent);

    }
}}


