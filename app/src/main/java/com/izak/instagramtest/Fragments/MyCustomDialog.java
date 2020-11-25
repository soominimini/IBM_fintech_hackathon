package com.izak.instagramtest.Fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.module.AppGlideModule;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.ibm.cloud.sdk.core.service.security.IamOptions;
import com.ibm.watson.personality_insights.v3.PersonalityInsights;
import com.ibm.watson.personality_insights.v3.model.Profile;
import com.ibm.watson.personality_insights.v3.model.ProfileOptions;
import com.ibm.watson.personality_insights.v3.model.Trait;
import com.izak.instagramtest.R;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class MyCustomDialog extends DialogFragment {



    private static final String TAG = "MyCustomDialog";

    public interface OnInputSelected{

        void sendInput(String input);
    }
    private OnInputSelected mOnInputSelected;

    //widgets
    private EditText mInput;
    private TextView mActionOk, mActionCancel, heading;
    private ImageButton imageView1, imageView2, imageView3, imageView4, imageView5;
    private int processcount;
    private String watson_request_string1 = "", watson_match_step_1 ="", watson_match_step_2 ="", watson_match_step_3 = "", watson_match_step_4 = "", watson_match_step_5 = "";
    private String category1, category2,category3, category4, category5;

    //    StorageReference storageReference;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mind_dialog_up, container, false);
        mActionOk = view.findViewById(R.id.action_ok);
        mActionCancel = view.findViewById(R.id.action_cancel);
        mInput = view.findViewById(R.id.input);
        imageView1 = view.findViewById(R.id.mind_dialog_image1);
        imageView2 = view.findViewById(R.id.mind_dialog_image2);
        imageView3 = view.findViewById(R.id.mind_dialog_image3);
        imageView4 = view.findViewById(R.id.mind_dialog_image4);
        imageView5 = view.findViewById(R.id.mind_dialog_image5);
        heading = view.findViewById(R.id.heading);
        processcount = 1;
        changethemind(1);

//        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("mindcheckimage");
//        String checkURL = storageReference.child("mind02_1.png").getDownloadUrl().toString();
//        Log.d("121231231231233",checkURL);





        this.SetListener();



//                switch (getId()){
//                    case R.id.mind_dialog_image1:
//                        watson_request_string1 = watson_request_string1.concat(watson_match_step_1);
//                        break;
//                    case R.id.mind_dialog_image2:
//                        watson_request_string1 = watson_request_string1 + watson_match_step_2;
//                        break;
//                    case R.id.mind_dialog_image3:
//                        watson_request_string1 = watson_request_string1 + watson_match_step_3;
//                        break;
//                    case R.id.mind_dialog_image4;
//                        watson_request_string1 = watson_request_string1 + watson_match_step_4;
//                        break;
//                    case R.id.mind_dialog_image5:
//                        watson_request_string1 = watson_request_string1 + watson_match_step_5;
//                        break;
//
//                }
//
//                processcount += 1;
//                Log.d("cccccccccccccccccccc", watson_request_string1);
//
//                if(processcount == 4){
//
////                    mOnInputSelected.sendInput(watson_request_string1);
//
//                    getDialog().dismiss();
//                }
//                Log.d(TAG, String.valueOf(processcount));
//                changethemind(processcount);
//
//            }
//        };
//        imageView1.setOnClickListener(onClickListener);
//        imageView2.setOnClickListener(onClickListener);
//        imageView3.setOnClickListener(onClickListener);
//        imageView4.setOnClickListener(onClickListener);
//        imageView5.setOnClickListener(onClickListener);
        changethemind(1);


//        storageReference = FirebaseStorage.getInstance().getReference().child("mindcheckimage");
//        String filename = "mind1.png";
//        storageReference.child(filename).getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
//            @Override
//            public void onSuccess(StorageMetadata storageMetadata) {
//                Log.d(TAG, "여기까지는 됫군");
//            }
//        });
//        String image1 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind1.png?alt=media&token=1850a5ef-48d8-40e7-92c2-bace5c90fd16";
//        Log.d(TAG, image1.toString());
//        Glide.with(this).using(new FirebaseImageLoader()).load(imagesRef).into(imageView);
//        Glide.with(this).load(image1).into(imageView1);
//        Glide.with(this).using(new FirebaseImageLoader()).load(imagesRef).into(imageView);
        //        GlideApp.with(this)
//                .load(imagesRef).into(imageView);
        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: closing dialog");
                getDialog().dismiss();
            }
        });

        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: capturing input.");

                String input = mInput.getText().toString();
                Log.d(TAG, "onClick: " + input);

                if(!input.equals("")){
                    Log.d(TAG, "onClick: " + input);
//Class<SearchFragment> searchFragment = SearchFragment.class;
//searchFragment.getConstructors()

//                    //Easiest way: just set the value.
//                    SearchFragment fragment = (SearchFragment) getActivity().getFragmentManager().findFragmentByTag("SearchFragment");
//                    fragment.search_bar.setText(input);

//                    Class<SearchFragment> searchFragment = SearchFragment.class;
//                    searchFragment.get();
                    mOnInputSelected.sendInput(input);
                }


                getDialog().dismiss();
            }
        });

        return view;
    }
