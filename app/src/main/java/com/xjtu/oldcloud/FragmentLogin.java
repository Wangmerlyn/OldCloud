package com.xjtu.oldcloud;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentLogin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLogin extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText editText_PersonName,editText_Password;
    Button button_Login;

    public FragmentLogin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentLogin.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentLogin newInstance(String param1, String param2) {
        FragmentLogin fragment = new FragmentLogin();
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

        //Log.println(1,"SHIT","HELLO");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        editText_Password = getActivity().findViewById(R.id.editTextTextPassword);
        editText_PersonName=getActivity().findViewById(R.id.editTextTextPersonName);
        button_Login = getActivity().findViewById(R.id.button_Login);
        Set_Button_Login();
    }

    void Set_Button_Login(){
        button_Login.setOnClickListener(v -> {
            if (editText_PersonName.getText().toString()!=""&&editText_Password.getText().toString()!=""){
                MyViewModel myViewModel = ViewModelProviders.of(getActivity()).get(MyViewModel.class);
                myViewModel.UserName=editText_PersonName.getText().toString();
                myViewModel.UserPassword=editText_Password.getText().toString();
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_fragmentLogin_to_lobby);
            }
        });
        /*button_Login.setOnClickListener(v -> {
            Toast.makeText(getContext(),"HAI",1).show();
        });*/
    }
}