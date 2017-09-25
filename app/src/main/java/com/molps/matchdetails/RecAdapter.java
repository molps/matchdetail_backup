package com.molps.matchdetails;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private RecyclerView recyclerView;
    private static final String LOG_TAG = RecAdapter.class.getSimpleName();

    private List<List<PlayerStats>> mData;
    private List<PlayerStats> basicData;
    private List<PlayerStats> detailData;
    private List<PlayerStats> nameData;

    public RecAdapter(RecyclerView recyclerView) {
        mData = new ArrayList<>();
        nameData = new ArrayList<>();
        basicData = new ArrayList<>();
        detailData = new ArrayList<>();
        this.recyclerView = recyclerView;

    }

    @Override
    public int getItemViewType(int position) {
        return nameData.get(position).getPlayerStatsId();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {
            case PlayerStats.ID_NAME:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
                return new NameViewHolder(view);
            case PlayerStats.ID_DETAIL:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail, parent, false);
                return new DetailViewHolder(view);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case PlayerStats.ID_NAME:
                ((NameViewHolder) holder).bindType();
                break;
            case PlayerStats.ID_DETAIL:
                ((DetailViewHolder) holder).bindType();
                break;
        }

    }

    @Override
    public int getItemCount() {
        return nameData.size();
    }

    public void swapData(List<List<PlayerStats>> newData) {
        int oldSize = detailData.size();
        mData = newData;
        basicData = mData.get(0);
        nameData = mData.get(1);
        detailData = mData.get(2);
        //notifyDataSetChanged();
        notifyItemRangeInserted(oldSize, detailData.size());

        Log.v(LOG_TAG, "DOTAMATCH: basicData.size = " + basicData.size() + " nameData.size = " + nameData.size() + " detailData.size = " + detailData.size());
    }

    public class NameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nameText;
        private PlayerName player;

        public NameViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(this);
        }

        private void bindType() {
            player = (PlayerName) nameData.get(getAdapterPosition());
            nameText.setText(player.getName());
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition() + 1;
            if (!player.isExpanded()) {
                nameData.add(position, detailData.get(getAdapterPosition()));
                detailData.add(position, null);
                notifyItemInserted(position);
                player.setExpanded(true);
                recyclerView.scrollToPosition(position);

                Log.v(LOG_TAG, "DOTAMATCH: nameData.size = " + nameData.size() + " totalCount size: " + getItemCount());
            } else {
                nameData.remove(position);
                notifyItemRemoved(position);
                player.setExpanded(false);
                detailData.remove(position);

            }
        }
    }

    public class DetailViewHolder extends RecyclerView.ViewHolder {
        private TextView heroDamage, towerDamage, lastHits, denies;

        public DetailViewHolder(View itemView) {
            super(itemView);
            heroDamage = itemView.findViewById(R.id.herodamage_tv);
            towerDamage = itemView.findViewById(R.id.towerdamage_tv);
            lastHits = itemView.findViewById(R.id.lasthits_tv);
            denies = itemView.findViewById(R.id.denies_tv);
        }

        private void bindType() {
            PlayerDetail player = (PlayerDetail) nameData.get(getAdapterPosition());

            heroDamage.setText(String.valueOf(player.getHeroDamage()));
            towerDamage.setText(String.valueOf(player.getBuildingDamage()));
            lastHits.setText(String.valueOf(player.getLastHits()));
            denies.setText(String.valueOf(player.getDenies()));


        }
    }
}
