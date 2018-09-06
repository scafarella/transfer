package com.revolut.transfer.dtos;

public class TransferRequestDTO {

    private Long fromAccount;

    private Long toAccount;

    private Double amount;

    public void setFromAccount(Long fromAccount) {
        this.fromAccount = fromAccount;
    }

    public void setToAccount(Long toAccount) {
        this.toAccount = toAccount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getFromAccount() {
        return fromAccount;
    }

    public Long getToAccount() {
        return toAccount;
    }

    public Double getAmount() {
        return amount;
    }
}
