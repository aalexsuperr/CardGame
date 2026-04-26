package com.example.cardgame;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import com.example.cardgame.adapter.CardFieldAdapter;
import com.example.cardgame.adapter.CardHandAdapter;
import com.example.cardgame.engine.GameEngine;
import com.example.cardgame.model.*;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    private GameEngine engine;
    private Player[] players;
    private int activeIndex = 0;
    private int selectedHandIndex = -1;
    private boolean cardPlayedThisTurn = false;
    private boolean attackDoneThisTurn = false;

    // Opponent views
    private TextView tvOpponentName, tvOpponentHp, tvOpponentMana;
    private ProgressBar pbOpponentHp;
    private RecyclerView rvOpponentField;
    private CardFieldAdapter opponentFieldAdapter;

    // Middle
    private TextView tvTurnInfo, tvDeckCount;

    // Player views
    private RecyclerView rvPlayerField;
    private CardFieldAdapter playerFieldAdapter;
    private TextView tvPlayerName, tvPlayerHp, tvPlayerMana;
    private ProgressBar pbPlayerHp;

    // Hand
    private RecyclerView rvPlayerHand;
    private CardHandAdapter handAdapter;

    // Action buttons
    private Button btnPlayCard, btnAttack, btnEndTurn;

    // Handoff overlay
    private View handoffOverlay;
    private TextView tvHandoffTitle, tvHandoffMessage;
    private Button btnHandoffOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        String name1 = getIntent().getStringExtra("player1_name");
        String name2 = getIntent().getStringExtra("player2_name");
        Player p1 = new Player(name1 != null ? name1 : "Игрок 1");
        Player p2 = new Player(name2 != null ? name2 : "Игрок 2");
        engine = new GameEngine(p1, p2);
        players = new Player[]{p1, p2};

        bindViews();
        setupRecyclerViews();
        setupButtons();

        // Show handoff overlay for first turn
        showHandoff(true);
    }

    // -------------------------------------------------------------------------
    // Setup
    // -------------------------------------------------------------------------

    private void bindViews() {
        tvOpponentName = findViewById(R.id.tv_opponent_name);
        tvOpponentHp   = findViewById(R.id.tv_opponent_hp);
        tvOpponentMana = findViewById(R.id.tv_opponent_mana);
        pbOpponentHp   = findViewById(R.id.pb_opponent_hp);
        rvOpponentField = findViewById(R.id.rv_opponent_field);

        tvTurnInfo  = findViewById(R.id.tv_turn_info);
        tvDeckCount = findViewById(R.id.tv_deck_count);

        rvPlayerField = findViewById(R.id.rv_player_field);
        tvPlayerName  = findViewById(R.id.tv_player_name);
        tvPlayerHp    = findViewById(R.id.tv_player_hp);
        tvPlayerMana  = findViewById(R.id.tv_player_mana);
        pbPlayerHp    = findViewById(R.id.pb_player_hp);

        rvPlayerHand = findViewById(R.id.rv_player_hand);

        btnPlayCard = findViewById(R.id.btn_play_card);
        btnAttack   = findViewById(R.id.btn_attack);
        btnEndTurn  = findViewById(R.id.btn_end_turn);

        handoffOverlay  = findViewById(R.id.handoff_overlay);
        tvHandoffTitle   = findViewById(R.id.tv_handoff_title);
        tvHandoffMessage = findViewById(R.id.tv_handoff_message);
        btnHandoffOk     = findViewById(R.id.btn_handoff_ok);
    }

    private void setupRecyclerViews() {
        // Opponent field (player index 1 initially)
        opponentFieldAdapter = new CardFieldAdapter(players[1].getFieldMutable());
        rvOpponentField.setLayoutManager(
            new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvOpponentField.setAdapter(opponentFieldAdapter);
        opponentFieldAdapter.setOnCardClickListener(this::showCardDetail);

        // Player field (player index 0 initially)
        playerFieldAdapter = new CardFieldAdapter(players[0].getFieldMutable());
        rvPlayerField.setLayoutManager(
            new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvPlayerField.setAdapter(playerFieldAdapter);
        playerFieldAdapter.setOnCardClickListener(this::showCardDetail);

        // Hand (player index 0 initially)
        handAdapter = new CardHandAdapter(new ArrayList<>(players[0].getHand()));
        rvPlayerHand.setLayoutManager(
            new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvPlayerHand.setAdapter(handAdapter);
        handAdapter.setOnSelectionChangedListener(pos -> {
            selectedHandIndex = pos;
            updateButtonStates();
        });
    }

    private void setupButtons() {
        btnPlayCard.setOnClickListener(v -> onPlayCard());
        btnAttack.setOnClickListener(v -> onAttack());
        btnEndTurn.setOnClickListener(v -> onEndTurn());
        btnHandoffOk.setOnClickListener(v -> {
            handoffOverlay.setVisibility(View.GONE);
            startTurn();
        });
    }

    // -------------------------------------------------------------------------
    // Turn flow
    // -------------------------------------------------------------------------

    private void showHandoff(boolean isFirst) {
        Player active = currentPlayer();
        tvHandoffTitle.setText("Ход " + engine.getTurnNumber());
        String msg = isFirst
            ? "Начинает " + active.getName() + "!\n\nПередайте устройство и нажмите «Я готов»."
            : "Очередь игрока:\n" + active.getName() + "\n\nПередайте устройство и нажмите «Я готов».";
        tvHandoffMessage.setText(msg);
        handoffOverlay.setVisibility(View.VISIBLE);
    }

    private void startTurn() {
        engine.startTurn(currentPlayer());
        refreshAllAdapters();
        refreshUI();
        List<String> log = engine.getEventLog();
        if (!log.isEmpty()) showEventLog("Начало хода", log);
    }

    private void onPlayCard() {
        if (cardPlayedThisTurn) {
            Toast.makeText(this, "В этот ход карта уже выложена", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedHandIndex < 0) {
            Toast.makeText(this, "Нажмите на карту в руке для выбора", Toast.LENGTH_SHORT).show();
            return;
        }
        if (currentPlayer().getFieldMutable().size() >= Player.MAX_FIELD_SIZE) {
            Toast.makeText(this, "Поле полное! Максимум " + Player.MAX_FIELD_SIZE + " карт", Toast.LENGTH_SHORT).show();
            return;
        }
        List<Card> hand = currentPlayer().getHand();
        if (selectedHandIndex >= hand.size()) {
            selectedHandIndex = -1;
            updateButtonStates();
            return;
        }
        showPaymentDialog(hand.get(selectedHandIndex), selectedHandIndex);
    }

    private void showPaymentDialog(Card card, int handIndex) {
        Player active = currentPlayer();
        int cost = card.getCost();
        boolean canMana = active.getMana(card.getElement()) >= cost;
        boolean canGold = active.getGold() >= cost;

        String manaLabel = card.getElement().icon + " Мана " + card.getElement()
            + " (" + cost + ")" + (canMana ? "" : " — недостаточно");
        String goldLabel = "💰 Золото (" + cost + ")" + (canGold ? "" : " — недостаточно");

        AlertDialog dialog = new MaterialAlertDialogBuilder(this)
            .setTitle(card.getName())
            .setMessage("Стихия: " + card.getElement() + "\n⚔" + card.getAttack()
                + "  🛡" + card.getDefense() + "  ❤" + card.getMaxHp()
                + "\n\nОплатить картой:")
            .setPositiveButton(manaLabel, null)
            .setNegativeButton(goldLabel, null)
            .setNeutralButton("Отмена", null)
            .create();

        dialog.setOnShowListener(d -> {
            Button btnMana = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            Button btnGold = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            if (!canMana) btnMana.setEnabled(false);
            if (!canGold) btnGold.setEnabled(false);

            btnMana.setOnClickListener(v -> {
                if (engine.playCard(active, handIndex, false)) {
                    onCardPlayed(dialog);
                }
            });
            btnGold.setOnClickListener(v -> {
                if (engine.playCard(active, handIndex, true)) {
                    onCardPlayed(dialog);
                }
            });
        });
        dialog.show();
    }

    private void onCardPlayed(AlertDialog dialog) {
        dialog.dismiss();
        cardPlayedThisTurn = true;
        selectedHandIndex = -1;
        handAdapter.clearSelection();
        refreshAllAdapters();
        refreshUI();
        showEventLog("Карта выложена", engine.getEventLog());
    }

    private void onAttack() {
        if (attackDoneThisTurn) {
            Toast.makeText(this, "Вы уже атаковали в этот ход", Toast.LENGTH_SHORT).show();
            return;
        }
        if (currentPlayer().getField().isEmpty()) {
            Toast.makeText(this, "Нет карт на поле для атаки!", Toast.LENGTH_SHORT).show();
            return;
        }
        engine.executeAttacks(currentPlayer(), opponent());
        attackDoneThisTurn = true;
        refreshAllAdapters();
        refreshUI();
        showEventLog("Результат атаки", engine.getEventLog());
        if (engine.isGameOver()) showGameOver();
    }

    private void onEndTurn() {
        engine.tickFieldEffects(currentPlayer());
        engine.tickFieldEffects(opponent());
        activeIndex = 1 - activeIndex;
        engine.incrementTurn();
        cardPlayedThisTurn = false;
        attackDoneThisTurn = false;
        selectedHandIndex = -1;
        showHandoff(false);
    }

    // -------------------------------------------------------------------------
    // UI refresh
    // -------------------------------------------------------------------------

    private void refreshAllAdapters() {
        playerFieldAdapter.setCards(players[activeIndex].getFieldMutable());
        opponentFieldAdapter.setCards(players[1 - activeIndex].getFieldMutable());
        playerFieldAdapter.notifyDataSetChanged();
        opponentFieldAdapter.notifyDataSetChanged();

        handAdapter.setCards(new ArrayList<>(currentPlayer().getHand()));
        handAdapter.notifyDataSetChanged();
    }

    private void refreshUI() {
        Player active = currentPlayer();
        Player opp = opponent();

        // Opponent
        tvOpponentName.setText(opp.getName());
        tvOpponentHp.setText(opp.getHp() + "/" + opp.getMaxHp() + " ❤");
        pbOpponentHp.setMax(opp.getMaxHp());
        pbOpponentHp.setProgress(opp.getHp());
        tvOpponentMana.setText(buildManaString(opp));
        setHpBarColor(pbOpponentHp, opp.getHp(), opp.getMaxHp());

        // Turn info
        tvTurnInfo.setText("Ход " + engine.getTurnNumber() + " • " + active.getName());
        tvDeckCount.setText("Колода: " + engine.getDeck().size());

        // Player
        tvPlayerName.setText(active.getName());
        tvPlayerHp.setText(active.getHp() + "/" + active.getMaxHp() + " ❤");
        pbPlayerHp.setMax(active.getMaxHp());
        pbPlayerHp.setProgress(active.getHp());
        tvPlayerMana.setText(buildManaString(active));
        setHpBarColor(pbPlayerHp, active.getHp(), active.getMaxHp());

        updateButtonStates();
    }

    private String buildManaString(Player p) {
        StringBuilder sb = new StringBuilder();
        for (Element e : Element.values()) {
            sb.append(e.icon).append(p.getMana(e)).append(" ");
        }
        sb.append("  💰").append(p.getGold());
        return sb.toString();
    }

    private void setHpBarColor(ProgressBar pb, int hp, int maxHp) {
        double ratio = (double) hp / maxHp;
        int color;
        if (ratio > 0.5) color = Color.parseColor("#4CAF50");
        else if (ratio > 0.25) color = Color.parseColor("#FF9800");
        else color = Color.parseColor("#F44336");
        pb.getProgressDrawable().setColorFilter(color,
            android.graphics.PorterDuff.Mode.SRC_IN);
    }

    private void updateButtonStates() {
        boolean canPlay = !cardPlayedThisTurn && selectedHandIndex >= 0;
        boolean canAttack = !attackDoneThisTurn && !currentPlayer().getField().isEmpty();

        btnPlayCard.setEnabled(canPlay);
        btnAttack.setEnabled(canAttack);
        btnPlayCard.setAlpha(canPlay ? 1.0f : 0.4f);
        btnAttack.setAlpha(canAttack ? 1.0f : 0.4f);
    }

    // -------------------------------------------------------------------------
    // Dialogs
    // -------------------------------------------------------------------------

    private void showEventLog(String title, List<String> log) {
        if (log.isEmpty()) return;
        StringBuilder sb = new StringBuilder();
        for (String line : log) sb.append("• ").append(line).append("\n");
        new MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setMessage(sb.toString())
            .setPositiveButton("ОК", null)
            .show();
    }

    private void showCardDetail(Card card) {
        String detail = card.getElement().icon + " " + card.getElement()
            + "\n⚔ Атака: " + card.getAttack()
            + "\n🛡 Защита: " + card.getEffectiveDefense()
            + "\n❤ ХП: " + card.getCurrentHp() + "/" + card.getMaxHp()
            + "\n💰 Стоимость: " + card.getCost()
            + "\n\n✨ Способность:\n" + card.getAbility().description;
        new MaterialAlertDialogBuilder(this)
            .setTitle(card.getName())
            .setMessage(detail)
            .setPositiveButton("ОК", null)
            .show();
    }

    private void showGameOver() {
        Player winner = engine.getWinner();
        String msg = winner == null
            ? "⚔ Ничья! Оба игрока пали в сражении."
            : "🏆 Победитель: " + winner.getName() + "!";
        new MaterialAlertDialogBuilder(this)
            .setTitle("Игра окончена!")
            .setMessage(msg + "\n\nХод " + engine.getTurnNumber())
            .setCancelable(false)
            .setPositiveButton("В главное меню", (d, w) -> finish())
            .setNegativeButton("Новая игра", (d, w) -> {
                finish();
                startActivity(getIntent());
            })
            .show();
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private Player currentPlayer() { return players[activeIndex]; }
    private Player opponent() { return players[1 - activeIndex]; }

    @Override
    public void onBackPressed() {
        new MaterialAlertDialogBuilder(this)
            .setTitle("Выйти из игры?")
            .setMessage("Прогресс текущей партии будет потерян.")
            .setPositiveButton("Выйти", (d, w) -> finish())
            .setNegativeButton("Продолжить", null)
            .show();
    }
}
