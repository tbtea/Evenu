package com.example.tonytea.evenu;

public class Transaction {

    private String transaction_id;
    private String buyer_id;
    private String seller_id;

    public Transaction(String tid, String bid, String sid){
        this.transaction_id = tid;
        this.buyer_id = bid;
        this.seller_id = sid;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(String buyer_id) {
        this.buyer_id = buyer_id;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }
}
