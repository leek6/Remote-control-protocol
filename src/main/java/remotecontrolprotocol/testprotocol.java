package remotecontrolprotocol;

public class testprotocol {						//test case
	int[] test1 = {0x4D, 0x42, 0x43, 0x43, 0x44};				//2.1.1	MBCCD
	long test2 = 8366678268L;									//2.1.2	SBCRD 10진수로 올때
	StringBuffer test3 = new StringBuffer("4D49535344");		//3.1.1	MISSD 16진수 스트링버퍼
	StringBuffer test4 = new StringBuffer("8373838268");		//3.1.2	SISRD 10진수 스트링 버퍼
	StringBuffer test5 = new StringBuffer("MIFFD");				//3.2.2 MIFFD 문자 스트링 버퍼
	
	
	
	
//	int[] test6 = {0x53, 0x49, 0x46, 0x52, 0x44};		//3.2.2	SIFRD
//	int[] test7 = {0x4D, 0x49, 0x54, 0x19, 0x44};		//3.3.1	설정온도 25도	MIT25D
//	int[] test8 = {0x53, 0x49, 0x54, 0x1E, 0x44};		//3.3.2	전송받은 설정온도 30도	SIT30D
//	int[] test9 = {0x53, 0x49, 0x14, 0x52, 0x44};		//3.4.1 현재온도 20도 현재위치 R	SI20RD
//	int[] testerr1 = {0x56, 0x57, 0x58, 0x59, 0x5A};	//VWXYZ
//	int[] testerr2 = {0x4D, 0x42, 0x43, 0x43, 0x55};	//MBCCU end code err
//	int[] testerr3 = {0};
}


//