// matching API result with crowd funding products (specifically, categories of products)
    private void SetListener() {
        View.OnClickListener Listener = new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                switch (view.getId()) {
                    case R.id.mind_dialog_image1:
                        watson_request_string1 = watson_request_string1.concat(watson_match_step_1);
                        break;
                    case R.id.mind_dialog_image2:
                        watson_request_string1 = watson_request_string1.concat(watson_match_step_2);
                        break;
                    case R.id.mind_dialog_image3:
                        watson_request_string1 = watson_request_string1.concat(watson_match_step_3);
                        break;
                    case R.id.mind_dialog_image4:
                        watson_request_string1 = watson_request_string1.concat(watson_match_step_4);
                        break;
                    case R.id.mind_dialog_image5:
                        watson_request_string1 = watson_request_string1.concat(watson_match_step_5);
                        break;
                }

                processcount += 1;
                if(processcount == 16){

                    IamOptions options = new IamOptions.Builder()
                            .apiKey("9aPTQUgHwV74s4gjD7zC4ZsCppop7j1-gNheGmW8S6UA")
                            .build();
                    final PersonalityInsights service = new PersonalityInsights("2016-10-19", options); //version
                    service.setEndPoint("https://gateway.watsonplatform.net/personality-insights/api");

                    // DB에서 가져온 string 예제
                    final String temp = watson_request_string1;
//                    final String temp = "여유로운, 느긋한,높은 성취의지, 목표지향적, 계획적인,신중한, 근면한, 계획적인,우직한, 한 우물은 파는, 교양있는,현실적인, 합리적인,창의적인, 지적인, 모험적인,사교적인, 대화를 즐기는, 따뜻한,외향적인, 활력적인, 사교적인,외향적인,활동적인,차분한, 느긋한, 세심한,스트레스를 쉽게 받는, 회복력 있는,불안한, 사려 깊은, 스트레스를 쉽게 받는,자율적인, 속을 드러내지 않는,신뢰로운, 이타적인,무관심한, 회의적인";

                    Thread workingThread = new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            //최소 100단어 이상 들어가야 하기 때문에 for문 돌립니다
                            //                the string itself has too small length of data( not enough data), so I just make it expanded by multiplying it
                            String text = temp;
                            for(int i=0; i<5; i++){
                                if(i<4)text+=", ";
                                text+=temp;
                            }
                            ProfileOptions options_profile = new ProfileOptions.Builder()
                                    .text(text)
                                    .contentLanguage("ko") //language 선택
                                    .consumptionPreferences(true) //이거는 사용 안하셔도 돼요(소비성향)
                                    .build();

                            Profile profile = service.profile(options_profile).execute().getResult();
                            List<Trait> personality = profile.getPersonality();


                            // openess 에서는 total percentile 값이 아니라 하위항목인 emotionalty의 percentile을 사용할 거예요
                            double emotion_p = personality.get(0).getChildren().get(2).getPercentile();

                            if(emotion_p<=0.55)category1 = "미술";
                            else if(emotion_p>0.55 && emotion_p<=0.7)category1 = "홈리빙";
                            else if(emotion_p>0.7 && emotion_p<=0.85)category1 = "교육";
                            else category1= "출판";


                            //cons 에서도 total percentile이 아니라 하위 항목인 self-discipline 값을 사용합니다
                            double discipline_p  = personality.get(1).getChildren().get(4).getPercentile();

                            if(discipline_p<=0.74) category2 = "여행";
                            else if(discipline_p>0.74 && discipline_p<=0.80)category2 = "사진";
                            else if(discipline_p>0.80 && discipline_p<=0.85) category2 = "출판";
                            else category2 = "캠페인";



                            //extraversion도 Activity level의 값 만을 사용합니다
                            double activity_p = personality.get(2).getChildren().get(0).getPercentile();

                            if(activity_p<=0.84) category3 = "디자인 소품";
                            else if(activity_p>0.84 && activity_p<=0.88)category3 = "홈리빙";
                            else if(activity_p>0.88 && activity_p<=0.93)category3 = "모임";
                            else category3 = "공연";


                            //Agreeableness는 total percentile을 사용합니다,
                            double agreeable_p = personality.get(3).getPercentile();

                            if(agreeable_p<=0.02) category4 = "음악";
                            else if(agreeable_p>0.02 && agreeable_p<=0.04)category4 = "사진";
                            else if(agreeable_p>0.04 && agreeable_p<=0.06)category4 = "모임";
                            else category4 = "기부";

                            //Emotional range도 total percentile을 사용합니다.
                            double neuro_p = personality.get(4).getPercentile();

                            if(neuro_p<=0.55) category5= "출판";
                            else if(neuro_p>0.55 && neuro_p<=0.66)category5= "미술";
                            else if(neuro_p>0.66 && neuro_p<=0.77)category5= "기부";
                            else category5= "캠페인";

                        }
                    };
                    workingThread.start();
