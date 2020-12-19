package com.xjtu.oldcloud;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.yzq.zxinglibrary.encode.CodeCreator;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CodePage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CodePage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView imageView_Code ;
    Button button_Refresh,button_CallFam;
    public CodePage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CodePage.
     */
    // TODO: Rename and change types and number of parameters
    public static CodePage newInstance(String param1, String param2) {
        CodePage fragment = new CodePage();
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
        return inflater.inflate(R.layout.fragment_code_page, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageView_Code = getActivity().findViewById(R.id.imageView_Code);
        button_Refresh = getActivity().findViewById(R.id.button_Refresh);
        button_CallFam = getActivity().findViewById(R.id.button_CallFam);
        Call_Family();
        Code_Refresh();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void Call_Family(){
        MyViewModel myViewModel = ViewModelProviders.of(getActivity()).get(MyViewModel.class);
        button_CallFam.setOnClickListener(v->{
            if(getActivity().checkSelfPermission(Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
                String[] Permissions = {Manifest.permission.CALL_PHONE};
                requestPermissions(Permissions,10);
            }
            Intent intent=new Intent(Intent.ACTION_CALL);
            Uri Data = Uri.parse("tel:"+myViewModel.PhoneNumber);
            intent.setData(Data);
            startActivity(intent);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void Code_Refresh(){
        button_Refresh.setOnClickListener(v -> {
            MyViewModel myViewModel = ViewModelProviders.of(getActivity()).get(MyViewModel.class);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
            Bitmap code=null;
            String Prefix = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            try {
                code = CodeCreator.createQRCode(Prefix+myViewModel.UserName+"+"+myViewModel.UserPassword,400,400,null);
            } catch (WriterException e) {
                e.printStackTrace();
                Log.println(Log.DEBUG,"DEBUG","SHIT HAPPENDS");
            }
            if(code!=null){
                imageView_Code.setImageBitmap(code);
                Send_Text();
            }
            else{
                Toast.makeText(getContext(),"SHITCODEMANAAAAA",Toast.LENGTH_LONG).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void Send_Text(){
        MyViewModel myViewModel = ViewModelProviders.of(getActivity()).get(MyViewModel.class);

        if(getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)!=
                        PackageManager.PERMISSION_GRANTED  ||  getActivity().checkSelfPermission(Manifest.permission.SEND_SMS)==PackageManager.PERMISSION_DENIED){
            String[] Permission={Manifest.permission.SEND_SMS,Manifest.permission.ACCESS_FINE_LOCATION};

            requestPermissions(Permission,10);
            Toast.makeText(getContext(),Integer.toString(Permission.length),Toast.LENGTH_LONG).show();
        }
        String serviceString = Context.LOCATION_SERVICE;
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(serviceString);
        String provider = LocationManager.GPS_PROVIDER;
        Location location = locationManager.getLastKnownLocation(provider);
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        SmsManager smsManager = SmsManager.getDefault();
        String Content = "所处位置纬度："+Double.toString(lat)+"经度："+Double.toString(lng)+"!!!!";
        //Toast.makeText(getContext(),myViewModel.PhoneNumber,Toast.LENGTH_LONG).show();
        smsManager.sendTextMessage(myViewModel.PhoneNumber,null,Content,null,null);

    }
}