package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Result implements Parcelable {


    @SerializedName("RSRP")
    @Expose
    private Integer rSRP;
    @SerializedName("RSRQ")
    @Expose
    private Integer rSRQ;
    @SerializedName("SINR")
    @Expose
    private Integer sINR;

    public Result() {
    }

    public Result(int rSRP, int rSRQ, int sINR) {
        this.rSRP = rSRP;
        this.rSRQ = rSRQ;
        this.sINR = sINR;
    }


    protected Result(Parcel in) {
        if (in.readByte() == 0) {
            rSRP = null;
        } else {
            rSRP = in.readInt();
        }
        if (in.readByte() == 0) {
            rSRQ = null;
        } else {
            rSRQ = in.readInt();
        }
        if (in.readByte() == 0) {
            sINR = null;
        } else {
            sINR = in.readInt();
        }
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };

    public Integer getRSRP() {
        return rSRP;
    }

    public void setRSRP(Integer rSRP) {
        this.rSRP = rSRP;
    }

    public Integer getRSRQ() {
        return rSRQ;
    }

    public void setRSRQ(Integer rSRQ) {
        this.rSRQ = rSRQ;
    }

    public Integer getSINR() {
        return sINR;
    }

    public void setSINR(Integer sINR) {
        this.sINR = sINR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (rSRP == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(rSRP);
        }
        if (rSRQ == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(rSRQ);
        }
        if (sINR == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(sINR);
        }
    }
}

