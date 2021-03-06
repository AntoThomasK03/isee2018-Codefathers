package codefathers.tripalert.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import codefathers.tripalert.models.Tracking;
import codefathers.tripalert.R;
import codefathers.tripalert.models.TrackingStatus;

public class FollowedTrackingsRecyclerAdapter extends RecyclerView.Adapter<FollowedTrackingsRecyclerAdapter.ViewHolder> {
    private static final String TAG = "FollowedTrackingsRecycl";
    private LayoutInflater mInflater;           //i have no idea what this is
    private List<Tracking> mTrackings;      //cached copies of trackings
    public FollowedTrackingsListener onClickListener;

    public interface FollowedTrackingsListener {

        void unfollowOnClick(View v, int position);
    }

    //the view holder holds the seperate views.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView phoneNumber;
        TextView startingPoint;
        TextView destination;
        TextView log;
        CardView card;
        Button unfollow;



        public ViewHolder(View itemView) {
            super(itemView);
            phoneNumber = itemView.findViewById(R.id.recPhoneNumber);
            startingPoint = itemView.findViewById(R.id.recStartingPoint);
            destination = itemView.findViewById(R.id.recDestination);
            card = itemView.findViewById(R.id.recCard);
            unfollow = itemView.findViewById(R.id.unfollowButton);

            unfollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.unfollowOnClick(v, getAdapterPosition());
                }
            });


        }
    }

    public FollowedTrackingsRecyclerAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_tracking, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mTrackings != null) {
            Tracking current = mTrackings.get(position);

            holder.phoneNumber.setText(current.getCreator());
            holder.destination.setText(current.getDestination().getAddress());
            holder.startingPoint.setText(current.getStartingPoint().getAddress());


            int color;
            switch (current.getStatus()) {
                case TrackingStatus.DELAYED:
                    color = ContextCompat.getColor(holder.card.getContext(), R.color.colorDelayPauseNotResponding);
                    break;
                case TrackingStatus.NOT_RESPONDING:
                    color = ContextCompat.getColor(holder.card.getContext(), R.color.colorDelayPauseNotResponding);
                    break;
                case TrackingStatus.EMERGENCY:
                    color = ContextCompat.getColor(holder.card.getContext(), R.color.colorEmergency);
                    break;
                case TrackingStatus.ABORTED:
                    color = ContextCompat.getColor(holder.card.getContext(), R.color.colorDestructiveAction);
                    break;
                case TrackingStatus.FINISHED:
                    color = ContextCompat.getColor(holder.card.getContext(), R.color.colorArrived);
                    break;
                default:
                    color = ContextCompat.getColor(holder.card.getContext(), R.color.colorEverythingFine);
            }
            holder.phoneNumber.setTextColor(color);
            holder.card.setCardBackgroundColor(color);
            holder.unfollow.setTextColor(ContextCompat.getColor(holder.card.getContext(), R.color.colorTextOnBlack));
        }

    }

    public void setListener(FollowedTrackingsListener listener){
        onClickListener = listener;
    }
    public void setTrackings(List<Tracking> trackings) {
        mTrackings = trackings;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mTrackings != null)
            return mTrackings.size();
        else return 0;
    }


}

