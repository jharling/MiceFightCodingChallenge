package com.company;

import java.util.List;
import java.util.Objects;

public class FighterInfo {
    private String company;
    private String challenge;
    private List<Vendors> vendors;


    public FighterInfo() {
    }

    public FighterInfo(String company, String challenge, List<Vendors> vendors) {
        this.company = company;
        this.challenge = challenge;
        this.vendors = vendors;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public List<Vendors> getVendors() {
        return vendors;
    }

    public void setVendors(List<Vendors> vendors) {
        this.vendors = vendors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FighterInfo that = (FighterInfo) o;
        return Objects.equals(company, that.company) &&
                Objects.equals(challenge, that.challenge) &&
                Objects.equals(vendors, that.vendors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(company, challenge, vendors);
    }

    @Override
    public String toString() {
        return "FighterInfo{" +
                "company='" + company + '\'' +
                ", challenge='" + challenge + '\'' +
                ", vendors=" + vendors +
                '}';
    }
}
