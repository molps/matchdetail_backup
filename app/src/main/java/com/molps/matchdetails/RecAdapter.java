package com.molps.matchdetails;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

public class RecAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private RecyclerView recyclerView;
    private static final String LOG_TAG = RecAdapter.class.getSimpleName();

    private List<List<PlayerStats>> mData;
    private List<PlayerStats> basicData;
    private List<PlayerStats> detailData;
    private List<PlayerStats> nameData;
    private RequestManager glide;
    private Context c;

    public RecAdapter(Context context, RequestManager glide, RecyclerView recyclerView) {
        mData = new ArrayList<>();
        nameData = new ArrayList<>();
        basicData = new ArrayList<>();
        detailData = new ArrayList<>();
        this.recyclerView = recyclerView;
        this.glide = glide;
        c = context;

    }

    @Override
    public int getItemViewType(int position) {
        return basicData.get(position).getPlayerStatsId();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {
            case PlayerStats.ID_BASIC:
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
            case PlayerStats.ID_BASIC:
                ((NameViewHolder) holder).bindType();
                break;
            case PlayerStats.ID_DETAIL:
                ((DetailViewHolder) holder).bindType();
                break;
        }

    }

    @Override
    public int getItemCount() {
        return basicData.size();
    }

    public void swapData(List<List<PlayerStats>> newData) {
        int oldSize = basicData.size();
        mData = newData;
        basicData = mData.get(0);
        nameData = mData.get(1);
        detailData = mData.get(2);
        //notifyDataSetChanged();
        notifyItemRangeInserted(oldSize, basicData.size());

        Log.v(LOG_TAG, "DOTAMATCH: basicData.size = " + basicData.size() + " nameData.size = " + nameData.size() + " detailData.size = " + detailData.size());
    }

    public class NameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private PlayerBasic player;
        private View topDivider, botDivider;
        private TextView playerName, kda, level;
        private ImageView hero, slot0, slot1, slot2, slot3, slot4, slot5;

        public NameViewHolder(View itemView) {
            super(itemView);
            topDivider = itemView.findViewById(R.id.top_divider);
            botDivider = itemView.findViewById(R.id.bot_divider);
            playerName = itemView.findViewById(R.id.player_name);
            kda = itemView.findViewById(R.id.kda);
            level = itemView.findViewById(R.id.hero_level);
            hero = itemView.findViewById(R.id.hero_image);
            slot0 = itemView.findViewById(R.id.item_0);
            slot1 = itemView.findViewById(R.id.item_1);
            slot2 = itemView.findViewById(R.id.item_2);
            slot3 = itemView.findViewById(R.id.item_3);
            slot4 = itemView.findViewById(R.id.item_4);
            slot5 = itemView.findViewById(R.id.item_5);


            itemView.setOnClickListener(this);
        }

        private void bindType() {
            level.setVisibility(View.INVISIBLE);
            if (getAdapterPosition() == 0)
                topDivider.setVisibility(View.INVISIBLE);
            else
                topDivider.setVisibility(View.VISIBLE);
            if (getAdapterPosition() == getItemCount() - 1)
                botDivider.setVisibility(View.VISIBLE);
            else
                botDivider.setVisibility(View.INVISIBLE);
            player = (PlayerBasic) basicData.get(getAdapterPosition());
            if (!TextUtils.isEmpty(player.getPlayerName())) {
                playerName.setText(player.getPlayerName());
            } else
                playerName.setText("Anonymous");
            kda.setText(player.getKDA());
            level.setText(String.valueOf(player.getHeroLevel()));
            glide.load(ImageResources.getItem(c, player.getItem0())).into(slot0);
            glide.load(ImageResources.getItem(c, player.getItem1())).into(slot1);
            glide.load(ImageResources.getItem(c, player.getItem2())).into(slot2);
            glide.load(ImageResources.getItem(c, player.getItem3())).into(slot3);
            glide.load(ImageResources.getItem(c, player.getItem4())).into(slot4);
            glide.load(ImageResources.getItem(c, player.getItem5())).into(slot5);
            glide.load(ImageResources.getHero(c, player.getHeroImageId())).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    level.setVisibility(View.VISIBLE);
                    return false;
                }
            }).into(hero);


        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition() + 1;
            if (!player.isExpanded()) {
                basicData.add(position, detailData.get(getAdapterPosition()));
                detailData.add(position, null);
                notifyItemInserted(position);
                player.setExpandend(true);
                if (((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition() <= position)
                    recyclerView.scrollToPosition(position);

                Log.v(LOG_TAG, "DOTAMATCH: nameData.size = " + nameData.size() + " totalCount size: " + getItemCount());
            } else {
                basicData.remove(position);
                notifyItemRemoved(position);
                player.setExpandend(false);
                detailData.remove(position);

            }
        }
    }

    public class DetailViewHolder extends RecyclerView.ViewHolder {
        private TextView heroDamage, towerDamage, lastHits, denies, gpm, xpm, obs, sen;

        public DetailViewHolder(View itemView) {
            super(itemView);
            heroDamage = itemView.findViewById(R.id.herodamage_tv);
            towerDamage = itemView.findViewById(R.id.towerdamage_tv);
            lastHits = itemView.findViewById(R.id.lasthits_tv);
            denies = itemView.findViewById(R.id.denies_tv);
            gpm = itemView.findViewById(R.id.gpm_tv);
            xpm = itemView.findViewById(R.id.xpm_tv);
            obs = itemView.findViewById(R.id.obs_tv);
            sen = itemView.findViewById(R.id.sen_tv);
        }

        private void bindType() {
            PlayerDetail player = (PlayerDetail) detailData.get(getAdapterPosition() - 1);

            heroDamage.setText("Hero damage: " + String.valueOf(player.getHeroDamage()));
            towerDamage.setText("Building damage: " + String.valueOf(player.getBuildingDamage()));
            lastHits.setText("Last hits: " + String.valueOf(player.getLastHits()));
            denies.setText("Denies: " + String.valueOf(player.getDenies()));
            gpm.setText("GPM: " + String.valueOf(player.getGpm()));
            xpm.setText("XPM: " + String.valueOf(player.getXpm()));
            obs.setText(String.valueOf(player.getObs()) + "x");
            sen.setText(String.valueOf(player.getSen()) + "x");


        }
    }
}
