package co.novau233.socketServer.Packets.Server;

import co.novau233.socketServer.Packets.Packet;

public class GlobalMSGPacket implements Packet<String> {
    @Override
    public String getHead() {
        return "GLMS";
    }
    @Override
    public String getTag() {return null;}
    private String m;
    @Override
    public void setAttachMsg(String msg) {
        this.m=msg;
    }
    @Override
    public String getAttachMsg() {
        return this.m;
    }
}
