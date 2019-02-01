package com.example.commonlib.gson;

public class UserPaymentGson {

    /**
     * SubstitutePayment : 5
     * SubstituteShipment : 3
     * CollectionGoods : 0
     * SubstitutesEvaluated : 0
     */

    private int SubstitutePayment;
    private int SubstituteShipment;
        private int CollectionGoods;

    @Override
    public String toString() {
        return "UserPaymentGson{" +
                "SubstitutePayment=" + SubstitutePayment +
                ", SubstituteShipment=" + SubstituteShipment +
                ", CollectionGoods=" + CollectionGoods +
                ", SubstitutesEvaluated=" + SubstitutesEvaluated +
                '}';
    }

    private int SubstitutesEvaluated;

    public int getSubstitutePayment() {
        return SubstitutePayment;
    }

    public void setSubstitutePayment(int SubstitutePayment) {
        this.SubstitutePayment = SubstitutePayment;
    }

    public int getSubstituteShipment() {
        return SubstituteShipment;
    }

    public void setSubstituteShipment(int SubstituteShipment) {
        this.SubstituteShipment = SubstituteShipment;
    }

    public int getCollectionGoods() {
        return CollectionGoods;
    }

    public void setCollectionGoods(int CollectionGoods) {
        this.CollectionGoods = CollectionGoods;
    }

    public int getSubstitutesEvaluated() {
        return SubstitutesEvaluated;
    }

    public void setSubstitutesEvaluated(int SubstitutesEvaluated) {
        this.SubstitutesEvaluated = SubstitutesEvaluated;
    }
}
