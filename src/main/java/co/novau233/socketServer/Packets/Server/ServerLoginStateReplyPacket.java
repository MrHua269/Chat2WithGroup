package co.novau233.socketServer.Packets.Server;

import co.novau233.socketServer.EnumUserState;
import co.novau233.socketServer.Packets.Packet;

public class ServerLoginStateReplyPacket implements Packet<EnumUserState> {
    @Override
    public String getHead() {
        return "LOGINSTATE";
    }
    @Override
    public String getTag() {
        return "Client";
    }
    public EnumUserState state = null;
    @Override
    public void setAttachMsg(EnumUserState msg) {
      this.state = msg;
    }
    @Override
    public EnumUserState getAttachMsg() {
        return state;
    }
}
