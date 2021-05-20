package remotecontrol;

import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;

public class Rcp {
	public static byte endCode;

	enum Info {
		FIRST_SEND_CODE,	//0

		INTERNALHEAT_START, INTERNALHEAT_FINISH, INTERNALHEAT_TEMPCONTROL, INTERNALHEAT_STATUSTRANS,	//1, 2, 3, 4

		EXTERNALHEAT_START, EXTERNALHEAT_FINISH, EXTERNALHEAT_TEMPCONTROL, EXTERNALHEAT_STASUSTRANS, 	//5, 6, 7, 8

		SOUNDWAVE_START, SOUNDWAVE_FINISH, SOUNDWAVE_CONTROL,	//9, 10, 11

		PEMF_START, PEMF_FINISH,	//12, 13

		NACK	//14
	};


	public static void main(String[] args){
		Packet request = new Packet();		//input
		byte[] response = new byte[5];		//output

		request = packetize();
		response = responsePacket(request);

	}


	public static Packet packetize() {
		Scanner scan = new Scanner(System.in);
		Packet request = new Packet();
		byte input;
		byte[] result = new byte[5];


		Queue<Byte> q = new LinkedList<Byte>();

		try {		//make packet
			while (true) {

			//---------------------------------------------------------------------------------- input
				input = scan.nextByte();	//test input: only ascii decimal
			//----------------------------------------------------------------------------------

				q.offer(input);

				if (q.size() > 5) {		//make code 5
					q.remove();
//					System.out.println("remove");
				}

				if (input == 68) {		//endCode == 0x44(D)
					if (q.size() < 5) {			//if code is shorter than 5
						q.clear();
//						System.out.println("short code clear");
					}
					else {
						if (!(q.peek() == 77 || q.peek() == 83)) {	//if unmatch header
							q.clear();
//							System.out.println("header unmatch clear");
						}
						else {										//make command if match header
							for (int i = 0; i < 5; i++) {
								result[i] = q.poll();
							}
//							System.out.println("make command");

							request.header = result[0];
							request.communicationName = result[1];
							request.command1 = result[2];
							request.command2 = result[3];
							endCode = result[4];
							break;

						}
					}
				}
			}
			byte[] cc = {result[0], result[1], result[2], result[4]};
			String ctrl_cmd = new String(cc);

			String cmd = new String(result);

			if (cmd.equals("MBCCD")) {
				request.info = Info.FIRST_SEND_CODE;
			}
			else if (cmd.equals("MISSD")) {
				request.info = Info.INTERNALHEAT_START;
			}
			else if (cmd.equals("MIFFD")) {
				request.info = Info.INTERNALHEAT_FINISH;
			}
			else if (ctrl_cmd.equals("MITD")) {
				request.info = Info.INTERNALHEAT_TEMPCONTROL;
			}
//			else if (false) {	//------------------------------------------------------------
//				request.info = Info.INTERNALHEAT_STATUSTRANS;
//			}
			else if (cmd.equals("MESSD")) {
				request.info = Info.EXTERNALHEAT_START;
			}
			else if (cmd.equals("MEFFD")) {
				request.info = Info.EXTERNALHEAT_FINISH;
			}
			else if (ctrl_cmd.equals("METD")) {
				request.info = Info.EXTERNALHEAT_TEMPCONTROL;
			}
//			else if (false) {	//------------------------------------------------------------
//				request.info = Info.externalHeat_stasusTrans;
//			}
			else if (cmd.equals("MSSSD")) {
				request.info = Info.SOUNDWAVE_START;
			}
			else if (cmd.equals("MSFFD")) {
				request.info = Info.SOUNDWAVE_FINISH;
			}
			else if (ctrl_cmd.equals("MSID")) {
				request.info = Info.SOUNDWAVE_CONTROL;
			}
			else if (cmd.equals("MPSSD")) {
				request.info = Info.PEMF_START;
			}
			else if (cmd.equals("MPFFD")) {
				request.info = Info.PEMF_FINISH;
			}
			else {
				request.info = Info.NACK;
			}

//			System.out.println(q.peek());
//
//			System.out.println(request.info);
//			System.out.println("Packet header: 		" + "0x" + Integer.toHexString(request.header).toUpperCase()+ "(" + (char)request.header + ")");
//			System.out.println("comminicationName: 	" + "0x" + Integer.toHexString(request.communicationName).toUpperCase() + "(" + (char)request.communicationName + ")");
//			System.out.println("command1: 		" + "0x" + Integer.toHexString(request.command1).toUpperCase() + "(" + (char)request.command1 + ")");
//			System.out.println("command2: 		" + "0x" + Integer.toHexString(request.command2).toUpperCase() + "(" + (char)request.command2 + ")");
//			System.out.println("endCode: 		" + "0x" + Integer.toHexString(endCode).toUpperCase() + "(" + (char)endCode + ")");

		} catch (Exception e) {
//			System.out.println("err");
		}

		return request;

	}


