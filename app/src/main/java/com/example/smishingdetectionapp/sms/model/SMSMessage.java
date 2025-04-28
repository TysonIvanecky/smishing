package com.example.smishingdetectionapp.sms.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class SMSMessage implements Parcelable {
    private String sender;
    private String body;
    private int severity;

    public SMSMessage(String sender, String body, int severity) {
        this.sender = sender;
        this.body = body;
        this.severity = severity;
    }

    protected SMSMessage(Parcel in) {
        sender = in.readString();
        body = in.readString();
        severity = in.readInt();
    }

    public static final Creator<SMSMessage> CREATOR = new Creator<SMSMessage>() {
        @Override
        public SMSMessage createFromParcel(Parcel in) {
            return new SMSMessage(in);
        }

        @Override
        public SMSMessage[] newArray(int size) {
            return new SMSMessage[size];
        }
    };

    public String getSender() {
        return sender;
    }

    public String getBody() {
        return body;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(sender);
        dest.writeString(body);
    }
}
