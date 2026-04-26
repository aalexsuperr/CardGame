package com.example.cardgame.model;

public enum Ability {
    NONE("Нет способности"),
    DOUBLE_STRIKE_VS_WATER("x2 удар по Ловкости"),
    DOUBLE_STRIKE_VS_FIRE("x2 удар по Силе"),
    DOUBLE_STRIKE_VS_EARTH("x2 удар по Интеллекту"),
    DOUBLE_STRIKE_VS_AIR("x2 удар по Универсалам"),
    DOUBLE_STRIKE_VS_CHAOS("x2 удар по Тьме"),
    REGEN_FIRE("Регенерация +1 ХП/ход"),
    REGEN_WATER("Регенерация +1 ХП/ход"),
    REGEN_EARTH("Регенерация +1 ХП/ход"),
    REGEN_AIR("Регенерация +1 ХП/ход"),
    REGEN_CHAOS("Регенерация +1 ХП/ход"),
    BURN("Поджог: -1 ХП врагу/ход"),
    POISON("Яд: -1 ХП врагу/ход"),
    SHIELD_BREAK("Пробивание защиты"),
    LIFE_STEAL("Вампиризм: 50% урона в ХП"),
    SPLASH("Всплеск: удар по всем"),
    TAUNT("Провокация: бьют первым"),
    MANA_DRAIN("Истощение маны врага"),
    FORTIFY("+2 к защите при выкладывании"),
    SWIFT("Быстрота: атакует дважды"),
    NECROMANCY("Некромантия: -3 ХП врагу при смерти");

    public final String description;

    Ability(String description) {
        this.description = description;
    }
}
