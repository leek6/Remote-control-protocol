package remotecontrolprotocol;

//import remotecontrolprotocol.packet;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;

public class rcp {	
	public static byte endCode;
	public static boolean in = false;
	public static boolean ex = false;
	
	enum Info {
		first_send_code,	//0
		
		internalHeat_start, internalHeat_finish, internalHeat_tempControl, internalHeat_statusTrans,	//1, 2, 3, 4
		
		externalHeat_start, externalHeat_finish, externalHeat_tempControl, externalHeat_stasusTrans, 	//5, 6, 7, 8
		
		soundWave_start, soundWave_finish, soundWave_control,	//9, 10, 11
		
		PEMF_start, PEMF_finish,	//12, 13
		
		NACK	//14
	};
	
	
	public static void main(String[] args){
		packet request = new packet();		//input
		packet response = new packet();		//output
		packet inHeatTrans = new packet();
		packet exHeatTrans = new packet();
		
		request = packetize();
		response = responsePacket(request);		
		
		
		
		
		if (in) {
			
		//------------------------------------------------------ get temperature & location when use
			byte currentTemp = 0;
			byte currentLoc = 0;
		//------------------------------------------------------
			
			inHeatTrans.info = Info.internalHeat_statusTrans;
			inHeatTrans.header = 83;
			inHeatTrans.command1 = 73;
			inHeatTrans.command2 = currentTemp;
			inHeatTrans.command2 = currentLoc;
		}
		
		if (ex) {
		//------------------------------------------------------ get temperature when use
			byte currentTemp = 0;
		//------------------------------------------------------
			
			exHeatTrans.info = Info.externalHeat_stasusTrans;
			exHeatTrans.header = 83;
			exHeatTrans.command1 = 69;
			exHeatTrans.command2 = currentTemp;
			exHeatTrans.command2 = 0;
		}
		
	}

	
	
	public static packet packetize() {
		Scanner scan = new Scanner(System.in);
		packet request = new packet();
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
					System.out.println("remove");
				}
				
