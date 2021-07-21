package com.orem.bashhub.data;

import android.content.Context;

import com.orem.bashhub.utils.Const;

public class PayMethodPOJO {

    private String name, type;
    private int icon;

    public PayMethodPOJO(String name, String type, int icon) {
        this.name = name;
        this.type = type;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public boolean tvWalletVisible() {
        return type.equals(Const.PAY_WALLET);
    }

    public String walletAmount(Context mContext){
        return Const.getLoggedInUser(mContext).getWallet();
    }
}
