package com.example.cardgame.adapter;

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
import java.util.List;

public class CardFieldAdapter extends RecyclerView.Adapter<CardFieldAdapter.VH> {

    public interface OnCardClickListener { void onClick(Card card); }

    private List<Card> cards;
    private OnCardClickListener clickListener;

    public CardFieldAdapter(List<Card> cards) {
        this.cards = cards;
    }

    public void setCards(List<Card> cards) { this.cards = cards; }
    public void setOnCardClickListener(OnCardClickListener l) { this.clickListener = l; }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_field, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        Card card = cards.get(position);
        h.bind(card);
        h.itemView.setOnClickListener(v -> { if (clickListener != null) clickListener.onClick(card); });
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
        TextView tvElement, tvName, tvAtk, tvDef, tvHp;
        ProgressBar pbHp;
        ImageView ivArt;
        View cardRoot;

        VH(@NonNull View itemView) {
            super(itemView);
            cardRoot = itemView.findViewById(R.id.card_root);
            tvElement = itemView.findViewById(R.id.tv_element);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAtk = itemView.findViewById(R.id.tv_atk);
            tvDef = itemView.findViewById(R.id.tv_def);
            tvHp = itemView.findViewById(R.id.tv_hp);
            pbHp = itemView.findViewById(R.id.pb_hp);
            ivArt = itemView.findViewById(R.id.iv_art);
        }

        void bind(Card card) {
            tvElement.setText(card.getElement().icon);
            tvName.setText(card.getName());
            tvAtk.setText("⚔" + card.getAttack());
            tvDef.setText("🛡" + card.getEffectiveDefense());
            tvHp.setText(card.getCurrentHp() + "/" + card.getMaxHp());
            pbHp.setMax(card.getMaxHp());
            pbHp.setProgress(card.getCurrentHp());

            // Load hero portrait from Dota 2 CDN
            String url = HeroImages.getUrl(card.getName());
            Glide.with(ivArt.getContext())
                .load(url)
                .placeholder(getArtDrawable(card.getElement()))
                .error(getArtDrawable(card.getElement()))
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .into(ivArt);

            // Element-colored border
            GradientDrawable bg = new GradientDrawable();
            bg.setShape(GradientDrawable.RECTANGLE);
            bg.setCornerRadius(16f);
            bg.setColor(0xFF1E2433);
            bg.setStroke(4, card.getElement().color);
            cardRoot.setBackground(bg);
        }
    }
}
