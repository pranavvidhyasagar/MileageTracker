package com.example.pranavvidhyasagar.mileagetracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by h283245 on 6/3/18.
 */

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder> {

    private Context mctx;
    private List<VehicleCard> vehicleList;

    public VehicleAdapter(Context mctx, List<VehicleCard> vehicleList) {
        this.mctx = (Context) mctx;
        this.vehicleList = vehicleList;

    }

    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(mctx);
        View view = inflater.inflate(R.layout.list_layout,null);
        return new VehicleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder holder, int position) {
        VehicleCard vehicle = vehicleList.get(position);
        holder.Manufacturer.setText(vehicle.getManufacturer());
        holder.Model.setText(vehicle.getModel());
        holder.Type.setText(vehicle.getType());
        holder.Email.setText(vehicle.getEmail());
    }

    @Override
    public int getItemCount() {
        return vehicleList.size();
    }

    class VehicleViewHolder extends RecyclerView.ViewHolder{

        TextView Model,Manufacturer,Type,Email;

        public VehicleViewHolder(@NonNull View itemView) {
            super(itemView);

            Model = itemView.findViewById(R.id.Model);
            Manufacturer = itemView.findViewById(R.id.Manufacturer);
            Type = itemView.findViewById(R.id.Type);
            Email = itemView.findViewById(R.id.Email);
        }
    }
}
