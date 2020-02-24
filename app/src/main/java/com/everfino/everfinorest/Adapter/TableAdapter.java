package com.everfino.everfinorest.Adapter;

import android.content.Context;
import android.content.DialogInterface;
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
import com.everfino.everfinorest.Fragments.EditMenuFragment;
import com.everfino.everfinorest.Fragments.EditTableFragment;
import com.everfino.everfinorest.Fragments.MenuFragment;
import com.everfino.everfinorest.Fragments.TableFragment;
import com.everfino.everfinorest.Models.MenuList;
import com.everfino.everfinorest.Models.TableList;
import com.everfino.everfinorest.R;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.Viewholder> {
    Context context;
    List<HashMap<String,String>> ls;
    HashMap<String, String> map;

    public TableAdapter(Context context, List<HashMap<String, String>> ls) {
        this.context = context;
        this.ls = ls;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.menulist_design, null);

        return new Viewholder(view);
    }


    public void onBindViewHolder(@NonNull TableAdapter.Viewholder holder, int position) {
        map=ls.get(position);
        holder.txtdemo.setText(map.get("tableno")+map.get("status"));
        Log.e("AD#####",map.get("tableno"));
    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView txtdemo;
        private Api apiService;


        public Viewholder(@NonNull final View itemView) {
            super(itemView);
            apiService= ApiClient.getClient().create(Api.class);
            txtdemo=itemView.findViewById(R.id.txtdemo);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Fragment fragment=new EditTableFragment();
                    Bundle b=new Bundle();
                    b.putString("tableid",ls.get(getAdapterPosition()).get("tableid"));
                    b.putString("tableno",ls.get(getAdapterPosition()).get("tableno"));
                    b.putString("status",ls.get(getAdapterPosition()).get("status"));
                    b.putString("tableqr",ls.get(getAdapterPosition()).get("tableqr"));

                    fragment.setArguments(b);

                    loadFragment(fragment,itemView);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(context, "long press"+getAdapterPosition(), Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder al=new AlertDialog.Builder(v.getContext());
                    al.setMessage("Do you want to delete");
                    al.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Call<TableList> call=apiService.delete_Rest_Table(Integer.parseInt(ls.get(getAdapterPosition()).get("tableid")));
                            call.enqueue(new Callback<TableList>() {
                                @Override
                                public void onResponse(Call<TableList> call, Response<TableList> response) {
                                    Toast.makeText(itemView.getContext(), "deleted", Toast.LENGTH_SHORT).show();
                                    Fragment fragment=new TableFragment();
                                    loadFragment(fragment,itemView);
                                }

                                @Override
                                public void onFailure(Call<TableList> call, Throwable t) {

                                }
                            });
                        }
                    });
                    al.setNegativeButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Fragment fragment=new TableFragment();
                            loadFragment(fragment,itemView);
                        }
                    });

                    AlertDialog a=al.create();
                    a.show();
                    return false;
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
}
