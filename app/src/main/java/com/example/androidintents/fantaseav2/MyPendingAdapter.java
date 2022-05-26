package com.example.androidintents.fantaseav2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyPendingAdapter extends RecyclerView.Adapter<MyPendingAdapter.MyViewHolder> {

    Context context;
    ArrayList<UserPending> list;
    static RecyclerViewClickListener listener;

    public MyPendingAdapter(Context context, ArrayList<UserPending> list, RecyclerViewClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.findcustomercard,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        UserPending user = list.get(position);
        holder.username.setText(user.getUsername());
        holder.activities.setText(user.getActivities());
        holder.agency_name.setText(user.getAgency_name());
        holder.destination_name.setText(user.getDestination_name());
        holder.destination_province.setText(user.getDestination_province());
        holder.boat_name.setText(user.getBoat_name());
        holder.capacity.setText(user.getCapacity());
        holder.date_sched.setText(user.getDate_sched());
        holder.date_sent.setText(user.getDate_sent());
        holder.ticketStatus.setText(user.getTicketStatus());
        holder.payID.setText(user.getPayID());
        holder.base_price.setText(String.valueOf(user.getBase_price())); //integer

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView username, activities, agency_name, destination_name, destination_province, boat_name,capacity,date_sched,date_sent,ticketStatus,payID,base_price;

        //ImageView messagePicture;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.destinationCustomerNameTextView);
            activities= itemView.findViewById(R.id.destinationActivitesTextView);
            agency_name = itemView.findViewById(R.id.agencyNameTextView);
            destination_name = itemView.findViewById(R.id.destinationNameTextView);
            destination_province = itemView.findViewById(R.id.destinationProvinceTextView);
            boat_name = itemView.findViewById(R.id.pumpboatNameTextView);
            capacity = itemView.findViewById(R.id.seatingCapacityTextView);
            date_sched= itemView.findViewById(R.id.dateScheduledTextView);
            date_sent = itemView.findViewById(R.id.dateBookedTextView);
            ticketStatus = itemView.findViewById(R.id.ticketStatusTextView);
            payID = itemView.findViewById(R.id.payIDTextView);
            base_price = itemView.findViewById(R.id.basePriceTextView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }
    public interface RecyclerViewClickListener{
        void onClick(View v , int Position);
    }
}