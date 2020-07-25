package com.moft.xfapply.model.common;

import java.util.List;

public class UpgradeDBInfo {
    public enum MODE {
        common,
        offline,
        history
    }
    private String mode;
    private int baseLine;
    private List<UpgradeCommand> upgradeCommand;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getBaseLine() {
        return baseLine;
    }

    public void setBaseLine(int baseLine) {
        this.baseLine = baseLine;
    }

    public List<UpgradeCommand> getUpgradeCommand() {
        return upgradeCommand;
    }

    public void setUpgradeCommand(List<UpgradeCommand> upgradeCommand) {
        this.upgradeCommand = upgradeCommand;
    }

    public class UpgradeCommand {
        private int verNo;
        private List<String> commands;

        public int getVerNo() {
            return verNo;
        }

        public void setVerNo(int verNo) {
            this.verNo = verNo;
        }

        public List<String> getCommands() {
            return commands;
        }

        public void setCommands(List<String> commands) {
            this.commands = commands;
        }
    }
}
