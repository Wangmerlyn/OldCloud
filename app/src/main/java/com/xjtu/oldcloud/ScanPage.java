package com.xjtu.oldcloud;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScanPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScanPage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_SCAN_CODE = 100;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView imageView_Result;
    Button button_Scan,button_LoginShit;
    TextView textView_Code;
    Button button;
    MyViewModel myViewModel;
    public ScanPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScanPage.
     */
    // TODO: Rename and change types and number of parameters
    public static ScanPage newInstance(String param1, String param2) {
        ScanPage fragment = new ScanPage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scan_page, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        //myViewModel = ViewModelProviders.of(this).get(key,MyViewModel.class);
        button_Scan = getActivity().findViewById(R.id.button_Scan);
        imageView_Result = getActivity().findViewById(R.id.imageView_Result);
        textView_Code = getActivity().findViewById(R.id.textView_Code);
        Scan_Code();

        button_LoginShit = getActivity().findViewById(R.id.button_LoginShit);
        TryLogin();
    }

    void Scan_Code(){
        button_Scan.setOnClickListener(v -> {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                if(getActivity().checkSelfPermission(Manifest.permission.CAMERA)==
                        PackageManager.PERMISSION_DENIED||
                        getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==
                                PackageManager.PERMISSION_DENIED){
                    String[] Permission={Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    requestPermissions(Permission,10);
                }
            }
            Scan_Action();

        });
    }
    void Scan_Action(){
        Intent intent = new Intent(getContext(), CaptureActivity.class);
        ZxingConfig zxingConfig=new ZxingConfig();
        zxingConfig.setShake(true);
        zxingConfig.setPlayBeep(true);
        zxingConfig.setShowbottomLayout(true);
        intent.putExtra(Constant.INTENT_ZXING_CONFIG,zxingConfig);
        startActivityForResult(intent,REQUEST_SCAN_CODE);
        //MyViewModel myViewModel = ViewModelProviders.of(getActivity()).get(MyViewModel.class);
        textView_Code.setText(myViewModel.Code);
        //Toast.makeText(getContext(),myViewModel.Code+"THISIS THE SHIT",Toast.LENGTH_LONG).show();
    }

    void TryLogin(){
        button_LoginShit.setOnClickListener(v->{
            //MyViewModel myViewModel = ViewModelProviders.of(getActivity()).get(MyViewModel.class);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            Date date=null;
            Toast.makeText(getContext(),myViewModel.Code,Toast.LENGTH_LONG).show();
            try {
                date = simpleDateFormat.parse(myViewModel.Code.substring(0,15));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(date!=null){
                Date date_now = new Date();
                if(Math.abs(date_now.getTime()-date.getTime())<=15){
                    int index = myViewModel.Code.indexOf('+');
                    myViewModel.ScannedName = myViewModel.Code.substring(15,index);
                    myViewModel.ScannedPassword=myViewModel.Code.substring(index+1);
                    Toast.makeText(getContext(),myViewModel.ScannedName,Toast.LENGTH_LONG).show();

                    PackageManager packageManager = getActivity().getPackageManager();
                    Intent intent = packageManager.getLaunchIntentForPackage("com.redfinger.app");
                    startActivity(intent);

                }
                else{
                    Toast.makeText(getContext(),"Code Overdue!!!",Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(getContext(),"Scan Code Failed!!!",Toast.LENGTH_LONG).show();
            }

        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            if(data!=null){
                String code = data.getStringExtra(Constant.CODED_CONTENT);
                //MyViewModel myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
                myViewModel.Code=code;
                Log.println(Log.DEBUG,"ME","sdadasda");
                Log.e("Debug","OKKKKK");
                textView_Code.setText(myViewModel.Code);
                //Toast.makeText(getApplicationContext(),"SHITHAPPENS",Toast.LENGTH_LONG).show();
            }
        }
    }
}