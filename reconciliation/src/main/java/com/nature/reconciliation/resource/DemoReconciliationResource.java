package com.nature.reconciliation.resource;

import com.nature.reconciliation.definitions.ReconciliationResource;

import java.math.BigDecimal;

/**
 * 示例对账资源
 */
public class DemoReconciliationResource implements ReconciliationResource {
    /**
     * 流水号
     */
    private String serialNo;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 状态
     */
    private String status;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DemoReconciliationResource{" +
                "serialNo='" + serialNo + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                '}';
    }
}
