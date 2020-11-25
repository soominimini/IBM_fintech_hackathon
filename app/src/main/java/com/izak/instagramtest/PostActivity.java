package com.izak.instagramtest;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.ibm.cloud.sdk.core.service.security.IamOptions;
import com.ibm.watson.personality_insights.v3.PersonalityInsights;
import com.ibm.watson.personality_insights.v3.model.Profile;
import com.ibm.watson.personality_insights.v3.model.ProfileOptions;
import com.ibm.watson.personality_insights.v3.model.Trait;
import com.izak.instagramtest.Fragments.HomeFragment2;
import com.izak.instagramtest.Fragments.MyCustomDialog;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;
import java.util.List;

import static android.util.Log.d;

public class PostActivity extends AppCompatActivity {

    private Uri mImageUri;
    String miUrlOk = "";
    private StorageTask uploadTask;
    StorageReference storageRef;
    private ToggleButton category1, category2, category3;
    ImageView close, image_added;
    TextView post;
    private String tagselect="";

    EditText description, inputmoney;
    Button mindinspectbt;

    String date, categorytab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        close = findViewById(R.id.close);
        image_added = findViewById(R.id.image_added);
        post = findViewById(R.id.post);
        description = findViewById(R.id.description);

        storageRef = FirebaseStorage.getInstance().getReference("posts");

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PostActivity.this, MainActivity.class));
                finish();
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage_10();
            }
        });


        CropImage.activity()
                .setAspectRatio(1,1)
                .start(PostActivity.this);

        DatePicker datePicker = (DatePicker)findViewById(R.id.post_datepicker);
        datePicker.updateDate(2019, 7, 30);
        int year = 2019;
        int month = 7;
        int day = 30;
        date = year + "년" + month + "월" + day + "일";

        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date = year + "년" + monthOfYear + "월" + dayOfMonth + "일";
//                Toast.makeText(PostActivity.this, date, Toast.LENGTH_SHORT).show();
            }
        });
        inputmoney = findViewById(R.id.activity_post_textinput_money);
        category1 = findViewById(R.id.activity_post_category1_text);
//        category1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(category1.isChecked()){
//                    Log.d("버튼확인1","체크되어있어요");
//                }
//                category1.setTextColor(0xFF1734C4);
//                category2.setTextColor(0xFD271E1E);
//                category3.setTextColor(0xFD271E1E);
//            }
//        });
        category2 = findViewById(R.id.activity_post_category2_text);
//        category2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                category1.setTextColor(0xFD271E1E);
//                category2.setTextColor(0xFF1734C4);
//                category3.setTextColor(0xFD271E1E);
//            }
//        });
        category3 = findViewById(R.id.activity_post_category3_text);
//        category3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                category1.setTextColor(0xFD271E1E);
//                category2.setTextColor(0xFD271E1E);
//                category3.setTextColor(0xFF1734C4);            }
//        });
        SetListener();

    }

    private void SetListener() {
        View.OnClickListener Listener = new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                switch (view.getId()) {
                    case R.id.activity_post_category1_text:
                        break;
                    case R.id.activity_post_category2_text:
                        break;
                    case R.id.activity_post_category3_text:
                        break;
                }
                if(category1.isChecked()){
                    category1.setTextColor(0xFF1734C4);
                    tagselect = category1.getTextOn().toString();
                } else{
                    category1.setTextColor(0xFD271E1E);
                    tagselect = "";
                }
                if(category2.isChecked()){
                    category2.setTextColor(0xFF1734C4);
                    tagselect = tagselect.concat("," + category2.getTextOn().toString());
                } else{
                    category2.setTextColor(0xFD271E1E);
                }
                if(category3.isChecked()){
                    category3.setTextColor(0xFF1734C4);
                    tagselect = tagselect.concat("," + category3.getTextOn().toString());
                } else{
                    category3.setTextColor(0xFD271E1E);
                }
                Log.d("11111",tagselect);

            }
        };
        category1.setOnClickListener(Listener);
        category2.setOnClickListener(Listener);
        category3.setOnClickListener(Listener);
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadImage_10(){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Posting");
        pd.show();
        if (mImageUri != null){
            final StorageReference fileReference = storageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            uploadTask = fileReference.putFile(mImageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        miUrlOk = downloadUri.toString();

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");

                        String postid = reference.push().getKey();

                        long timeend = System.currentTimeMillis();

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("postid", postid);
                        hashMap.put("postimage", miUrlOk);
                        hashMap.put("description", description.getText().toString());
                        hashMap.put("publisher", FirebaseAuth.getInstance().getCurrentUser().getUid());
                        hashMap.put("timeend", date);
                        hashMap.put("inputmoney", inputmoney.getText().toString());
                        hashMap.put("nowmoney", "0");
                        hashMap.put("tag", tagselect);


                        reference.child(postid).setValue(hashMap);

                        pd.dismiss();

                        startActivity(new Intent(PostActivity.this, MainActivity.class));
                        finish();

                    } else {
                        Toast.makeText(PostActivity.this, "상품등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(PostActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(PostActivity.this, "대표사진을 꼭! 선택해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            mImageUri = result.getUri();

            image_added.setImageURI(mImageUri);
        } else {
            Toast.makeText(this, "Something gone wrong!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PostActivity.this, MainActivity.class));
            finish();
        }
    }

}
