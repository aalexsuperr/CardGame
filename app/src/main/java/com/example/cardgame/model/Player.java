package com.example.cardgame.model;

import java.util.*;

public class Player {
    public static final int MAX_FIELD_SIZE = 6;
    public static final int STARTING_HP = 30;
    public static final int MANA_PER_TURN = 2;
    public static final int GOLD_PER_TURN = 3;

    private final String name;
    private int hp;
    private final int maxHp;
    private final Map<Element, Integer> mana = new EnumMap<>(Element.class);
    private int gold;
    private final List<Card> hand = new ArrayList<>();
    private final List<Card> field = new ArrayList<>();

    public Player(String name) {
        this.name = name;
        this.hp = STARTING_HP;
        this.maxHp = STARTING_HP;
        this.gold = 5;
        for (Element e : Element.values()) {
            mana.put(e, 2);
        }
    }

    public void addManaAllElements(int amount) {
        for (Element e : Element.values()) {
            mana.merge(e, amount, Integer::sum);
        }
    }

    public void addGold(int amount) { gold += amount; }

    public boolean spendMana(Element element, int cost) {
        int current = mana.getOrDefault(element, 0);
        if (current < cost) return false;
        mana.put(element, current - cost);
        return true;
    }

    public boolean spendGold(int cost) {
        if (gold < cost) return false;
        gold -= cost;
        return true;
    }

    public void drainMana(Element element, int amount) {
        mana.put(element, Math.max(0, mana.getOrDefault(element, 0) - amount));
    }

    public void addToHand(Card card) { hand.add(card); }

    public Card removeFromHand(int index) {
        if (index < 0 || index >= hand.size()) return null;
        return hand.remove(index);
    }

    public boolean playCard(Card card) {
        if (field.size() >= MAX_FIELD_SIZE) return false;
        card.onPlay();
        field.add(card);
        return true;
    }

    public void removeDeadFromField() {
        field.removeIf(c -> !c.isAlive());
    }

    public void takeDamage(int amount) { hp = Math.max(0, hp - amount); }
    public void heal(int amount) { hp = Math.min(maxHp, hp + amount); }
    public boolean isAlive() { return hp > 0; }

    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getGold() { return gold; }
    public int getMana(Element e) { return mana.getOrDefault(e, 0); }
    public Map<Element, Integer> getManaMap() { return Collections.unmodifiableMap(mana); }
    public List<Card> getHand() { return Collections.unmodifiableList(hand); }
    public List<Card> getField() { return Collections.unmodifiableList(field); }
    public List<Card> getFieldMutable() { return field; }
}
