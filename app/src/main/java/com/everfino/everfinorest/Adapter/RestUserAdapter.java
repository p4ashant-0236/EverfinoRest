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
import com.everfino.everfinorest.AppSharedPreferences;
import com.everfino.everfinorest.Fragments.EditMenuFragment;
import com.everfino.everfinorest.Fragments.EditRestUserFragment;
import com.everfino.everfinorest.Fragments.EditTableFragment;
import com.everfino.everfinorest.Fragments.MenuFragment;
import com.everfino.everfinorest.Fragments.RestStaffManageFragment;
import com.everfino.everfinorest.Fragments.TableFragment;
import com.everfino.everfinorest.Models.MenuList;
import com.everfino.everfinorest.Models.RestUserResponse;
import com.everfino.everfinorest.Models.TableList;
import com.everfino.everfinorest.R;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestUserAdapter extends RecyclerView.Adapter<RestUserAdapter.Viewholder> {
    Context context;
    List<HashMap<String,String>> ls;
    HashMap<String, String> map;
    AppSharedPreferences appSharedPreferences;
    HashMap<String,String> pref;
    public RestUserAdapter(Context context, List<HashMap<String, String>> ls) {
        this.context = context;
        this.ls = ls;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.restuserlist_design, null);
        appSharedPreferences=new AppSharedPreferences(context);
        return new Viewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        map=ls.get(position);
        holder.name.setText(map.get("name"));
        holder.email.setText(map.get("email"));
        holder.mobile.setText(map.get("name"));
        holder.role.setText(map.get("role"));
        Log.e("AD#####",map.get("name"));
    }


    @Override
    public int getItemCount() {
        return ls.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView name,email,mobile,role;
        private Api apiService;


        public Viewholder(@NonNull final View itemView) {
            super(itemView);
            apiService= ApiClient.getClient().create(Api.class);
            name=itemView.findViewById(R.id.txt_name);
            email=itemView.findViewById(R.id.txt_email);
            mobile=itemView.findViewById(R.id.txt_mobileno);
            role=itemView.findViewById(R.id.txt_role);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Fragment fragment=new EditRestUserFragment();
                    Bundle b=new Bundle();
                    b.putString("userid",ls.get(getAdapterPosition()).get("userid"));
                    b.putString("name",ls.get(getAdapterPosition()).get("name"));
                    b.putString("password",ls.get(getAdapterPosition()).get("password"));
                    b.putString("email",ls.get(getAdapterPosition()).get("email"));
                    b.putString("mobileno",ls.get(getAdapterPosition()).get("mobileno"));
                    b.putString("role",ls.get(getAdapterPosition()).get("role"));

                    fragment.setArguments(b);

                    loadFragment(fragment,itemView);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(context, "long press"+getAdapterPosition(), Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder al=new AlertDialog.Builder(v.getContext());
                    pref=appSharedPreferences.getPref();
                    al.setMessage("Do you want to delete");
                    al.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Call<RestUserResponse> call=apiService.delete_Rest_User(Integer.parseInt(pref.get("restid")),Integer.parseInt(ls.get(getAdapterPosition()).get("userid")));
                            call.enqueue(new Callback<RestUserResponse>() {
                                @Override
                                public void onResponse(Call<RestUserResponse> call, Response<RestUserResponse> response) {
                                    Toast.makeText(itemView.getContext(), "deleted", Toast.LENGTH_SHORT).show();
                                    Fragment fragment=new RestStaffManageFragment();
                                    loadFragment(fragment,itemView);
                                }

                                @Override
                                public void onFailure(Call<RestUserResponse> call, Throwable t) {

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

    } public void filterList(List<HashMap<String,String>> ls)
    {
        this.ls=ls;
        notifyDataSetChanged();
    }
}
