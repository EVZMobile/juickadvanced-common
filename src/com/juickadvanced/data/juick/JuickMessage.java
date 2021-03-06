/*
 * Juick
 * Copyright (C) 2008-2012, Ugnich Anton
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.juickadvanced.data.juick;

import com.juickadvanced.data.MessageID;
import com.juickadvanced.data.bnw.BnwMessageID;

import java.io.Serializable;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

/**
 *
 * @author Ugnich Anton
 */
public class JuickMessage implements Serializable {

    private MessageID MID = null;
    private int RID = 0;
    private int replyTo = 0;
    public String Text = null;
    public JuickUser User = null;
    public Vector<String> tags = new Vector<String>();
    public Date Timestamp = null;
    public long deltaTime = Long.MIN_VALUE;
    public int replies = 0;
    public String Photo = null;
    public String Video = null;
    public boolean translated;
    public String source;
    public String microBlogCode;
    public boolean privateMessage;
    public boolean read;        // already read
    public int readComments;

    transient public String continuationInformation;
    transient public long messageSaveDate;
    transient public Object parsedText;
    transient public Object parsedUI;
    public JuickMessage contextPost;      // parent post
    public JuickMessage contextReply;     // parent reply
    public int myFoundCount;
    public int hisFoundCount;

    public JuickMessage() {
    }

    public String getTags() {
        String t = new String();
        for (Enumeration e = tags.elements(); e.hasMoreElements();) {
            String tag = (String) e.nextElement();
            if (t.length() > 0) {
                t += ' ';
            }
            t += '*' + tag;
        }
        return t;
    }


    @Override
    public String toString() {
        String msg = "";
        if (User != null) {
            msg += "@" + User.UName + ": ";
        }
        msg += getTags();
        if (msg.length() > 0) {
            msg += "\n";
        }
        if (Photo != null) {
            msg += Photo + "\n";
        } else if (Video != null) {
            msg += Video + "\n";
        }
        if (Text != null) {
            msg += Text + "\n";
        }
        msg = webLinkToMessage(msg);
        return msg;
    }

    protected String webLinkToMessage(String msg) {
        if (MID instanceof JuickMessageID) {
            msg += "#"+((JuickMessageID)MID).getMid();
            if (RID > 0) {
                msg += "/" + RID;
            }
            msg += " http://juick.com/" + ((JuickMessageID)MID).getMid();
            if (RID > 0) {
                msg += "#" + RID;
            }
            return msg;
        } else {
            return "http://example.com/"+MID.toString();
        }
    }

    public MessageID getMID() {
        return MID;
    }

    public int getRID() {
        return RID;
    }

    public void setRID(int RID) {
        this.RID = RID;
    }

    public int getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(int replyTo) {
        this.replyTo = replyTo;
    }

    public void setMID(MessageID MID) {
        this.MID = MID;
    }

    public String getDisplayMessageNo() {
        String s = getMID().toDisplayString();
        if (RID > 0) {
            s += "/"+RID;
        }
        return s;
    }
}