	public static byte[] responsePacket(Packet req) {
		byte[] res = new byte[5];

	//------------------------------------------------------ get temperature & location when use
		byte in_currentTemp = 0;	//internal temperature
		byte currentLoc = 0;
		byte ex_currentTemp = 0;	//external temperature
	//------------------------------------------------------

		Info info = req.info;	//change method to get enum

		switch(info) {

		case FIRST_SEND_CODE:
			res[0] = 83;
			res[1] = 66;
			res[2] = 67;
			res[3] = 82;			// SBCR
			break;

		case INTERNALHEAT_START:
			res[0] = 83;
			res[1] = 73;
			res[2] = 83;
			res[3] = 82;			// SISR
			break;

		case INTERNALHEAT_FINISH:
			res[0] = 83;
			res[1] = 73;
			res[2] = 70;
			res[3] = 82;			// SIFR
			break;

		case INTERNALHEAT_TEMPCONTROL:
			res[0] = 83;
			res[1] = 73;
			res[2] = 84;
			res[3] = req.command2;	// SIT'temperature'
			break;

		case INTERNALHEAT_STATUSTRANS:
			res[0] = 83;
			res[1] = 73;
			res[2] = in_currentTemp;
			res[3] = currentLoc;
			break;

		case EXTERNALHEAT_START:
			res[0] = 83;
			res[1] = 69;
			res[2] = 83;
			res[3] = 82;			// SESR
			break;

		case EXTERNALHEAT_FINISH:
			res[0] = 83;
			res[1] = 69;
			res[2] = 70;
			res[3] = 82;			// SEFR
			break;

		case EXTERNALHEAT_TEMPCONTROL:
			res[0] = 83;
			res[1] = 69;
			res[2] = 84;
			res[3] = req.command2;	// SET'temperature'
			break;

		case EXTERNALHEAT_STASUSTRANS:
			res[0] = 83;
			res[1] = 69;
			res[2] = ex_currentTemp;
			res[3] = 0;
			break;

		case SOUNDWAVE_START:
			res[0] = 83;
			res[1] = 83;
			res[2] = 83;
			res[3] = 82;			// SSSR
			break;

		case SOUNDWAVE_FINISH:
			res[0] = 83;
			res[1] = 83;
			res[2] = 70;
			res[3] = 82;			// SSFR
			break;

		case SOUNDWAVE_CONTROL:
			res[0] = 83;
			res[1] = 83;
			res[2] = 73;
			res[3] = 82;			// SSIR
			break;

		case PEMF_START:
			res[0] = 83;
			res[1] = 80;
			res[2] = 83;
			res[3] = 82;			// SPSR
			break;

		case PEMF_FINISH:
			res[0] = 83;
			res[1] = 80;
			res[2] = 70;
			res[3] = 82;			// SPFR
			break;

		case NACK:
			res[0] = 78;
			res[1] = 65;
			res[2] = 67;
			res[3] = 75;			// NACK
			break;

		default:
			res[0] = 78;
			res[1] = 65;
			res[2] = 67;
			res[3] = 75;			// NACK
			break;

		}

		res[4] = 68;	// D (endCode)

		return res;
	}


}
