package com.everfino.everfinorest.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.everfino.everfinorest.ApiConnection.Api;
import com.everfino.everfinorest.ApiConnection.ApiClient;
import com.everfino.everfinorest.MainActivity;
import com.everfino.everfinorest.Models.MenuList;
import com.everfino.everfinorest.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditMenuFragment extends Fragment {

    EditText itemname,itemprice,itemdesc,itemtype;
    Button editmenubtn,cancelbtn;

    private static Api apiService;
    MenuList m;
    public EditMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        m=new MenuList(Integer.parseInt(getArguments().getString("itemid")),getArguments().getString("itemname"),Integer.parseInt(getArguments().getString("itemprice")),getArguments().getString("itemdesc"),getArguments().getString("itemtype"));
        Log.e("###",m.itemname+m.itemdesc);
        View view= inflater.inflate(R.layout.fragment_edit_menu, container, false);
        apiService= ApiClient.getClient().create(Api.class);
        itemname=view.findViewById(R.id.eitemname);
        itemprice=view.findViewById(R.id.eitemprice);
        itemdesc=view.findViewById(R.id.eitemdesc);
        itemtype=view.findViewById(R.id.eitemtype);

        itemname.setText(m.itemname);
        itemprice.setText(m.itemprice+"" +
                "");
        itemdesc.setText(m.itemdesc);
        itemtype.setText(m.itemtype);

        editmenubtn=view.findViewById(R.id.editnewbtn);
        cancelbtn=view.findViewById(R.id.ecancelbtn);
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=new MenuFragment();

                loadFragment(fragment);
            }
        });

        editmenubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m.itemname=itemname.getText().toString();
                m.itemprice=Integer.parseInt(itemprice.getText().toString());
                m.itemdesc=itemdesc.getText().toString();
                m.itemtype=itemtype.getText().toString();

                 Call<MenuList> call=apiService.update_Rest_Menu(m.itemid,m);
                 call.enqueue(new Callback<MenuList>() {
                     @Override
                     public void onResponse(Call<MenuList> call, Response<MenuList> response) {
                         Toast.makeText(getContext(), "edited", Toast.LENGTH_SHORT).show();
                         Fragment fragment=new MenuFragment();

                        loadFragment(fragment);
                     }

                     @Override
                     public void onFailure(Call<MenuList> call, Throwable t) {
                         Toast.makeText(getContext(), t.toString()+"Try Again", Toast.LENGTH_SHORT).show();
                     }
                 });
            }
        });
        return view;
    }


    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
