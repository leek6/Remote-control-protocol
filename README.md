# Remote-control-protocol

## Usage
### Enum infomation
#### Communication test
|Enum|request|response|
|---|:---:|:---:|
|*FIRST_SEND_CODE*|MBCCD|SBCRD|

#### Internal Projector
|Enum|request|response|
|---|:---:|:---:|
|*INTERNALHEAT_START*|MISSD|SISRD|
|*INTERNALHEAT_FINISH*|MIFFD|SIFRD|
|*INTERNALHEAT_TEMPCONTROL*|MIT'temp'D|SIT'temp'D|
|*INTERNALHEAT_STATUSTRANS*||SI'temp''loc'D|

#### External Projector
|Enum|request|response|
|---|:---:|:---:|
|*EXTERNALHEAT_START*|MESSD|SESRD|
|*EXTERNALHEAT_FINISH*|MEFFD|SEFRD|
|*EXTERNALHEAT_TEMPCONTROL*|MET'temp'D|SET'tempD|
|*EXTERNALHEAT_STASUSTRANS*||SE'temp'0D|

#### External sound wave
|Enum|request|response|
|---|:---:|:---:|
|*SOUNDWAVE_START*|MSSSD|SSSRD|
|*SOUNDWAVE_FINISH*|MSFFD|SSFRD|
|*SOUNDWAVE_CONTROL*|MSI'set'D|SSIRD|

#### PEMF
|Enum|request|response|
|---|:---:|:---:|
|*PEMF_START*|MPSSD|SPSRD|
|*PEMF_FINISH*|MPFFD|SPFRD|

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
