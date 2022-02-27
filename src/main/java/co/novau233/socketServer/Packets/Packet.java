package co.novau233.socketServer.Packets;

import java.io.Serializable;

public interface Packet<T> extends Serializable {
    public String getHead();
    public String getTag();
    public void setAttachMsg(T msg);
    public T getAttachMsg();
}
