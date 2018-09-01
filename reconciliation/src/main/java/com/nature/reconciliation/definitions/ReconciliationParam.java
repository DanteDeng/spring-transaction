package com.nature.reconciliation.definitions;

public interface ReconciliationParam<K, T extends ReconciliationResource> {
    K getKey();

    void setKey(K key);

    T getaSide();

    void setaSide(T aSide);

    T getbSide();

    void setbSide(T bSide);

    boolean getCheckResult();

    void setCheckResult(boolean checkResult);
}
