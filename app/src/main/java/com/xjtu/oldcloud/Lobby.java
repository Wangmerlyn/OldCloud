package com.xjtu.oldcloud;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Lobby#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Lobby extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageButton imageButton_Code,imageButton_Scan;
    public Lobby() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Lobby.
     */
    // TODO: Rename and change types and number of parameters
    public static Lobby newInstance(String param1, String param2) {
        Lobby fragment = new Lobby();
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
        return inflater.inflate(R.layout.fragment_lobby, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageButton_Code = getActivity().findViewById(R.id.imageButton_Code);
        imageButton_Scan = getActivity().findViewById(R.id.imageButton_Scan);
        Code_Page();
        Scan_Page();
    }
    void Code_Page(){
            imageButton_Code.setOnClickListener(v -> {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_lobby_to_codePage);
            });
    }
    void Scan_Page(){
        imageButton_Scan.setOnClickListener(v->{
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_lobby_to_scanPage);
        });
    }
}