package com.example.cardgame.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Maps card names to Dota 2 official hero portrait URLs from cdn.steamstatic.com.
 * Format: https://cdn.steamstatic.com/apps/dota2/images/dota_react/heroes/crops/{key}.png
 */
public final class HeroImages {

    private static final String BASE = "https://cdn.steamstatic.com/apps/dota2/images/dota_react/heroes/crops/";

    private static final Map<String, String> URLS = new HashMap<>();

    static {
        // ── STRENGTH (Сила) ──
        put("Axe",              "axe");
        put("Dragon Knight",    "dragon_knight");
        put("Earthshaker",      "earthshaker");
        put("Pudge",            "pudge");
        put("Slardar",          "slardar");
        put("Tidehunter",       "tidehunter");
        put("Chaos Knight",     "chaos_knight");
        put("Centaur",          "centaur");
        put("Clockwerk",        "rattletrap");
        put("Bristleback",      "bristleback");
        put("Wraith King",      "skeleton_king");
        put("Legion Cmd",       "legion_commander");
        put("Mars",             "mars");
        put("Underlord",        "abyssal_underlord");
        put("Kunkka",           "kunkka");
        put("Primal Beast",     "primal_beast");
        put("Dawnbreaker",      "dawnbreaker");
        put("Spirit Breaker",   "spirit_breaker");
        put("Huskar",           "huskar");
        put("Timbersaw",        "shredder");

        // ── AGILITY (Ловкость) ──
        put("Juggernaut",       "juggernaut");
        put("Phantom Assassin", "phantom_assassin");
        put("Anti-Mage",        "antimage");
        put("Faceless Void",    "faceless_void");
        put("Bloodseeker",      "bloodseeker");
        put("Clinkz",           "clinkz");
        put("Drow Ranger",      "drow_ranger");
        put("Luna",             "luna");
        put("Mirana",           "mirana");
        put("Monkey King",      "monkey_king");
        put("Morphling",        "morphling");
        put("Naga Siren",       "naga_siren");
        put("Riki",             "riki");
        put("Slark",            "slark");
        put("Spectre",          "spectre");
        put("Ursa",             "ursa");
        put("Weaver",           "weaver");
        put("Medusa",           "medusa");
        put("Phantom Lancer",   "phantom_lancer");
        put("Gyrocopter",       "gyrocopter");

        // ── INTELLIGENCE (Интеллект) ──
        put("Crystal Maiden",   "crystal_maiden");
        put("Invoker",          "invoker");
        put("Zeus",             "zuus");
        put("Lina",             "lina");
        put("Lion",             "lion");
        put("Storm Spirit",     "storm_spirit");
        put("Puck",             "puck");
        put("Queen of Pain",    "queenofpain");
        put("Shadow Shaman",    "shadow_shaman");
        put("Warlock",          "warlock");
        put("Witch Doctor",     "witch_doctor");
        put("Rubick",           "rubick");
        put("Skywrath Mage",    "skywrath_mage");
        put("Silencer",         "silencer");
        put("Tinker",           "tinker");
        put("OD",               "obsidian_destroyer");
        put("Void Spirit",      "void_spirit");
        put("Ancient App.",     "ancient_apparition");
        put("Pugna",            "pugna");
        put("Disruptor",        "disruptor");

        // ── UNIVERSAL (Универсал) ──
        put("Dark Willow",      "dark_willow");
        put("Pangolier",        "pangolier");
        put("Grimstroke",       "grimstroke");
        put("Hoodwink",         "hoodwink");
        put("Muerta",           "muerta");
        put("Snapfire",         "snapfire");
        put("Winter Wyvern",    "winter_wyvern");
        put("Nature's Prophet", "furion");
        put("Batrider",         "batrider");
        put("Brewmaster",       "brewmaster");
        put("Beastmaster",      "beastmaster");
        put("Lycan",            "lycan");
        put("Meepo",            "meepo");
        put("Night Stalker",    "night_stalker");
        put("Enigma",           "enigma");
        put("Techies",          "techies");
        put("Chen",             "chen");
        put("Io",               "wisp");
        put("Sand King",        "sand_king");
        put("Elder Titan",      "elder_titan");

        // ── DIRE (Тьма) ──
        put("Shadow Fiend",     "nevermore");
        put("Doom",             "doom_bringer");
        put("Dark Seer",        "dark_seer");
        put("Terrorblade",      "terrorblade");
        put("Viper",            "viper");
        put("Leshrac",          "leshrac");
        put("Death Prophet",    "death_prophet");
        put("Undying",          "undying");
        put("Visage",           "visage");
        put("Broodmother",      "broodmother");
        put("Lifestealer",      "life_stealer");
        put("Bane",             "bane");
        put("Venomancer",       "venomancer");
        put("Arc Warden",       "arc_warden");
        put("Abaddon",          "abaddon");
        put("Jakiro",           "jakiro");
        put("Necrophos",        "necrolyte");
        put("Nyx Assassin",     "nyx_assassin");
        put("Shadow Demon",     "shadow_demon");
        put("Razor",            "razor");
    }

    private static void put(String name, String key) {
        URLS.put(name, BASE + key + ".png");
    }

    /** Returns the CDN portrait URL for a given hero name, or null if unknown. */
    public static String getUrl(String heroName) {
        return URLS.get(heroName);
    }

    private HeroImages() {}
}
