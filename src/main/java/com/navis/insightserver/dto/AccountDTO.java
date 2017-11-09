package com.navis.insightserver.dto;

/**
 * Created by darrell-shofstall on 8/11/17.
 */
public class AccountDTO extends BaseDTO{

    private static final long serialVersionUID = 1L;

private String accountNumber;
private String accountName;

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public AccountDTO() {
    }
}
