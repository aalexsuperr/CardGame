package com.example.cardgame.model;

import java.util.*;

public class Deck {
    private final Deque<Card> cards = new ArrayDeque<>();

    public Deck() {
        List<Card> all = new ArrayList<>();
        all.addAll(buildFireCards());
        all.addAll(buildWaterCards());
        all.addAll(buildEarthCards());
        all.addAll(buildAirCards());
        all.addAll(buildChaosCards());
        Collections.shuffle(all);
        cards.addAll(all);
    }

    // ── STRENGTH (Сила) ── STR Dota 2 heroes
    private List<Card> buildFireCards() {
        List<Card> l = new ArrayList<>();
        l.add(new Card("Axe",              Element.FIRE, 2, 8,  1, 5, Ability.BURN));
        l.add(new Card("Dragon Knight",    Element.FIRE, 4,14,  4, 5, Ability.TAUNT));
        l.add(new Card("Earthshaker",      Element.FIRE, 3,10,  2, 4, Ability.SPLASH));
        l.add(new Card("Pudge",            Element.FIRE, 3,13,  3, 4, Ability.BURN));
        l.add(new Card("Slardar",          Element.FIRE, 3,10,  2, 5, Ability.SHIELD_BREAK));
        l.add(new Card("Tidehunter",       Element.FIRE, 4,14,  5, 3, Ability.TAUNT));
        l.add(new Card("Chaos Knight",     Element.FIRE, 3, 9,  1, 5, Ability.SWIFT));
        l.add(new Card("Centaur",          Element.FIRE, 4,12,  3, 5, Ability.SPLASH));
        l.add(new Card("Clockwerk",        Element.FIRE, 3,11,  4, 3, Ability.TAUNT));
        l.add(new Card("Bristleback",      Element.FIRE, 3,12,  4, 2, Ability.REGEN_FIRE));
        l.add(new Card("Wraith King",      Element.FIRE, 4,12,  3, 5, Ability.NECROMANCY));
        l.add(new Card("Legion Cmd",       Element.FIRE, 4,11,  2, 6, Ability.DOUBLE_STRIKE_VS_WATER));
        l.add(new Card("Mars",             Element.FIRE, 4,13,  5, 4, Ability.FORTIFY));
        l.add(new Card("Underlord",        Element.FIRE, 4,14,  4, 3, Ability.MANA_DRAIN));
        l.add(new Card("Kunkka",           Element.FIRE, 4,11,  3, 6, Ability.SPLASH));
        l.add(new Card("Primal Beast",     Element.FIRE, 5,16,  5, 5, Ability.TAUNT));
        l.add(new Card("Dawnbreaker",      Element.FIRE, 3,10,  2, 5, Ability.REGEN_FIRE));
        l.add(new Card("Spirit Breaker",   Element.FIRE, 3, 9,  1, 6, Ability.SWIFT));
        l.add(new Card("Huskar",           Element.FIRE, 3,11,  2, 6, Ability.LIFE_STEAL));
        l.add(new Card("Timbersaw",        Element.FIRE, 3,10,  3, 5, Ability.BURN));
        return l;
    }

    // ── AGILITY (Ловкость) ── AGI Dota 2 heroes
    private List<Card> buildWaterCards() {
        List<Card> l = new ArrayList<>();
        l.add(new Card("Juggernaut",       Element.WATER, 3, 9, 1, 6, Ability.SWIFT));
        l.add(new Card("Phantom Assassin", Element.WATER, 3, 8, 1, 7, Ability.LIFE_STEAL));
        l.add(new Card("Anti-Mage",        Element.WATER, 2, 7, 1, 5, Ability.MANA_DRAIN));
        l.add(new Card("Faceless Void",    Element.WATER, 4,12, 3, 6, Ability.SWIFT));
        l.add(new Card("Bloodseeker",      Element.WATER, 3,10, 1, 6, Ability.LIFE_STEAL));
        l.add(new Card("Clinkz",           Element.WATER, 2, 6, 0, 6, Ability.SWIFT));
        l.add(new Card("Drow Ranger",      Element.WATER, 3, 8, 1, 6, Ability.DOUBLE_STRIKE_VS_FIRE));
        l.add(new Card("Luna",             Element.WATER, 3, 9, 1, 5, Ability.SPLASH));
        l.add(new Card("Mirana",           Element.WATER, 3, 9, 2, 5, Ability.DOUBLE_STRIKE_VS_FIRE));
        l.add(new Card("Monkey King",      Element.WATER, 3,10, 2, 6, Ability.SWIFT));
        l.add(new Card("Morphling",        Element.WATER, 4,12, 2, 6, Ability.REGEN_WATER));
        l.add(new Card("Naga Siren",       Element.WATER, 4,12, 3, 5, Ability.SPLASH));
        l.add(new Card("Riki",             Element.WATER, 2, 7, 1, 5, Ability.SHIELD_BREAK));
        l.add(new Card("Slark",            Element.WATER, 3,10, 1, 6, Ability.LIFE_STEAL));
        l.add(new Card("Spectre",          Element.WATER, 4,12, 2, 6, Ability.SPLASH));
        l.add(new Card("Ursa",             Element.WATER, 3,11, 2, 7, Ability.DOUBLE_STRIKE_VS_FIRE));
        l.add(new Card("Weaver",           Element.WATER, 2, 7, 0, 5, Ability.REGEN_WATER));
        l.add(new Card("Medusa",           Element.WATER, 5,14, 3, 6, Ability.TAUNT));
        l.add(new Card("Phantom Lancer",   Element.WATER, 4,11, 2, 5, Ability.SPLASH));
        l.add(new Card("Gyrocopter",       Element.WATER, 4,10, 2, 6, Ability.SPLASH));
        return l;
    }

