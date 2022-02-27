package co.novau233.socketServer.Packets.Client;

import co.novau233.socketServer.Packets.Packet;

public class ClientLoginRequestPacket implements Packet<String[]> {
    String[] msgs = new String[256];
    @Override
    public String getHead() {
        return "LOGINREQUEST";
    }
    @Override
    public String getTag() {
        return "Client";
    }
    @Override
    public void setAttachMsg(String[] msg) {
       this.msgs=msg;
    }
    @Override
    public String[] getAttachMsg() {
        return this.msgs;
    }
}
