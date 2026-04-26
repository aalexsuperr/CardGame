package com.example.cardgame.model;

public class Card {
    private final String name;
    private final Element element;
    private final int cost;
    private final int maxHp;
    private int currentHp;
    private final int defense;
    private final int attack;
    private final Ability ability;
    private int bonusDefense = 0;
    private boolean burned = false;
    private boolean poisoned = false;
    private int burnTurns = 0;
    private int poisonTurns = 0;

    public Card(String name, Element element, int cost, int maxHp, int defense, int attack, Ability ability) {
        this.name = name;
        this.element = element;
        this.cost = cost;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.defense = defense;
        this.attack = attack;
        this.ability = ability;
    }

    public Card copy() {
        return new Card(name, element, cost, maxHp, defense, attack, ability);
    }

    public void onPlay() {
        if (ability == Ability.FORTIFY) {
            bonusDefense += 2;
        }
    }

    public int getEffectiveDefense() {
        return defense + bonusDefense;
    }

    public int getAttackAgainst(Card target) {
        int base = attack;
        Element tElem = target.getElement();
        boolean doubleStrike =
            (ability == Ability.DOUBLE_STRIKE_VS_WATER && tElem == Element.WATER) ||
            (ability == Ability.DOUBLE_STRIKE_VS_FIRE  && tElem == Element.FIRE)  ||
            (ability == Ability.DOUBLE_STRIKE_VS_EARTH && tElem == Element.EARTH) ||
            (ability == Ability.DOUBLE_STRIKE_VS_AIR   && tElem == Element.AIR)   ||
            (ability == Ability.DOUBLE_STRIKE_VS_CHAOS && tElem == Element.CHAOS);
        if (doubleStrike) base *= 2;
        return base;
    }

    public int takeDamage(int rawDamage, boolean ignoreDefense) {
        int def = ignoreDefense ? 0 : getEffectiveDefense();
        int actual = Math.max(0, rawDamage - def);
        currentHp -= actual;
        return actual;
    }

    public void heal(int amount) {
        currentHp = Math.min(maxHp, currentHp + amount);
    }

    /** End-of-turn tick. Returns bonus direct damage to apply to the enemy player (unused in base version). */
    public int tickEndOfTurn() {
        if (ability == Ability.REGEN_FIRE || ability == Ability.REGEN_WATER ||
            ability == Ability.REGEN_EARTH || ability == Ability.REGEN_AIR ||
            ability == Ability.REGEN_CHAOS) {
            heal(1);
        }
        if (burned && burnTurns > 0) {
            currentHp -= 1;
            burnTurns--;
            if (burnTurns == 0) burned = false;
        }
        if (poisoned && poisonTurns > 0) {
            currentHp -= 1;
            poisonTurns--;
            if (poisonTurns == 0) poisoned = false;
        }
        return 0;
    }

    public void applyBurn(int turns) { burned = true; burnTurns = turns; }
    public void applyPoison(int turns) { poisoned = true; poisonTurns = turns; }
    public boolean isAlive() { return currentHp > 0; }

    public String getName() { return name; }
    public Element getElement() { return element; }
    public int getCost() { return cost; }
    public int getMaxHp() { return maxHp; }
    public int getCurrentHp() { return currentHp; }
    public int getDefense() { return defense; }
    public int getAttack() { return attack; }
    public Ability getAbility() { return ability; }
    public boolean hasTaunt() { return ability == Ability.TAUNT; }
    public boolean hasLifeSteal() { return ability == Ability.LIFE_STEAL; }
    public boolean hasShieldBreak() { return ability == Ability.SHIELD_BREAK; }
    public boolean hasSplash() { return ability == Ability.SPLASH; }
    public boolean hasSwift() { return ability == Ability.SWIFT; }
    public boolean hasManaD() { return ability == Ability.MANA_DRAIN; }
}
