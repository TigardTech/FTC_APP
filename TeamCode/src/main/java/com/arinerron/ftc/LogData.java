package com.arinerron.ftc;

import java.util.Stack;

class LogData {
    private String tag = "INFO";
    private String content = "";

    /* init log message */
    public LogData(String tag, String content) {
        this.setTag(tag);
        this.setContent(content);
    }

    /* returns a pre-formatted string */
    public String getMessage() {
        return "[" + this.getTag() + "]: " + this.getContent();
    }

    /* returns the tag, or the prefix before the message */
    public String getTag() {
        return this.tag.toUpperCase();
    }

    /* returns the content of the log message */
    public String getContent() {
        return this.content;
    }

    /* sets the tag/prefix of the message */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /* sets the content of the message */
    public void setContent(String content) {
        this.content = content;
    }
}
