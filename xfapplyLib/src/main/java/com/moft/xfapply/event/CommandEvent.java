package com.moft.xfapply.event;

/**
 * Created by wangquan on 2016/11/12.
 */

public class CommandEvent {
    public static final String CMD_EXIT = "CMD_EXIT";
    public static final String CMD_LOGOUT = "CMD_LOGOUT";
    public static final String CMD_NETWORK_CHANGED = "CMD_NETWORK_CHANGED";
    public static final String CMD_UPDATE_OFFLINE_COMPLETED = "CMD_UPDATE_OFFLINE_COMPLETED";
    public static final String CMD_UPDATE_OFFLINE_MODE_CHANGED = "CMD_UPDATE_OFFLINE_MODE_CHANGED";

    public final String command;
    private Object data;

    public CommandEvent(String command) {
        this.command = command;
    }

    public CommandEvent(String command, Object data) {
        this.command = command;
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