				if (input == 68) {		//endCode == 0x44(D)
					if (q.size() < 5) {			//if code is shorter than 5
						q.clear();
						System.out.println("short code clear");
					}
					else {
						if (!(q.peek() == 77 || q.peek() == 83)) {	//if unmatch header
							q.clear();
							System.out.println("header unmatch clear");
						}
						else {										//make command if match header
							for (int i = 0; i < 5; i++) {
								result[i] = q.poll();
							}
							System.out.println("make command");
							
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
				request.info = Info.first_send_code;
			}
			else if (cmd.equals("MISSD")) {
				request.info = Info.internalHeat_start;
				in = true;
			}
			else if (cmd.equals("MIFFD")) {
				request.info = Info.internalHeat_finish;
				in = false;
			}
			else if (ctrl_cmd.equals("MITD")) {
				request.info = Info.internalHeat_tempControl;
			}
//			else if (false) {	//------------------------------------------------------------
//				request.info = Info.internalHeat_statusTrans;
//			}
			else if (cmd.equals("MESSD")) {
				request.info = Info.externalHeat_start;
				ex = true;
			}
			else if (cmd.equals("MEFFD")) {
				request.info = Info.externalHeat_finish;
				ex = true;
			}
			else if (ctrl_cmd.equals("METD")) {
				request.info = Info.externalHeat_tempControl;
			}
//			else if (false) {	//------------------------------------------------------------
//				request.info = Info.externalHeat_stasusTrans;
//			}
			else if (cmd.equals("MSSSD")) {
				request.info = Info.soundWave_start;
			}
			else if (cmd.equals("MSFFD")) {
				request.info = Info.soundWave_finish;
			}
			else if (ctrl_cmd.equals("MSID")) {
				request.info = Info.soundWave_control;
			}
			else if (cmd.equals("MPSSD")) {
				request.info = Info.PEMF_start;
			}
			else if (cmd.equals("MPFFD")) {
				request.info = Info.PEMF_finish;
			}
			else {
				request.info = Info.NACK;
			}
			
			
			System.out.println(q.peek());
			
			System.out.println(request.info);
			System.out.println("Packet header: 		" + "0x" + Integer.toHexString(request.header).toUpperCase()+ "(" + (char)request.header + ")");
			System.out.println("comminicationName: 	" + "0x" + Integer.toHexString(request.communicationName).toUpperCase() + "(" + (char)request.communicationName + ")");
			System.out.println("command1: 		" + "0x" + Integer.toHexString(request.command1).toUpperCase() + "(" + (char)request.command1 + ")");
			System.out.println("command2: 		" + "0x" + Integer.toHexString(request.command2).toUpperCase() + "(" + (char)request.command2 + ")");
			System.out.println("endCode: 		" + "0x" + Integer.toHexString(endCode).toUpperCase() + "(" + (char)endCode + ")");
			
		} catch (Exception e) {
			System.out.println("err");
		}
		
		return request;

	}
	
	
	
	
	public static packet responsePacket(packet req) {
		byte[] request = {req.header, req.communicationName, req.command1, req.command2, endCode};
		
		packet response = new packet();
		
		String cmd = new String(request);
		
		byte[] cc = {request[0], request[1], request[2], request[4]};
		String ctrl_cmd = new String(cc);
		
		
		if (cmd.equals("MBCCD")) {
			response.info = Info.first_send_code;
			response.header = 83;
			response.communicationName = 66;
			response.command1 = 67;
			response.command2 = 82;			// SBCR
		}
		else if (cmd.equals("MISSD")) {
			response.info = Info.internalHeat_start;
			response.header = 83;
			response.communicationName = 73;
			response.command1 = 83;
			response.command2 = 82;			// SISR
		}
		else if (cmd.equals("MIFFD")) {
			response.info = Info.internalHeat_finish;
			response.header = 83;
			response.communicationName = 73;
			response.command1 = 70;
			response.command2 = 82;			// SIFR
		}
		else if (ctrl_cmd.equals("MITD")) {
			response.info = Info.internalHeat_tempControl;
			response.header = 83;
			response.communicationName = 73;
			response.command1 = 84;
			response.command2 = req.command2;	// SIT'temperature'
		}
//		else if (false) {	//------------------------------------------------------------
//			
//		//------------------------------------------------------ get temperature & location when use
//			byte currentTemp = 0;
//			byte currentLoc = 0;
//		//------------------------------------------------------
//			
//			response.info = Info.internalHeat_statusTrans;
//			response.header = 83;
//			response.command1 = 73;
//			response.command2 = currentTemp;
//			response.command2 = currentLoc;
//		}
		else if (cmd.equals("MESSD")) {
			response.info = Info.externalHeat_start;
			response.header = 83;
			response.communicationName = 69;
			response.command1 = 83;
			response.command2 = 82;			// SESR
		}
		else if (cmd.equals("MEFFD")) {
			response.info = Info.externalHeat_finish;
			response.header = 83;
			response.communicationName = 69;
			response.command1 = 70;
			response.command2 = 82;			// SEFR
		}
		else if (ctrl_cmd.equals("METD")) {
			response.info = Info.externalHeat_tempControl;
			response.header = 83;
			response.communicationName = 69;
			response.command1 = 84;
			response.command2 = req.command2;	// SET'temperature'
		}
//		else if (false) {	//------------------------------------------------------------
//			
//		//------------------------------------------------------ get temperature when use
//			byte currentTemp = 0;
//		//------------------------------------------------------
//			
//			response.info = Info.externalHeat_stasusTrans;
//			response.header = 83;
//			response.command1 = 69;
//			response.command2 = currentTemp;
//			response.command2 = 0;
//		}
		else if (cmd.equals("MSSSD")) {
			response.info = Info.soundWave_start;
			response.header = 83;
			response.communicationName = 83;
			response.command1 = 83;
			response.command2 = 82;			// SSSR
		}
		else if (cmd.equals("MSFFD")) {
			response.info = Info.soundWave_finish;
			response.header = 83;
			response.communicationName = 83;
			response.command1 = 70;
			response.command2 = 82;			// SSFR
		}
		else if (ctrl_cmd.equals("MSID")) {
			response.info = Info.soundWave_control;
			response.header = 83;
			response.communicationName = 83;
			response.command1 = 73;
			response.command2 = 82;			// SSIR
		}
		else if (cmd.equals("MPSSD")) {
			response.info = Info.PEMF_start;
			response.header = 83;
			response.communicationName = 80;
			response.command1 = 83;
			response.command2 = 82;			// SPSR
		}
		else if (cmd.equals("MPFFD")) {
			response.info = Info.PEMF_finish;
			response.header = 83;
			response.communicationName = 80;
			response.command1 = 70;
			response.command2 = 82;			// SPFR
		}
		else {
			response.info = Info.NACK;
			response.header = 78;
			response.communicationName = 65;
			response.command1 = 67;
			response.command2 = 75;			// NACK
		}
		
		

		
		return response;
	}
	
	

}
