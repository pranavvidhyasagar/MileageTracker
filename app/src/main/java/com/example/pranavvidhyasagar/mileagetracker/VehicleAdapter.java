package com.example.pranavvidhyasagar.mileagetracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by h283245 on 6/3/18.
 */

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder> {

    private Context mctx;
    private List<String> vehicleList;
    private  onItemClickListener mlistener = null;

    public interface onItemClickListener {
        public void itemClicked(View view , int position);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        mlistener = listener;
    }


    public VehicleAdapter(Context mctx, List<String> vehicleList) {
        this.mctx = (Context) mctx;
        this.vehicleList = vehicleList;

    }



    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(mctx);
        View view = inflater.inflate(R.layout.list_layout,null);
        /*view.setOnClickListener(mOnClickListener);*/
        return new VehicleViewHolder(view, mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder holder, int position) {
        String vehicle = vehicleList.get(position);
        holder.Manufacturer.setText(vehicle);

        /*holder.Model.setText(vehicle.getModel());
        holder.Type.setText(vehicle.getType());
        holder.Email.setText(vehicle.getEmail());*/
    }

    @Override
    public int getItemCount() {
        return vehicleList.size();
    }

    public static class VehicleViewHolder extends RecyclerView.ViewHolder{

        TextView Model,Manufacturer,Type,Email;

        public VehicleViewHolder(@NonNull View itemView, final onItemClickListener listener) {
            super(itemView);

            //Model = itemView.findViewById(R.id.Model);
            Manufacturer = itemView.findViewById(R.id.Manufacturer);
            //Type = itemView.findViewById(R.id.Type);
            //Email = itemView.findViewById(R.id.Email);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            listener.itemClicked(view,pos);
                        }
                    }


                }
            });
        }
    }
}
