package com.revolut.transfer.dtos;

public class TransferRequestDTO {

    private Long fromAccount;

    private Long toAccount;

    private Long amount;

    public void setFromAccount(Long fromAccount) {
        this.fromAccount = fromAccount;
    }

    public void setToAccount(Long toAccount) {
        this.toAccount = toAccount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getFromAccount() {
        return fromAccount;
    }

    public Long getToAccount() {
        return toAccount;
    }

    public Long getAmount() {
        return amount;
    }
}
