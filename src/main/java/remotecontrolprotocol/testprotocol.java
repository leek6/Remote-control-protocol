package remotecontrolprotocol;

public class testprotocol {						//test case
	int[] test1 = {0x4D, 0x42, 0x43, 0x43, 0x44};				//2.1.1	MBCCD
	long test2 = 8366678268L;									//2.1.2	SBCRD 10������ �ö�
	StringBuffer test3 = new StringBuffer("4D49535344");		//3.1.1	MISSD 16���� ��Ʈ������
	StringBuffer test4 = new StringBuffer("8373838268");		//3.1.2	SISRD 10���� ��Ʈ�� ����
	StringBuffer test5 = new StringBuffer("MIFFD");				//3.2.2 MIFFD ���� ��Ʈ�� ����
	
	
	
	
//	int[] test6 = {0x53, 0x49, 0x46, 0x52, 0x44};		//3.2.2	SIFRD
//	int[] test7 = {0x4D, 0x49, 0x54, 0x19, 0x44};		//3.3.1	�����µ� 25��	MIT25D
//	int[] test8 = {0x53, 0x49, 0x54, 0x1E, 0x44};		//3.3.2	���۹��� �����µ� 30��	SIT30D
//	int[] test9 = {0x53, 0x49, 0x14, 0x52, 0x44};		//3.4.1 ����µ� 20�� ������ġ R	SI20RD
//	int[] testerr1 = {0x56, 0x57, 0x58, 0x59, 0x5A};	//VWXYZ
//	int[] testerr2 = {0x4D, 0x42, 0x43, 0x43, 0x55};	//MBCCU end code err
//	int[] testerr3 = {0};
}


//