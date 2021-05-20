package remotecontrol;

import remotecontrol.Rcp.Info;

public class Packet {
	Info info;
	byte header;
	byte communicationName;
	byte command1;
	byte command2;
}