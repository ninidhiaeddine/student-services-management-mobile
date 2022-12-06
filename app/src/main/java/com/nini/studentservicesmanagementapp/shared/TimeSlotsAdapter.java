package com.nini.studentservicesmanagementapp.shared;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nini.studentservicesmanagementapp.R;
import com.nini.studentservicesmanagementapp.data.models.TimeSlot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TimeSlotsAdapter extends RecyclerView.Adapter<TimeSlotsAdapter.ViewHolder> {
    private final List<TimeSlot> timeSlotsSet;
    private final Context context;
    private OnItemClickListener onItemClickListener;

    public TimeSlotsAdapter(Context context, List<TimeSlot> timeSlots) {
        this.context = context;
        timeSlotsSet = timeSlots;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.time_slot_card, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // convert dates to desirable format:
        Date startTime = timeSlotsSet.get(position).startTime;
        Date endTime = timeSlotsSet.get(position).endTime;

        // extract hours and minutes from the time slot:
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String formattedStartTime = formatter.format(startTime);
        String formattedEndTime = formatter.format(endTime);

        // compute values:
        String timeSlot = formattedStartTime + " - " + formattedEndTime;
        String capacity = timeSlotsSet.get(position).currentCapacity + " / " + timeSlotsSet.get(position).maximumCapacity;
        boolean isAvailable = timeSlotsSet.get(position).currentCapacity < timeSlotsSet.get(position).maximumCapacity;

        // set text values:
        viewHolder.timeSlotTextValue.setText(timeSlot);
        viewHolder.capacityTextValue.setText(capacity);
        viewHolder.availableTextValue.setText(formatIsAvailable(isAvailable));
        if (isAvailable)
            viewHolder.availableTextValue.setTextColor(context.getResources().getColor(R.color.teal_200));
        else
            viewHolder.availableTextValue.setTextColor(context.getResources().getColor(R.color.orange));

        viewHolder.itemView.setOnClickListener(v -> {
            onItemClickListener.onItemClick(timeSlotsSet.get(position));
        });
    }

    private String formatIsAvailable(boolean isAvailable) {
        if (isAvailable)
            return "Yes";
        else
            return "No";
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (timeSlotsSet == null)
            return 0;

        return timeSlotsSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView timeSlotTextValue;
        public TextView capacityTextValue;
        public TextView availableTextValue;

        private final View view;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            this.view = view;

            findViews();
        }

        private void findViews() {
            timeSlotTextValue = view.findViewById(R.id.text_time_slot_value);
            capacityTextValue = view.findViewById(R.id.text_capacity_value);
            availableTextValue = view.findViewById(R.id.text_available_value);
        }
    }
}
