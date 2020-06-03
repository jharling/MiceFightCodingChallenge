package com.company;

import java.util.Objects;

public class Vendors {
    private String vendor;
    private int health;
    private int damage;
    private int attacks;
    private int dodge;
    private int critical;
    private int initiative;
    private int wins;
    private int loses;

    public Vendors() {
    }

    public Vendors(String vendor, int health, int damage, int attacks, int dodge, int critical, int initiative) {
        this.vendor = vendor;
        this.health = health;
        this.damage = damage;
        this.attacks = attacks;
        this.dodge = dodge;
        this.critical = critical;
        this.initiative = initiative;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getAttacks() {
        return attacks;
    }

    public void setAttacks(int attacks) {
        this.attacks = attacks;
    }

    public int getDodge() {
        return dodge;
    }

    public void setDodge(int dodge) {
        this.dodge = dodge;
    }

    public int getCritical() {
        return critical;
    }

    public void setCritical(int critical) {
        this.critical = critical;
    }

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLoses() {
        return loses;
    }

    public void setLoses(int loses) {
        this.loses = loses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vendors vendors = (Vendors) o;
        return health == vendors.health &&
                damage == vendors.damage &&
                attacks == vendors.attacks &&
                dodge == vendors.dodge &&
                critical == vendors.critical &&
                initiative == vendors.initiative &&
                Objects.equals(vendor, vendors.vendor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vendor, health, damage, attacks, dodge, critical, initiative);
    }

    @Override
    public String toString() {
        return "Vendors{" +
                "vendor='" + vendor + '\'' +
                ", health=" + health +
                ", damage=" + damage +
                ", attacks=" + attacks +
                ", dodge=" + dodge +
                ", critical=" + critical +
                ", initiative=" + initiative +
                '}';
    }
}