    // ── INTELLIGENCE (Интеллект) ── INT Dota 2 heroes
    private List<Card> buildEarthCards() {
        List<Card> l = new ArrayList<>();
        l.add(new Card("Crystal Maiden",   Element.EARTH, 2, 6, 0, 4, Ability.MANA_DRAIN));
        l.add(new Card("Invoker",          Element.EARTH, 5,11, 2, 7, Ability.SPLASH));
        l.add(new Card("Zeus",             Element.EARTH, 3, 8, 1, 6, Ability.SPLASH));
        l.add(new Card("Lina",             Element.EARTH, 3, 8, 1, 6, Ability.BURN));
        l.add(new Card("Lion",             Element.EARTH, 2, 7, 0, 5, Ability.MANA_DRAIN));
        l.add(new Card("Storm Spirit",     Element.EARTH, 3, 9, 1, 6, Ability.SWIFT));
        l.add(new Card("Puck",             Element.EARTH, 2, 7, 1, 5, Ability.SWIFT));
        l.add(new Card("Queen of Pain",    Element.EARTH, 3, 9, 1, 6, Ability.POISON));
        l.add(new Card("Shadow Shaman",    Element.EARTH, 2, 7, 0, 4, Ability.TAUNT));
        l.add(new Card("Warlock",          Element.EARTH, 3, 9, 1, 4, Ability.SPLASH));
        l.add(new Card("Witch Doctor",     Element.EARTH, 2, 7, 0, 4, Ability.POISON));
        l.add(new Card("Rubick",           Element.EARTH, 3, 9, 1, 5, Ability.NONE));
        l.add(new Card("Skywrath Mage",    Element.EARTH, 2, 6, 0, 6, Ability.DOUBLE_STRIKE_VS_EARTH));
        l.add(new Card("Silencer",         Element.EARTH, 3, 9, 2, 5, Ability.MANA_DRAIN));
        l.add(new Card("Tinker",           Element.EARTH, 3, 8, 1, 6, Ability.SWIFT));
        l.add(new Card("OD",               Element.EARTH, 4,12, 2, 7, Ability.SHIELD_BREAK));
        l.add(new Card("Void Spirit",      Element.EARTH, 3,10, 2, 6, Ability.SWIFT));
        l.add(new Card("Ancient App.",     Element.EARTH, 2, 6, 0, 5, Ability.BURN));
        l.add(new Card("Pugna",            Element.EARTH, 3, 8, 1, 5, Ability.MANA_DRAIN));
        l.add(new Card("Disruptor",        Element.EARTH, 3, 9, 2, 5, Ability.DOUBLE_STRIKE_VS_EARTH));
        return l;
    }