//                    .start();

                    try {
                        workingThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    mOnInputSelected.sendInput(watson_request_string1);ksmimkFirebasAutss = nswindi
// FirebassAutj = Firebase Auti .getinstance().getCurrentuser().child();fiiisninsidiFirebase.Authg.getCurrentUser
                    category5 = category1.concat("," + category2).concat("," + category3).concat("," + category4).concat("," + category5);

                    mOnInputSelected.sendInput(category5);

                    Log.d("1111111",category5);
//                    Log.d("2222222",category2);
//                    Log.d("3333333",category3);
//                    Log.d("4444444",category4);
//                    Log.d("5555555",category5);
                    getDialog().dismiss();
                }
                changethemind(processcount);
            }
        };

        imageView1.setOnClickListener(Listener);
        imageView2.setOnClickListener(Listener);
        imageView3.setOnClickListener(Listener);
        imageView4.setOnClickListener(Listener);
        imageView5.setOnClickListener(Listener);
    }

    private void changethemind(int count) {
        String image1 = "",image2 = "",image3 = "",image4 = "",image5 = "",texttitle = "";

        switch (count){
            case 1:
                texttitle = "언제 청소를 시작하시겠습니까?\n(1/15)";
                image1 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind1.png?alt=media&token=1850a5ef-48d8-40e7-92c2-bace5c90fd16";
                image2 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind2.png?alt=media&token=3d27bad6-1146-41b5-a39b-533e5136cb4d";
                image3 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind3.png?alt=media&token=1dc0a1ca-560c-44c9-aa5f-31ba6a728f20";
                image4 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind4.png?alt=media&token=f1750126-119a-4536-b3aa-f9e6fb177776";
                image5 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind5.png?alt=media&token=fefebe04-b00e-47fb-aa89-e437e193cafa";
                watson_match_step_1 = "부지런한, 성실한, 정돈된, ";
                watson_match_step_2 = "부지런한, 성실한, 정돈된, ";
                watson_match_step_3 = "여유로운, 느긋한, ";
                watson_match_step_4 = "여유로운, 느긋한, ";
                watson_match_step_5 = "여유로운, 느긋한, 게으른, ";
                break;
            case 2:
                texttitle = "아침 휴식시간, 당신은 어떻게 시간을 보내나요?\n(2/15)";
                image1 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind02_1.png?alt=media&token=bf4cbe81-f9ed-4df0-b214-58eb10fcdf14";
                image2 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind02_2.png?alt=media&token=e2e3030e-50ae-4ddc-a21f-d3be93532f90";
                image3 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind02_3.png?alt=media&token=632ffe93-d58e-42d6-b4c6-ffeb22da48c7";
                image4 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind02_4.png?alt=media&token=be845928-601c-49bb-9a06-0359355468ee";
                image5 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind02_5.png?alt=media&token=5a82e09e-8a44-42d6-bca4-dc73e739c676";
                watson_match_step_1 = "자유로운, 대담한, 여유로운, 느긋한, ";
                watson_match_step_2 = "자유로운, 여유로운, 느긋한, ";
                watson_match_step_3 = "성취의지, 목표지향적, ";
                watson_match_step_4 = "높은 성취의지, 목표지향적, 활동적인, ";
                watson_match_step_5 = "높은 성취의지, 목표지향적, 계획적인, ";
                break;
            case 3:
                texttitle = "일을 시작하기 전에 얼마나 많은 계획을 세우나요?\n(3/15)";
                image1 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind03_1.png?alt=media&token=8090c318-540d-498c-860b-8bb98e347afd";
                image2 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind03_2.png?alt=media&token=7b9e8cf2-4939-40f3-be64-71c822131a14";
                image3 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind03_3.png?alt=media&token=636662c9-470f-4e91-8b47-df31edc07f63";
                image4 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind03_4.png?alt=media&token=7e776607-e20a-4157-b71d-10472e26da4f";
                image5 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind03_5.png?alt=media&token=f62cfe02-7ba3-4c3c-b7c4-872bce42f91c";
                watson_match_step_1 = "대담한, 여유로운, 무계획적인, ";
                watson_match_step_2 = "대담한, 여유로운, ";
                watson_match_step_3 = "신중한, 근면한, 계획적인, ";
                watson_match_step_4 = "신중한, 근면한, 계획적인, ";
                watson_match_step_5 = "신중한, 근면한, 계획적인, ";
                break;
            case 4:
                texttitle = "당신의 상식 및 어휘 수준은?\n(4/15)";
                image1 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind04_1.png?alt=media&token=fb00f605-0dfd-4ede-9ec5-75d82b2520b9";
                image2 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind04_2.png?alt=media&token=26fbfba4-998f-45fc-b55b-399a7aad94e8";
                image3 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind04_3.png?alt=media&token=c0b0db2f-cfb2-4f0e-8012-82b1534b2181";
                image4 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind04_4.png?alt=media&token=eed05f32-5990-42d5-a4eb-a7c5dd6e6a2e";
                image5 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind04_5.png?alt=media&token=3b65284b-a8a5-4085-9e99-2b2464da9dce";
                watson_match_step_1 = "우직한, 강직한, 한 우물을 파는, ";
                watson_match_step_2 = "우직한, 강직한, 한 우물을 파는, ";
                watson_match_step_3 = "우직한, 한 우물은 파는, 교양있는, ";
                watson_match_step_4 = "지적인, 교양 있는, 진보적인, 호기심이 많은, ";
                watson_match_step_5 = "지적인, 교양 있는, 진보적인, 호기심이 많은, ";
                break;
            case 5:
                texttitle = "아래 그림 중 나의 상상력 수준과 비슷한 것은?\n(5/15)";
                image1 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind05_1.png?alt=media&token=47bd9c68-ec77-4364-8a96-b3d2209c2ff8";
                image2 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind05_2.png?alt=media&token=e3e5636f-5bd0-40cf-b2bf-172a25e10ac3";
                image3 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind05_3.png?alt=media&token=42855c7e-97e4-4be9-bd3c-ffed04eab204";
                image4 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind05_4.png?alt=media&token=7aa7948b-00ec-4964-9e93-abffcd3e0f56";
                image5 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind05_5.png?alt=media&token=631b351c-a667-44d7-8327-57ca112af1c2";
                watson_match_step_1 = "현실적인, 합리적인, ";
                watson_match_step_2 = "현실적인, 합리적인, ";
                watson_match_step_3 = "현실적인, 이성적인, ";
                watson_match_step_4 = "창의적인, 진보적인, 예술성, ";
                watson_match_step_5 = "창의적인, 진보적인, 예술성, ";
                break;
            case 6:
                texttitle = "이들 중, 당신처럼 생각하는 사람은 누구인가요?\n(6/15)";
                image1 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind06_1.png?alt=media&token=0a361bba-9b38-4d6a-88de-9a657266dbc4";
                image2 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind06_2.png?alt=media&token=37af272d-2357-4fca-80dc-8121f0aefeb9";
                image3 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind06_3.png?alt=media&token=8a865bf9-182f-4b52-af03-df2bef5d3b21";
                image4 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind06_4.png?alt=media&token=c4731ddd-921c-47d5-9aca-5c3a990d4470";
                image5 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind06_5.png?alt=media&token=9a3a40ec-095b-49b2-866c-8d691ec3527f";
                watson_match_step_1 = "강직한, 실용적인, 합리적인, ";
                watson_match_step_2 = "신중한, 조심스러운, 논리적인, ";
                watson_match_step_3 = "체계적인, 사려깊은, 신중한, ";
                watson_match_step_4 = "창의적인, 지적인, 모험적인, ";
                watson_match_step_5 = "창의적인, 지적인, 모험적인, ";
                break;
            case 7:
                texttitle = "사교 모임에서 당신의 모습은 어떤가요?\n(7/15)";
                image1 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind07_1.png?alt=media&token=a1943fcd-f7e4-46b9-996b-a691aaf978a6";
                image2 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind07_2.png?alt=media&token=8bf6558c-ef6b-454c-839b-909c94de439a";
                image3 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind07_3.png?alt=media&token=dad4a9cf-7c47-4b80-8472-27ebdb66792b";
                image4 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind07_4.png?alt=media&token=fcb19a9f-adc1-4425-8aec-63bb9708c662";
                image5 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind07_5.png?alt=media&token=8725a3eb-ebd9-41db-8fc3-2b1e40c6ab07";
                watson_match_step_1 = "사교적인, 활력적인, 대화를 즐기는, ";
                watson_match_step_2 = "사교적인, 대화를 즐기는, 따뜻한, ";
                watson_match_step_3 = "비사교적인, 지적인, 주장적인, ";
                watson_match_step_4 = "비사교적인, 고독한, 내향적인, ";
                watson_match_step_5 = "비사교적인, 사려깊은, 진중한, 내향적인, ";
                break;
            case 8:
                texttitle = "새로운 사람들과의 만남 이후 당신의 에너지 수준은?\n(8/15)";
                image1 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind08_1.png?alt=media&token=fbf8ef87-1cd5-40a0-bb20-30289679b114";
                image2 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind08_2.png?alt=media&token=cf55d8b0-4891-428f-9af1-c7ed74f6ae3f";
                image3 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind08_3.png?alt=media&token=4f67a88a-6b14-403a-89fc-a9e9bc4748c9";
                image4 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind08_4.png?alt=media&token=be459121-0e19-440f-a69b-57ce8158fc45";
                image5 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind08_5.png?alt=media&token=74252a43-b191-4557-91ff-023d6c29ac71";
                watson_match_step_1 = "내향적인, 보수적인, ";
                watson_match_step_2 = "내향적인, 보수적인, ";
                watson_match_step_3 = "소수의 친밀한 관계를 중시하는, ";
                watson_match_step_4 = "외향적인, 활력적인, 사교적인, ";
                watson_match_step_5 = "외향적인, 활력적인, 사교적인, ";
                break;
            case 9:
                texttitle = "홀로 집에 있는 시간동안 어느 정도의 편안함을 느끼나요?\n(9/15)";
                image1 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind09_1.png?alt=media&token=7845fcb2-fc24-4cd2-a666-f7aabcbe0cb9";
                image2 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind09_2.png?alt=media&token=258e21f1-7d0d-4610-bee9-fa73c41b36a7";
                image3 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind09_3.png?alt=media&token=d83c89b3-960c-411c-b409-dfed4b3c3e61";
                image4 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind09_4.png?alt=media&token=a2feb6a4-780e-4edd-b725-a1f99378c46b";
                image5 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind09_5.png?alt=media&token=3c40d3ce-8b6b-45b5-a0d4-42c94fe9c574";
                watson_match_step_1 = "외향적인,활동적인, ";
                watson_match_step_2 = "외향적인,활동적인, ";
                watson_match_step_3 = "속을 드러내지 않는, ";
                watson_match_step_4 = "내향적인, 개인주의적인, ";
                watson_match_step_5 = "내향적인, 개인주의적인, ";
                break;
            case 10:
                texttitle = "당신이 느끼는 감정의 강도와 가장 가까운 것은?\n(10/15)";
                image1 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind10_1.png?alt=media&token=6be5882e-e83f-47ce-897f-e7050b8f6430";
                image2 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind10_2.png?alt=media&token=b1e9cd4b-d60a-4b2e-ab32-09b14549d7c0";
                image3 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind10_3.png?alt=media&token=7115142a-a060-4c04-8548-4dc796159179";
                image4 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind10_4.png?alt=media&token=f06a8e19-3bf9-400f-9e48-a313a84f2268";
                image5 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind10_5.png?alt=media&token=34116194-7975-43de-88e0-2ba1202c1637";
                watson_match_step_1 = "차분한, 느긋한, 세심한, ";
                watson_match_step_2 = "차분한, 느긋한, 세심한, ";
                watson_match_step_3 = "생각이 많은, ";
                watson_match_step_4 = "불안한, 걱정이 많은,스트레스를 쉽게 받는, ";
                watson_match_step_5 = "불안한, 충동적인, 스트레스를 쉽게 받는, ";
                break;
            case 11:
                texttitle = "당신의 정서는 어느 정도로 안정되어 있나요?\n(11/15)";
                image1 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind11_1.png?alt=media&token=f34230fb-81e0-4fb6-885d-6e8af4e35d7b";
                image2 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind11_2.png?alt=media&token=17ae4a05-f0ec-4d7d-bdd0-5d6ae70dc34c";
                image3 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind11_3.png?alt=media&token=7936b38a-0027-419f-be06-e3d8d7b5cc88";
                image4 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind11_4.png?alt=media&token=dde220a4-d81a-4eea-8115-8d01ff1f401b";
                image5 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind11_5.png?alt=media&token=54915005-97eb-47d5-b998-ee2808e09ef2";
                watson_match_step_1 = "불안한, 스트레스를 쉽게 받는, ";
                watson_match_step_2 = "불안한, 스트레스를 쉽게 받는, ";
                watson_match_step_3 = "스트레스를 쉽게 받는, 회복력 있는, ";
                watson_match_step_4 = "회복력 있는, 차분한, ";
                watson_match_step_5 = "회복력 있는, 차분한, 긍정적인, ";
                break;
            case 12:
                texttitle = "당신의 결점을 나열한다면?\n(12/15)";
                image1 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind12_1.png?alt=media&token=06a5fb96-3c25-48de-9308-ef3468651e32";
                image2 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind12_2.png?alt=media&token=697d5a3c-14b1-4af5-879f-a5a32e72f453";
                image3 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind12_3.png?alt=media&token=5649ad52-33e9-4547-a012-809473423942";
                image4 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind12_4.png?alt=media&token=f80e6611-58a5-4b1f-aeb5-71f5c87a237a";
                image5 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind12_5.png?alt=media&token=1573d4da-d37b-4993-acd3-1960a3b3b6c4";
                watson_match_step_1 = "속을 드러내지 않는, 회복력이 있는, ";
                watson_match_step_2 = "속을 드러내지 않는, 회복력이 있는, ";
                watson_match_step_3 = "사려 깊은, 생각이 많은, ";
                watson_match_step_4 = "스트레스를 쉽게 받는, 사려 깊은, ";
                watson_match_step_5 = "불안한, 사려 깊은, 스트레스를 쉽게 받는, ";
                break;
            case 13:
                texttitle = "당신은 어떤 유형의 리더입니까?\n(13/15)";
                image1 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind13_1.png?alt=media&token=28f75596-18e4-4490-8fcb-e6137dd13329";
                image2 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind13_2.png?alt=media&token=10a9b703-8476-47f7-a39e-dd1056d44e79";
                image3 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind13_3.png?alt=media&token=2f978fc4-0b13-4604-955e-d896c8ee6733";
                image4 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind13_4.png?alt=media&token=87f0dd03-5c38-4ee0-a277-12f1df499a6b";
                image5 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind13_5.png?alt=media&token=96ea75a9-87a3-4f71-b3d1-8a4297fa0559";
                watson_match_step_1 = "신뢰로운, 이타적인, ";
                watson_match_step_2 = "신뢰로운, 공감 능력이 높은, 사려 깊은, ";
                watson_match_step_3 = "자율적인, 속을 드러내지 않는, ";
                watson_match_step_4 = "현실적인, 논리적인, 야망있는, 신중한, ";
                watson_match_step_5 = "현실적인, 논리적인, 야망있는, ";
                break;
            case 14:
                texttitle = "일반적으로 사람들은 서로를 어떻게 대하나요?\n(14/15)";
                image1 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind14_1.png?alt=media&token=fa34e748-f1b3-4ec1-9abf-186068a95dd5";
                image2 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind14_2.png?alt=media&token=67cb3051-4029-4721-9910-390540b2b156";
                image3 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind14_3.png?alt=media&token=cd9cbfb6-90c0-4151-a8b9-0286dc137be7";
                image4 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind14_4.png?alt=media&token=244e67ca-bdc1-4b98-9078-529c61e3a687";
                image5 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind14_5.png?alt=media&token=11a993e3-6669-48a7-a0f3-d145b41c0938";
                watson_match_step_1 = "신뢰로운, 이타적인, ";
                watson_match_step_2 = "신뢰로운, 이타적인, ";
                watson_match_step_3 = "겸손한, ";
                watson_match_step_4 = "회의적인, 현실적인, ";
                watson_match_step_5 = "회의적인, 자기 주장이 강한, ";
                break;
            case 15:
                texttitle = "타인의 성공에 당신은 어떻게 반응하나요?\n(15/15)";
                image1 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind15_1.png?alt=media&token=2a2c086c-8cef-461d-9d85-2c3028d2de43";
                image2 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind15_2.png?alt=media&token=1b85e43d-eec1-4b9e-a4a2-aa8ecb9aacf9";
                image3 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind15_3.png?alt=media&token=b1a330f0-2dd5-4091-a413-4fcf40922399";
                image4 = "https://firebasestorawatson_match_step_1ge.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind15_4.png?alt=media&token=6478301f-c6c5-460b-981c-aeecf30dd598";
                image5 = "https://firebasestorage.googleapis.com/v0/b/izakinsta.appspot.com/o/mindcheckimage%2Fmind15_5.png?alt=media&token=8c112baf-de66-4055-8d44-ca4e97f5c550";
                watson_match_step_1 = "무관심한, 회의적인, ";
                watson_match_step_2 = "무관심한, 회의적인, ";
                watson_match_step_3 = "현실적인, 진실한, ";
                watson_match_step_4 = "사려깊은, 신뢰로운, ";
                watson_match_step_5 = "공감능력이 높은, 신뢰로운, ";
                break;
        }




//        Log.d(TAG, image1.toString());
//        Glide.with(this).using(new FirebaseImageLoader()).load(imagesRef).into(imageView);
//        String checkURL = FirebaseStorage.getInstance().getReference().child("mindcheckimage").child("mind02_1.png").getDownloadUrl().toString();
//
//        Log.d("121231231231233",checkURL);
        Glide.with(MyCustomDialog.this).load(image1).into(imageView1);
        Glide.with(MyCustomDialog.this).load(image2).into(imageView2);
        Glide.with(MyCustomDialog.this).load(image3).into(imageView3);
        Glide.with(MyCustomDialog.this).load(image4).into(imageView4);
        Glide.with(MyCustomDialog.this).load(image5).into(imageView5);
        heading.setText(texttitle);
//        Glide.with(this);
//        (Glide.with(this))
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            Log.e(TAG, "onAttach success" );

            mOnInputSelected = (OnInputSelected) getTargetFragment();
//            String savecheck = getTargetFragment().toString();
//            Log.e(TAG, "onAttach success" +savecheck );

        }catch (ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException : " + e.getMessage() );
        }
    }


    @GlideModule
    public class MyAppGlideModule extends AppGlideModule {

        @Override
        public void registerComponents(Context context, Glide glide, Registry registry) {
            // Register FirebaseImageLoader to handle StorageReference
            registry.append(StorageReference.class, InputStream.class,
                    new FirebaseImageLoader.Factory());
        }
    }
}














