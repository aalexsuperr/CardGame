package com.example.cardgame.model;

public enum Element {
    FIRE("Сила",       "⚔",  0xFFFF6B35),   // Strength (STR)
    WATER("Ловкость",   "🏹", 0xFF29B6F6),   // Agility  (AGI)
    EARTH("Интеллект",  "🔮", 0xFF9C7FD4),   // Intelligence (INT)
    AIR("Универсал",    "⚡",  0xFFF1E05A),   // Universal
    CHAOS("Тьма",       "💀", 0xFFCE93D8);    // Dire

    public final String displayName;
    public final String icon;
    public final int color; // ARGB for Android

    Element(String displayName, String icon, int color) {
        this.displayName = displayName;
        this.icon = icon;
        this.color = color;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
