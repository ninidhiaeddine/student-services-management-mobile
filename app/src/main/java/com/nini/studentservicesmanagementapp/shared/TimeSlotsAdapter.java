package com.nini.studentservicesmanagementapp.shared;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nini.studentservicesmanagementapp.R;
import com.nini.studentservicesmanagementapp.data.models.TimeSlot;

import java.util.List;

public class TimeSlotsAdapter extends RecyclerView.Adapter<TimeSlotsAdapter.ViewHolder> {
    private List<TimeSlot> timeSlotsSet;

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
            capacityTextValue = view.findViewById(R.id.text_time_slot_value);
            availableTextValue = view.findViewById(R.id.text_time_slot_value);
        }
    }

    public TimeSlotsAdapter(List<TimeSlot> timeSlots) {
        timeSlotsSet = timeSlots;
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
        // compute values:
        String timeSlot = timeSlotsSet.get(position).startTime + " - " + timeSlotsSet.get(position).endTime;
        String capacity = timeSlotsSet.get(position).currentCapacity + " / " + timeSlotsSet.get(position).maximumCapacity;
        boolean isAvailable = timeSlotsSet.get(position).currentCapacity == timeSlotsSet.get(position).maximumCapacity;

        // set text values:
        viewHolder.timeSlotTextValue.setText(timeSlot);
        viewHolder.capacityTextValue.setText(capacity);
        viewHolder.availableTextValue.setText(String.valueOf(isAvailable));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (timeSlotsSet == null)
            return 0;

        return timeSlotsSet.size();
    }
}
