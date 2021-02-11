package com.anthonytepach.computerforms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class dashboard extends AppCompatActivity {


    private TextView usuario,correo;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        setUp();
        loadInfoUser();


    }



    private void loadInfoUser() {
        FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
            if (users != null) {
            Toast.makeText(this, "Hay usuario", Toast.LENGTH_SHORT).show();
            String name = users.getDisplayName();
            String email = users.getEmail();
            Uri photoUrl = users.getPhotoUrl();

            usuario.setText(name);
            correo.setText(email);
            loadImagePicasso(photoUrl);
        }
    }

    private void setUp() {
        usuario =(TextView)findViewById(R.id.user_name);
        correo=(TextView)findViewById(R.id.user_email);
        imageView=findViewById(R.id.user_photo);
    }
    private void loadImagePicasso(Uri photoUrl){
        Picasso.get()
                .load(photoUrl)
                .transform(new CropCircleTransformation())
                .into(imageView);
    }

    public void updateProfile(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName("Anthony Tepach")
                .setPhotoUri(Uri.parse("https://firebasestorage.googleapis.com/v0/b/computer-forms.appspot.com/o/IMG_20191020_195623_979.jpg?alt=media&token=5ab3845a-6233-47a1-a3a3-e6f54bf62e4d"))
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(dashboard.this, "Información Actualizada", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void signOut(View view) {
        // [START auth_sign_out]
        FirebaseAuth.getInstance().signOut();
       getLogin();

    }

    private void getLogin() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        onDestroy();
    }

    public void deleteUser() {
        // [START delete_user]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            System.out.println("borrado");
                        }
                    }
                });
        // [END delete_user]
    }

    @Override
    public void onBackPressed() {

    }
}
