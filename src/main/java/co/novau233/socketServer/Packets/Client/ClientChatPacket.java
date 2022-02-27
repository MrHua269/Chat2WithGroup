package co.novau233.socketServer.Packets.Client;

import co.novau233.socketServer.Packets.Packet;

public class ClientChatPacket implements Packet<String> {
    @Override
    public String getHead() {
        return "CHAT";
    }
    @Override
    public String getTag() {
        return "CLIENT";
    }
    public String s = null;
    @Override
    public void setAttachMsg(String msg) {
      this.s = msg;
    }
    @Override
    public String getAttachMsg() {
        return this.s;
    }
}
