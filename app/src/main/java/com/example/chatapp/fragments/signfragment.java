package com.example.chatapp.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.chatapp.R;
import com.example.chatapp.models.modelforprofile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Objects;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class signfragment<code> extends Fragment {

    ProgressBar bar;

    EditText email,password,name,confirm_password;
    Button register;

    String profilepic,images;
    Uri imageuri;


    int CAMERA_PERMISSION_CODE = 100;
    LinearLayout camera, gallary;
    AlertDialog alertDialog;

    FloatingActionButton editimage;
    CircleImageView profileimage;

    private static final int REQUEST_IMAGE_CAPTURE = 1,imageGalary=2;
    AlertDialog.Builder builder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_signfragment, container, false);

        register=view.findViewById(R.id.register);
        editimage=view.findViewById(R.id.floatingActionButton);
        profileimage=view.findViewById(R.id.profileimage);


        bar=view.findViewById(R.id.bar);

        name=view.findViewById(R.id.name);
        email=view.findViewById(R.id.email);
        password=view.findViewById(R.id.password);
        confirm_password=view.findViewById(R.id.confirm_password);




        editimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder = new AlertDialog.Builder(getContext());
                View viewimg = LayoutInflater.from(getContext()).inflate(R.layout.pop_for_image_selection, null);
                builder.setView(viewimg);
                alertDialog = builder.create();
                alertDialog.show();
                camera = viewimg.findViewById(R.id.camera);
                gallary = viewimg.findViewById(R.id.gallary);

                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                        updateimagecamera();
                    }
                });

                gallary.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                        updateimagegallery();
                    }
                });

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String names,emails,passwords,con_passs;
                images="";

                bar.setVisibility(View.VISIBLE);

                names=name.getText().toString();
                emails=email.getText().toString();
                passwords=password.getText().toString();
                con_passs=confirm_password.getText().toString();

                if (names.isEmpty()){
                    name.setError("Enter name");
                    return;
                }
                if (names.length()<4){
                    name.setError("name must contain atleast 4 characters");
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(emails).matches()){
                    email.setError("Enter valid eamil");
                    return;
                }
                if (passwords.isEmpty()){
                    password.setError("Enter password");
                    return;
                }
                if (passwords.length()<7){
                    password.setError("password must be greater than 7");
                    return;
                }
                if (con_passs.isEmpty()){
                    confirm_password.setError("Enter confirm password");
                    return;
                }
                if (!con_passs.equals(passwords)){
                    Toast.makeText(getContext(), "Both Passwords did not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(emails,passwords).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            if (imageuri!=null) {
                                FirebaseStorage.getInstance().getReference().child(emails).putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                            @Override
                                            public void onComplete(Task<Uri> task) {
                                                if (task.isSuccessful()) {
                                                    images = task.getResult().toString();

                                                    modelforprofile profile = new modelforprofile(names, emails, passwords, images, "busy",FirebaseAuth.getInstance().getCurrentUser().getUid());

                                                    FirebaseDatabase.getInstance().getReference().child("Users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).setValue(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            bar.setVisibility(View.INVISIBLE);
                                                            Toast.makeText(getContext(), "Profile created", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });

                                                }
                                            }
                                        });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        bar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else {
                                modelforprofile profile = new modelforprofile(names, emails, passwords, images, "busy",FirebaseAuth.getInstance().getCurrentUser().getUid());

                                FirebaseDatabase.getInstance().getReference().child("Users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).setValue(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        bar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getContext(), "Profile created", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        bar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        return view;
    }

    private void updateimagecamera() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
                try {
                    someActivityResultLaunchercamera.launch(takePictureIntent);
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            requestStoragePermission();
        }

    }

    private void updateimagegallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        //there are no request code

        someActivityResultLaunchergallary.launch(intent);

    }



    ActivityResultLauncher<Intent> someActivityResultLaunchercamera = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
//                        Intent data=result.getData();

//                        imageuri=result.getData().getData();
                        Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                        profileimage.setImageBitmap(bitmap);

                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                        String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, "Title", null);
                        imageuri=Uri.parse(path);


                    }
                }
            });


    ActivityResultLauncher<Intent> someActivityResultLaunchergallary = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes

                        imageuri=result.getData().getData();

                        Intent data=result.getData();
                        profileimage.setImageURI(data.getData());

                    }
                }
            });


    private void requestStoragePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.CAMERA)) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed for camera")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);

                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
            updateimagecamera();

        }


    }

}