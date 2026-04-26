package com.example.cardgame.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.cardgame.R;
import com.example.cardgame.model.Card;
import com.example.cardgame.model.Element;
import com.example.cardgame.model.HeroImages;
import java.util.ArrayList;
import java.util.List;

public class CardHandAdapter extends RecyclerView.Adapter<CardHandAdapter.VH> {

    public interface OnSelectionChangedListener { void onSelectionChanged(int index); }

    private List<Card> cards;
    private int selectedPos = -1;
    private OnSelectionChangedListener selectionListener;

    public CardHandAdapter(List<Card> cards) {
        this.cards = new ArrayList<>(cards);
    }

    public void setCards(List<Card> newCards) {
        this.cards = new ArrayList<>(newCards);
        selectedPos = -1;
    }

    public void clearSelection() {
        int prev = selectedPos;
        selectedPos = -1;
        if (prev >= 0 && prev < cards.size()) notifyItemChanged(prev);
    }

    public void setOnSelectionChangedListener(OnSelectionChangedListener l) {
        this.selectionListener = l;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_hand, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        Card card = cards.get(pos);
        boolean selected = (pos == selectedPos);
        h.bind(card, selected);
        h.itemView.setOnClickListener(v -> {
            int prev = selectedPos;
            selectedPos = (selectedPos == pos) ? -1 : pos;
            if (prev >= 0 && prev < cards.size()) notifyItemChanged(prev);
            if (selectedPos >= 0) notifyItemChanged(selectedPos);
            if (selectionListener != null) selectionListener.onSelectionChanged(selectedPos);
        });
    }

    @Override public int getItemCount() { return cards.size(); }

    private static int getArtDrawable(Element e) {
        switch (e) {
            case FIRE:  return R.drawable.card_art_strength;
            case WATER: return R.drawable.card_art_agility;
            case EARTH: return R.drawable.card_art_intelligence;
            case AIR:   return R.drawable.card_art_universal;
            case CHAOS: return R.drawable.card_art_dire;
            default:    return R.drawable.card_art_strength;
        }
    }

    static class VH extends RecyclerView.ViewHolder {
        View cardRoot;
        ImageView ivArt;
        TextView tvElement, tvName, tvAtk, tvDef, tvHp, tvCost, tvAbility;

        VH(@NonNull View itemView) {
            super(itemView);
            cardRoot = itemView.findViewById(R.id.card_root);
            ivArt = itemView.findViewById(R.id.iv_art);
            tvElement = itemView.findViewById(R.id.tv_element);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAtk = itemView.findViewById(R.id.tv_atk);
            tvDef = itemView.findViewById(R.id.tv_def);
            tvHp = itemView.findViewById(R.id.tv_hp);
            tvCost = itemView.findViewById(R.id.tv_cost);
            tvAbility = itemView.findViewById(R.id.tv_ability);
        }

        void bind(Card card, boolean selected) {
            tvElement.setText(card.getElement().icon);
            tvName.setText(card.getName());
            tvAtk.setText("⚔" + card.getAttack());
            tvDef.setText("🛡" + card.getDefense());
            tvHp.setText("❤" + card.getMaxHp());
            tvCost.setText(String.valueOf(card.getCost()));
            tvAbility.setText(card.getAbility().description);

            // Load hero portrait from Dota 2 CDN
            String url = HeroImages.getUrl(card.getName());
            Glide.with(ivArt.getContext())
                .load(url)
                .placeholder(getArtDrawable(card.getElement()))
                .error(getArtDrawable(card.getElement()))
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .into(ivArt);

            GradientDrawable bg = new GradientDrawable();
            bg.setShape(GradientDrawable.RECTANGLE);
            bg.setCornerRadius(16f);
            if (selected) {
                bg.setColor(0xFF2A3050);
                bg.setStroke(5, Color.parseColor("#FFD700")); // gold border
                cardRoot.setScaleX(1.05f);
                cardRoot.setScaleY(1.05f);
            } else {
                bg.setColor(0xFF1E2433);
                bg.setStroke(3, card.getElement().color);
                cardRoot.setScaleX(1.0f);
                cardRoot.setScaleY(1.0f);
            }
            cardRoot.setBackground(bg);
        }
    }
}