    // ── UNIVERSAL (Универсал) ── Universal Dota 2 heroes
    private List<Card> buildAirCards() {
        List<Card> l = new ArrayList<>();
        l.add(new Card("Dark Willow",      Element.AIR, 2, 7, 0, 5, Ability.POISON));
        l.add(new Card("Pangolier",        Element.AIR, 3,10, 3, 5, Ability.SWIFT));
        l.add(new Card("Grimstroke",       Element.AIR, 3, 8, 1, 5, Ability.LIFE_STEAL));
        l.add(new Card("Hoodwink",         Element.AIR, 2, 7, 1, 5, Ability.DOUBLE_STRIKE_VS_EARTH));
        l.add(new Card("Muerta",           Element.AIR, 3, 9, 1, 6, Ability.LIFE_STEAL));
        l.add(new Card("Snapfire",         Element.AIR, 3,10, 2, 5, Ability.SPLASH));
        l.add(new Card("Winter Wyvern",    Element.AIR, 4,12, 3, 5, Ability.TAUNT));
        l.add(new Card("Nature's Prophet", Element.AIR, 3, 9, 2, 4, Ability.REGEN_AIR));
        l.add(new Card("Batrider",         Element.AIR, 3, 9, 1, 5, Ability.BURN));
        l.add(new Card("Brewmaster",       Element.AIR, 4,13, 3, 5, Ability.FORTIFY));
        l.add(new Card("Beastmaster",      Element.AIR, 3,11, 3, 4, Ability.TAUNT));
        l.add(new Card("Lycan",            Element.AIR, 4,12, 2, 6, Ability.SWIFT));
        l.add(new Card("Meepo",            Element.AIR, 3,10, 2, 5, Ability.SPLASH));
        l.add(new Card("Night Stalker",    Element.AIR, 4,13, 3, 6, Ability.DOUBLE_STRIKE_VS_EARTH));
        l.add(new Card("Enigma",           Element.AIR, 4,10, 1, 6, Ability.SPLASH));
        l.add(new Card("Techies",          Element.AIR, 3, 7, 0, 7, Ability.NECROMANCY));
        l.add(new Card("Chen",             Element.AIR, 2, 8, 1, 4, Ability.REGEN_AIR));
        l.add(new Card("Io",               Element.AIR, 1, 5, 0, 2, Ability.REGEN_AIR));
        l.add(new Card("Sand King",        Element.AIR, 4,12, 3, 6, Ability.SPLASH));
        l.add(new Card("Elder Titan",      Element.AIR, 5,14, 4, 6, Ability.SPLASH));
        return l;
    }

    // ── DIRE (Тьма) ── Dark/Dire Dota 2 heroes
    private List<Card> buildChaosCards() {
        List<Card> l = new ArrayList<>();
        l.add(new Card("Shadow Fiend",     Element.CHAOS, 4,10, 1, 8, Ability.LIFE_STEAL));
        l.add(new Card("Doom",             Element.CHAOS, 5,15, 4, 6, Ability.BURN));
        l.add(new Card("Dark Seer",        Element.CHAOS, 3,10, 2, 5, Ability.MANA_DRAIN));
        l.add(new Card("Terrorblade",      Element.CHAOS, 4,11, 2, 7, Ability.DOUBLE_STRIKE_VS_CHAOS));
        l.add(new Card("Viper",            Element.CHAOS, 3, 9, 1, 6, Ability.POISON));
        l.add(new Card("Leshrac",          Element.CHAOS, 3, 9, 1, 6, Ability.BURN));
        l.add(new Card("Death Prophet",    Element.CHAOS, 3,10, 2, 6, Ability.SPLASH));
        l.add(new Card("Undying",          Element.CHAOS, 3,11, 3, 5, Ability.NECROMANCY));
        l.add(new Card("Visage",           Element.CHAOS, 4,12, 3, 5, Ability.TAUNT));
        l.add(new Card("Broodmother",      Element.CHAOS, 3,10, 2, 6, Ability.SPLASH));
        l.add(new Card("Lifestealer",      Element.CHAOS, 3,12, 3, 5, Ability.LIFE_STEAL));
        l.add(new Card("Bane",             Element.CHAOS, 3, 9, 1, 5, Ability.MANA_DRAIN));
        l.add(new Card("Venomancer",       Element.CHAOS, 3, 9, 1, 5, Ability.POISON));
        l.add(new Card("Arc Warden",       Element.CHAOS, 4,11, 2, 6, Ability.DOUBLE_STRIKE_VS_CHAOS));
        l.add(new Card("Abaddon",          Element.CHAOS, 3,11, 3, 5, Ability.REGEN_CHAOS));
        l.add(new Card("Jakiro",           Element.CHAOS, 3, 9, 2, 5, Ability.BURN));
        l.add(new Card("Necrophos",        Element.CHAOS, 3,11, 2, 5, Ability.POISON));
        l.add(new Card("Nyx Assassin",     Element.CHAOS, 3, 8, 1, 6, Ability.SHIELD_BREAK));
        l.add(new Card("Shadow Demon",     Element.CHAOS, 4,10, 1, 6, Ability.LIFE_STEAL));
        l.add(new Card("Razor",            Element.CHAOS, 4,11, 2, 7, Ability.MANA_DRAIN));
        return l;
    }

    public Card draw() {
        return cards.isEmpty() ? null : cards.poll();
    }

    public boolean isEmpty() { return cards.isEmpty(); }
    public int size() { return cards.size(); }
}
