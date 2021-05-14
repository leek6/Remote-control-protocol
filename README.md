# Remote-control-protocol

## Usage
### Enum infomation
#### Communication test
|Enum|request|response|
|---|:---:|:---:|
|*first_send_code*|MBCCD|SBCRD|

#### Internal Projector
|Enum|request|response|
|---|:---:|:---:|
|*internalHeat_start*|MISSD|SISRD|
|*internalHeat_finish*|MIFFD|SIFRD|
|*internalHeat_tempControl*|MIT'temp'D|SIT'temp'D|
|*internalHeat_statusTrans*||SI'temp''loc'D|

#### External Projector
|Enum|request|response|
|---|:---:|:---:|
|*externalHeat_start*|MESSD|SESRD|
|*externalHeat_finish*|MEFFD|SEFRD|
|*externalHeat_tempControl*|MET'temp'D|SET'tempD|
|*externalHeat_stasusTrans*||SE'temp'0D|

#### External sound wave
|Enum|request|response|
|---|:---:|:---:|
|*soundWave_start*|MSSSD|SSSRD|
|*soundWave_finish*|MSFFD|SSFRD|
|*soundWave_control*|MSI'set'D|SSIRD|

#### PEMF
|Enum|request|response|
|---|:---:|:---:|
|*PEMF_start*|MPSSD|SPSRD|
|*PEMF_finish*|MPFFD|SPFRD|

#### NACK
|Enum|request|response|
|---|:---:|:---:|
|*NACK*||NACK|

### Input
In function packetize, change input method of incomming signal.
Default is keyboard input.
``` Java
//---------------------------------------------------------------------------------- input
	input = scan.nextByte();	//test input: only ascii decimal
//----------------------------------------------------------------------------------
```

### Get temperature, location
Change method of get temperature or location infomation in function responsePacket(Info info, packet req)
``` Java
//------------------------------------------------------ get temperature & location when use
	byte in_currentTemp = 0;	//internal temperature
	byte currentLoc = 0;
	byte ex_currentTemp = 0;	//external temperature
//------------------------------------------------------
```

----------------------------------
## Author
Ki-hyuck Lee, Software Engineering Lab in Yonsei Univ., sinse 2020-09 ~


## Maintainer
Ki-hyuck Lee, leek6@yonsei.ac.kr
