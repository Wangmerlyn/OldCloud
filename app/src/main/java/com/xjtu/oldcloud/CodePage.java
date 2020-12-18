package com.xjtu.oldcloud;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    Button button_Refresh;
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageView_Code = getActivity().findViewById(R.id.imageView_Code);
        button_Refresh = getActivity().findViewById(R.id.button_Refresh);
        Code_Refresh();
    }

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
            }
            else{
                Toast.makeText(getContext(),"SHITCODEMANAAAAA",1).show();
            }
        });
    }
}