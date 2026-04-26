package com.example.cardgame.engine;

import com.example.cardgame.model.*;
import java.util.*;

public class GameEngine {
    private final Player player1;
    private final Player player2;
    private final Deck deck;
    private int turnNumber = 1;
    private boolean gameOver = false;
    private Player winner = null;
    private final List<String> eventLog = new ArrayList<>();

    // Initial hand size per player (drawn from shuffled deck)
    private static final int INITIAL_HAND = 25;

    public GameEngine(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.deck = new Deck();
        dealInitialHands();
    }

    private void dealInitialHands() {
        for (int i = 0; i < INITIAL_HAND; i++) {
            Card c1 = deck.draw();
            Card c2 = deck.draw();
            if (c1 != null) player1.addToHand(c1);
            if (c2 != null) player2.addToHand(c2);
        }
    }

    // ---- Turn lifecycle ----

    public void startTurn(Player active) {
        eventLog.clear();
        active.addManaAllElements(Player.MANA_PER_TURN);
        active.addGold(Player.GOLD_PER_TURN);
        Card drawn = deck.draw();
        if (drawn != null) {
            active.addToHand(drawn);
            log("Вы тянете карту: " + drawn.getName() + " [" + drawn.getElement() + "]");
        } else {
            log("Колода пуста!");
        }
        log("Добавлено +" + Player.MANA_PER_TURN + " маны ко всем стихиям и +" + Player.GOLD_PER_TURN + " золота");
    }

    public boolean playCard(Player active, int handIndex, boolean payWithGold) {
        eventLog.clear();
        List<Card> hand = active.getHand();
        if (handIndex < 0 || handIndex >= hand.size()) return false;
        if (active.getFieldMutable().size() >= Player.MAX_FIELD_SIZE) {
            log("Поле полное! Нельзя выложить больше " + Player.MAX_FIELD_SIZE + " карт.");
            return false;
        }
        Card card = hand.get(handIndex);
        boolean paid;
        if (payWithGold) {
            paid = active.spendGold(card.getCost());
        } else {
            paid = active.spendMana(card.getElement(), card.getCost());
        }
        if (!paid) return false;

        Card removed = active.removeFromHand(handIndex);
        active.playCard(removed);
        log(active.getName() + " выкладывает: " + removed.getName() + " [" + removed.getElement() + "]");
        if (removed.getAbility() != Ability.NONE) {
            log("Активируется: " + removed.getAbility().description);
        }
        return true;
    }

    public void executeAttacks(Player attacker, Player defender) {
        eventLog.clear();
        List<Card> attackers = new ArrayList<>(attacker.getFieldMutable());
        List<Card> defenderField = defender.getFieldMutable();

        if (attackers.isEmpty()) {
            log("Нет карт для атаки!");
            return;
        }

        for (Card atk : attackers) {
            if (!atk.isAlive()) continue;
            performCardAttack(atk, attacker, defenderField, defender);
        }

        attacker.removeDeadFromField();
        defender.removeDeadFromField();
        checkWinCondition();
    }

    private void performCardAttack(Card atk, Player attackerPlayer, List<Card> defenderField, Player defender) {
        if (atk.hasSplash() && !defenderField.isEmpty()) {
            log(atk.getName() + " — ВСПЛЕСК!");
            for (Card t : new ArrayList<>(defenderField)) {
                int dmg = t.takeDamage(atk.getAttackAgainst(t), atk.hasShieldBreak());
                log("  → " + t.getName() + " −" + dmg + " ХП (осталось " + Math.max(0, t.getCurrentHp()) + ")");
                if (atk.hasLifeSteal()) { atk.heal(dmg / 2); }
                handleDeathEffects(t, defender);
            }
            return;
        }

        Card target = chooseBestTarget(defenderField);
        if (target == null) {
            int atkVal = atk.getAttack();
            defender.takeDamage(atkVal);
            log(atk.getName() + " бьёт игрока " + defender.getName() + " напрямую — " + atkVal + " урона! (ХП: " + defender.getHp() + ")");
            if (atk.hasManaD()) {
                Element r = Element.values()[new Random().nextInt(Element.values().length)];
                defender.drainMana(r, 1);
                log("  → Истощение маны " + r + " у " + defender.getName());
            }
            return;
        }

        int atkVal = atk.getAttackAgainst(target);
        int dmg = target.takeDamage(atkVal, atk.hasShieldBreak());
        log(atk.getName() + " → " + target.getName() + ": " + atkVal + " урона (" + dmg + " с учётом защиты), ХП: " + Math.max(0, target.getCurrentHp()));

        if (atk.hasLifeSteal() && dmg > 0) {
            atk.heal(dmg / 2);
            log("  → Вампиризм: +" + (dmg / 2) + " ХП для " + atk.getName());
        }
        if (atk.getAbility() == Ability.BURN)   { target.applyBurn(3);   log("  → " + target.getName() + " подожжён!"); }
        if (atk.getAbility() == Ability.POISON) { target.applyPoison(3); log("  → " + target.getName() + " отравлен!"); }
        if (atk.hasManaD()) {
            defender.drainMana(target.getElement(), 1);
            log("  → Истощение маны " + target.getElement() + " у " + defender.getName());
        }
        if (atk.hasSwift() && target.isAlive()) {
            int dmg2 = target.takeDamage(atkVal, atk.hasShieldBreak());
            log("  → Быстрота: второй удар — " + dmg2 + " урона");
        }
        handleDeathEffects(target, defender);
    }

    private Card chooseBestTarget(List<Card> field) {
        if (field.isEmpty()) return null;
        for (Card c : field) if (c.hasTaunt() && c.isAlive()) return c;
        return field.stream().filter(Card::isAlive)
            .min(Comparator.comparingInt(Card::getCurrentHp)).orElse(null);
    }

    private void handleDeathEffects(Card dead, Player defender) {
        if (!dead.isAlive() && dead.getAbility() == Ability.NECROMANCY) {
            defender.takeDamage(3);
            log("  → НЕКРОМАНТИЯ: " + dead.getName() + " наносит 3 урона " + defender.getName() + "! (ХП: " + defender.getHp() + ")");
        }
    }

    public void tickFieldEffects(Player player) {
        for (Card c : player.getFieldMutable()) {
            c.tickEndOfTurn();
        }
        player.removeDeadFromField();
    }

    private void checkWinCondition() {
        if (!player1.isAlive() && !player2.isAlive()) { gameOver = true; winner = null; }
        else if (!player1.isAlive()) { gameOver = true; winner = player2; }
        else if (!player2.isAlive()) { gameOver = true; winner = player1; }
    }

    private void log(String msg) { eventLog.add(msg); }

    public boolean isGameOver() { return gameOver; }
    public Player getWinner() { return winner; }
    public List<String> getEventLog() { return Collections.unmodifiableList(eventLog); }
    public Player getPlayer1() { return player1; }
    public Player getPlayer2() { return player2; }
    public int getTurnNumber() { return turnNumber; }
    public void incrementTurn() { turnNumber++; }
    public Deck getDeck() { return deck; }
}
