package co.novau233.socketServer.Packets.Client;

import co.novau233.socketServer.Packets.Packet;

public class ClientREGPacket implements Packet<String[]> {
    @Override
    public String getHead() {return "REG";}
    @Override
    public String getTag() {return null;}
    private String[] attaches;
    @Override
    public void setAttachMsg(String[] msg) {
        this.attaches = msg;
    }
    @Override
    public String[] getAttachMsg() {
        return this.attaches;
    }
}
