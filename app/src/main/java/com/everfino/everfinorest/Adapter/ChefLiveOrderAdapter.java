package com.everfino.everfinorest.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.everfino.everfinorest.ApiConnection.Api;
import com.everfino.everfinorest.ApiConnection.ApiClient;
import com.everfino.everfinorest.AppSharedPreferences;
import com.everfino.everfinorest.ChefActivity;
import com.everfino.everfinorest.Fragments.ChefFragments.ChefLiveOrderFragment;
import com.everfino.everfinorest.Fragments.EditMenuFragment;
import com.everfino.everfinorest.Fragments.LiveOrderFragment;
import com.everfino.everfinorest.Fragments.MenuFragment;
import com.everfino.everfinorest.Fragments.TableFragment;
import com.everfino.everfinorest.MainActivity;
import com.everfino.everfinorest.Models.Liveorder;
import com.everfino.everfinorest.Models.MenuList;
import com.everfino.everfinorest.R;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChefLiveOrderAdapter extends RecyclerView.Adapter<ChefLiveOrderAdapter.Viewholder> {

    Context context;
    List<HashMap<String,String>> ls;
    HashMap<String, String> map;
    AppSharedPreferences appSharedPreferences;
    HashMap<String,String> pref;


    public ChefLiveOrderAdapter(Context context, List<HashMap<String,String>> ls) {
        this.context=context;
        this.ls=ls;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.liveorderlist_design, null);
        appSharedPreferences=new AppSharedPreferences(context);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        map=ls.get(position);
        holder.itemprice.setText(map.get("itemprice"));
        holder.quantity.setText("Quantity : "+map.get("quntity"));
        holder.itemname.setText(map.get("itemname"));
        holder.status.setText(map.get("status"));
        holder.order_date.setText(map.get("order_date"));
        holder.tableid.setText(map.get("tableid"));
    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView itemname,quantity,status,itemprice,order_date,tableid;
        private  Api apiService;


        public Viewholder(@NonNull final View itemView) {
            super(itemView);
            apiService= ApiClient.getClient().create(Api.class);
            itemname=itemView.findViewById(R.id.txt_itemname);
            quantity=itemView.findViewById(R.id.txt_quantity);
            status=itemView.findViewById(R.id.txt_Status);
            itemprice=itemView.findViewById(R.id.txt_itemprice);
            order_date=itemView.findViewById(R.id.txt_Date);
            tableid=itemView.findViewById(R.id.txt_tableid);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "press"+getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    appSharedPreferences=new AppSharedPreferences(context);
                    map=appSharedPreferences.getPref();
                    AlertDialog.Builder al=new AlertDialog.Builder(context);
                    al.setTitle("Set Stautus of Order");
                    final String[] items={"Accepted","NotAccepted","Pendding","Done"};
                    int chekeditem=1;
                    al.setSingleChoiceItems(items, chekeditem, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, int which) {
                            Call<Liveorder> call=apiService.set_Rest_liveorderstatus(Integer.parseInt(map.get("restid")),Integer.parseInt(ls.get(getAdapterPosition()).get("liveid")),items[which]);
                            call.enqueue(new Callback<Liveorder>() {
                                @Override
                                public void onResponse(Call<Liveorder> call, Response<Liveorder> response) {
                                    Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
                                    dialog.cancel();
                                    Fragment fragment=new ChefLiveOrderFragment();
                                    loadFragment(fragment,itemView);

                                }

                                @Override
                                public void onFailure(Call<Liveorder> call, Throwable t) {

                                }
                            });
                            switch (which) {
                                case 0:
                                    Toast.makeText(context, "Accepter", Toast.LENGTH_LONG).show();
                                    break;
                                case 1:
                                    Toast.makeText(context, "NotAccepted", Toast.LENGTH_LONG).show();
                                    break;
                                case 2:
                                    Toast.makeText(context, "Pendding", Toast.LENGTH_LONG).show();
                                    break;
                                case 3:
                                    Toast.makeText(context, "Done", Toast.LENGTH_LONG).show();
                                    break;
                            }

                        }

                    });
                    AlertDialog a=al.create();
                    a.show();

                }
            });
        }

        public void loadFragment(Fragment fragment,View v) {
            AppCompatActivity activity=(AppCompatActivity) v.getContext();
            FragmentTransaction transaction =activity.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }
    public void filterList(List<HashMap<String,String>> ls)
    {
        this.ls=ls;
        notifyDataSetChanged();
    }

}
