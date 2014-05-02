package gribs;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.BitSet;
import java.util.Date;

import org.apache.log4j.Logger;


public class Decode {
	
	static int offset = 0;
	private static final Logger log = Logger.getLogger(Decode.class.getPackage()
			.getName());
	

	public static Structure BeginDecode(byte[] gribFile,String filename) throws IOException {
		
		InputStream input = new ByteArrayInputStream (gribFile);
		Structure thisGRIB = new Structure(filename);
		
		thisGRIB.setGRIB(false);
          
          //����� GRIB � GRIB �����
		String search;
          do
          {
              search = new String(readBytes(input, 1),"UTF-8");
              if (search.endsWith("G"))
              {
                  search = new String(readBytes(input, 1),"UTF-8"); if (search.endsWith("R"))
                  {
                      search = new String(readBytes(input, 1),"UTF-8"); if (search.endsWith("I"))
                      { search = new String(readBytes(input, 1),"UTF-8"); if (search.endsWith("B")) thisGRIB.setGRIB(true); }
                  }
              }
          }
          while (!thisGRIB.isGRIB());
		
	
		if (thisGRIB.isGRIB()) {
			log.info("��� GRIB.");
		} else {
			log.info("���� �� �������� GRIB ������.");
			return null;
		}
		
		log.info("������ ����������� ���������."); //������ ����������� ���������
		thisGRIB.setLength(new BigInteger(1,readBytes(input, 3)).intValue());
		log.info("����� GRIB �����: " + thisGRIB.getLength()); 
		
		thisGRIB.setVersion(new BigInteger(1,readBytes(input, 1)).intValue());
		log.info("������ GRIB �����: " + thisGRIB.getVersion()); 	
		
		thisGRIB.setPart1Length(new BigInteger(1,readBytes(input,	3)).intValue());	
        log.info("����� ������� ����������� ���������: " +thisGRIB.getPart1Length());
        
        if (new BigInteger(1,readBytes(input,	1)).intValue() == 2) {
        	thisGRIB.setInterantional(true); log.info("������������� �����.");
        }
        else
        {
        	thisGRIB.setInterantional(false); log.info("��������� �����.");
        }
        
        thisGRIB.setWhereFrom(new BigInteger(1,readBytes(input, 1)).intValue());
        if (thisGRIB.getWhereFrom() == 4) log.info("���� ������� �� ������ :)");
        if (thisGRIB.getWhereFrom() == 74) log.info("����������������� ���� �� - ��������.");
       
        @SuppressWarnings("unused")
		byte[] empty = readBytes(input, 1);
             
        thisGRIB.setGridType(new BigInteger(1,readBytes(input, 1)).intValue());
        log.info("��� �����: " + thisGRIB.getGridType());


        byte[] Flag = readBytes(input,1);
          
        thisGRIB.setPart2Included(isSet(Flag, 7));
        thisGRIB.setPart3Included(isSet(Flag, 6));
        
        if (thisGRIB.isPart2Included()) 
        	log.info("������ 2 �������."); 
        else 
        	log.info("������ 2 �� �������.");
        
        if (thisGRIB.isPart3Included()) 
       	 	log.info("������ 3 �������."); 
       else 
       		log.info("������ 3 �� �������.");
       

        int UkazParam = new BigInteger(1,readBytes(input,1)).intValue(); 

        if (UkazParam == 33)
        {
             log.info("���� �������� ��������� ����� U.");
             thisGRIB.setHasWindComponent_U(true);
             thisGRIB.setHasWindComponent_V(false);
        }
        if (UkazParam == 34)
        {
             log.info("���� �������� ��������� ����� V.");
             thisGRIB.setHasWindComponent_U(false);
             thisGRIB.setHasWindComponent_V(true);
        }

        thisGRIB.setLevelType(new BigInteger(1,readBytes(input,1)).intValue()); 
        if (thisGRIB.getLevelType() == 100)
             log.info("��� ������ - ������������� �����������.");

        
        thisGRIB.setLevel(new BigInteger(1,readBytes(input,2)).intValue());
        log.info("������: "+thisGRIB.getLevel());

        //����

        int Year = new BigInteger(1,readBytes(input,1)).intValue();
        int Months = new BigInteger(1,readBytes(input,1)).intValue();
        int Day = new BigInteger(1,readBytes(input,1)).intValue();
        int Hours = new BigInteger(1,readBytes(input,1)).intValue();
        int Minutes = new BigInteger(1,readBytes(input,1)).intValue();  
          
         
         thisGRIB.setUnitOfTimeIndex(new BigInteger(1,readBytes(input,1)).intValue());

        if (thisGRIB.getUnitOfTimeIndex() == 1)
             log.info("��������� ������� �������: ���");
        else
        {
             log.info("��������� ������� �������: " + thisGRIB.getUnitOfTimeIndex());
        }
        
        
        
         thisGRIB.setP1(new BigInteger(1,readBytes(input,1)).intValue());
         log.info("P1 - ������ �������: " + thisGRIB.getP1());
         
         thisGRIB.setP2(new BigInteger(1,readBytes(input,1)).intValue());
         log.info("P2 - ������ �������: " + thisGRIB.getP2());

         thisGRIB.setTimePointer(new BigInteger(1,readBytes(input,1)).intValue());
         log.info("��������� �������: " + thisGRIB.getTimePointer());

         
         thisGRIB.setNumberOfCases(new BigInteger(1,readBytes(input,2)).intValue());
         log.info("����� �������: " + thisGRIB.getNumberOfCases());

         thisGRIB.setNumberOfCasesLost(new BigInteger(1,readBytes(input,1)).intValue());
         log.info("����� ���������� �������: " + thisGRIB.getNumberOfCasesLost());

         thisGRIB.setCentury((new BigInteger(1,readBytes(input,1)).intValue()));
         log.info("���: " + thisGRIB.getCentury());
         
         DateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yyyy");
         Date date = null;
		try {
			date = dateFormat.parse(Hours + ":" + Minutes + " " + Day + "." + Months + "." + ((thisGRIB.getCentury()-1)*100 + Year));
		} catch (ParseException e) {
			e.printStackTrace();
		}		
         
         thisGRIB.setDate(date);
         log.info("����� ���������� ������: "+ thisGRIB.getDate()); 

         thisGRIB.setSubcenterPointer((new BigInteger(1,readBytes(input,1)).intValue()));
         log.info("��������� ���������: " + thisGRIB.getSubcenterPointer());

       
        thisGRIB.setD((new BigInteger(1,readBytes(input,2)).intValue()));
         log.info("���������� �����������: " + thisGRIB.getD());

         log.info("������ �������� �����."); //������ �������� �����
         int p2l = 0;
         
         thisGRIB.setPart2Length((new BigInteger(1,readBytes(input,3)).intValue())); p2l=p2l-3;
         log.info("����� ������� �������� �����: " + thisGRIB.getPart2Length()); p2l = p2l + thisGRIB.getPart2Length();

        thisGRIB.setVerticalCoordParamsCount(new BigInteger(1,readBytes(input,1)).intValue()); p2l--;
         log.info("���������� ���������� ������������ ����������: " + thisGRIB.getVerticalCoordParamsCount());

        thisGRIB.setPV(readBytes(input,1)[0]);p2l--;
        if (thisGRIB.getPV() == -1)
             log.info("���� �� ������ �������������� ������� �����. ������. �����.");
        else
        {
             log.info("�������������� ������� ����������� ������������ ����������: " + thisGRIB.getPV());
        }

        
        thisGRIB.setDataType(new BigInteger(1,readBytes(input,1)).intValue()); p2l--;
        if (thisGRIB.getDataType() == 0)
             log.info("��� ������: �������-��������� �����.");
        else
        {
             log.info("��� ������: " + thisGRIB.getDataType());
        }


        thisGRIB.setParallelPointsCount(new BigInteger(1,readBytes(input,2)).intValue()); p2l=p2l -2;
        if (thisGRIB.getParallelPointsCount() == 65535)   thisGRIB.setParallelPointsCount(-1);
        
         log.info("���������� ����� ����� ���������: " +   thisGRIB.getParallelPointsCount());

        thisGRIB.setMeridianPointsCountl(new BigInteger(1,readBytes(input,2)).intValue()); p2l=p2l -2;
         log.info("���������� ����� ����� ���������: " + thisGRIB.getMeridianPointsCountl());

         
         Flag = readBytes(input,3);  
         BitSet tempSet =  BitSet.valueOf(Flag);
         int tempInt = 1;
         
         if ( tempSet.get(7) == true ) //��� 1 ���������� ����� ������
         {
         	tempSet.set(7,false);
         	tempInt = -1;         			
         }
        thisGRIB.setFirstPointLatitude(new BigInteger(1,tempSet.toByteArray()).intValue()*tempInt);p2l=p2l -3;
        log.info("������ ������ ����� �����: " + thisGRIB.getFirstPointLatitude());

         
         Flag = readBytes(input,3);  
         tempSet =  BitSet.valueOf(Flag);
         tempInt =1;
         
         if ( tempSet.get(7) == true )//��� 1 ���������� �������� �������
         {
         	tempSet.set(7,false);
         	tempInt = -1;         			
         }
        	
        thisGRIB.setFirstPointLongitude(new BigInteger(1,tempSet.toByteArray()).intValue()*tempInt);p2l=p2l -3;
         log.info("������� ������ ����� �����: " + thisGRIB.getFirstPointLongitude());

        byte[] Flag2 = readBytes(input,1);p2l--;

        thisGRIB.setIncrementsInTheDirectionsAreGiven(isSet(Flag2,7));
    
        if (thisGRIB.isIncrementsInTheDirectionsAreGiven())
             log.info("���������� �� ������������ ������.");
        else
        {  log.info("���������� �� ������������ �� ������."); }
        
        thisGRIB.setEarthSpheroid(isSet(Flag2, 6));
        if (thisGRIB.isEarthSpheroid())
             log.info("����� - �������.");
        else
        {  log.info("����� - ���."); }
        
        thisGRIB.setDesignedWithRespectToTheReferenceGrid(isSet(Flag2, 3));
        
        if (thisGRIB.isDesignedWithRespectToTheReferenceGrid())
             log.info("��������������� ���������� ��������� ������� u � v ������������ ����������� ����� � ������������ ���������� �������������� ���������� x � y");
        else
        {  log.info("������� ���������� ���� ������� u � v ������������ ���������� � ��������� �����������"); }

        
        Flag = readBytes(input,3);  
        tempSet =  BitSet.valueOf(Flag);
        tempInt =1;
        
        if ( tempSet.get(7) == true )//��� 1 ���������� ����� ������
        {
        	tempSet.set(7,false);
        	tempInt = -1;         			
        }
        
        thisGRIB.setLastPointLatitude(new BigInteger(1,tempSet.toByteArray()).intValue()*tempInt); p2l=p2l -3;
         log.info("������ ��������� ����� �����: " + thisGRIB.getLastPointLatitude());
         
         Flag = readBytes(input,3);  
         tempSet =  BitSet.valueOf(Flag);
         tempInt =1;
         
         if ( tempSet.get(7) == true )//��� 1 ���������� �������� �������
         {
         	tempSet.set(7,false);
         	tempInt = -1;         			
         }
        	
        thisGRIB.setLastPointLongitude(new BigInteger(1,tempSet.toByteArray()).intValue()*tempInt); p2l=p2l -3;
         log.info("������� ��������� ����� �����: " + thisGRIB.getLastPointLongitude());

        thisGRIB.setDi(new BigInteger(1,readBytes(input,2)).intValue()); p2l=p2l -2;
        if (thisGRIB.getDi() == 65535)   thisGRIB.setDi(-1);
        
        log.info("���������� � ����������� i: " + thisGRIB.getDi());
         
    

         thisGRIB.setDj(new BigInteger(1,readBytes(input,2)).intValue()); p2l=p2l -2;
         log.info("���������� � ����������� j: " + thisGRIB.getDj());

        byte[] Flag3 = readBytes(input,1); p2l--;

   
        thisGRIB.setScanToMinusI(isSet(Flag3, 7));
        
        if (thisGRIB.isScanToMinusI())
             log.info("������������ � ����������� -i.");
        else
        {  log.info("������������ � ����������� +i."); }
        thisGRIB.setScanToPlusJ(isSet(Flag3, 6));        
        
        if (thisGRIB.isScanToPlusJ())
             log.info("������������ � ����������� +j.");
        else
        	{  log.info("������������ � ����������� -j."); }
        
        thisGRIB.setNeighborPointsInDirectionIareConsecutive(isSet(Flag3, 5));
        if (thisGRIB.isNeighborPointsInDirectionIareConsecutive())
             log.info("�������� ����� � ����������� j �������� �����������������.");
        else
        {  log.info("�������� ����� � ����������� i �������� �����������������."); }



        @SuppressWarnings("unused")
		byte[] rezerv = readBytes(input, p2l);
        	
        
        

         log.info("������ �������� ������.");
         thisGRIB.setPart4Length(new BigInteger(1,readBytes(input, 3)).intValue());
         log.info("����� ������� �������� ������: " + thisGRIB.getPart4Length());



        byte[] Flag5 = readBytes(input, 1);

        
        thisGRIB.setCoeffOfSphericalHarminicFunction(isSet(Flag5, 7));
        thisGRIB.setDataByPointsOfGrib(!isSet(Flag5, 7));

        if (thisGRIB.isCoeffOfSphericalHarminicFunction())
             log.info("������������ ����������� ������������� �������.");
        if (thisGRIB.isDataByPointsOfGrib())
        {  log.info("������ �� ������ �����."); }
        
        thisGRIB.setSimplePackage(!isSet(Flag5, 6));        
        if (thisGRIB.isSimplePackage())
        	log.info("������� ��������."); 
        else
        {  log.info("������� ��������.");}
        
        thisGRIB.setInt(isSet(Flag5, 5));
        
        if (thisGRIB.isInt())
             log.info("������������ ����� �����.");
        else
        {  log.info("������������ �������� � ��������� �������."); }
        
        thisGRIB.setAdditionalFlagBits(isSet(Flag5, 4));
        if (thisGRIB.isAdditionalFlagBits())
             log.info("���� �������������� ���� ������.");
        else
        {  log.info("��� �������������� ������."); }

        byte[] mashKo = readBytes(input, 2);
        new BitSet();
		BitSet mashKobits = BitSet.valueOf(mashKo);          
        BitSet mashKobits1 =  BitSet.valueOf(mashKo);

        for (int i = 7; i >= 0; i--)
            mashKobits1.set(i + 8,mashKobits.get(i));
        for (int i = 15; i >= 8; i--)
            mashKobits1.set(i - 8,mashKobits.get(i));

        @SuppressWarnings("unused")
		double coe,coe1;
        double E = 0;

       if (mashKobits1.get(15) == true)
        {
             //log.info("����� -");
            mashKobits1.set(15,false);
            for (int i = 15; i >= 0; i--)
            {
                if (mashKobits1.get(i) == true)
                {
                    coe = Math.pow(10, i);
                    coe1 = Math.pow(2, i);
                    E += coe1 * 1;
                }
            } E = (-1) * E;
        }
        else
        {
             //log.info("����� +");
            mashKobits1.set(15,false);
            for (int i = 15; i >= 0; i--)
            {
                if (mashKobits1.get(i) == true)
                {
                    coe = Math.pow(10, i);
                    coe1 = Math.pow(2, i);
                    E += coe1 * 1;
                }
            }
        }

       thisGRIB.setScallingFactor(E);
        log.info("���������� �����������: " + thisGRIB.getScallingFactor());
        
        byte[] NOU = readBytes(input, 4);
        new BitSet();
		BitSet NObits = BitSet.valueOf(NOU);  
		BitSet NObits1 = new BitSet(24);

        double su = 0;
        double Au = 0;
        double Bu = 0;
        double coe2u;
        if (NObits.get(7) == true) su = -1; //���� ������� ��� ����� 1, �� s= -1. 
        for (int i = 6; i >= 0; i--)
        {//�������� ��������� ���������� �
            if (NObits.get(i) == true)
            {
                coe2u = Math.pow(2, i);
                Au += 1 * coe2u;
            }
        }
        int p0 = 23;
        for (int i = 15; i >= 8; i--)
        {
            if (NObits.get(i) == true) { NObits1.set(p0, true); }
            p0--;
        }
        for (int i = 23; i >= 16; i--)
        {
            if (NObits.get(i)  == true) { NObits1.set(p0, true);}
            p0--;
        }
        for (int i = 31; i >= 24; i--)
        {
            if (NObits.get(i)  == true) { NObits1.set(p0, true); }
            p0--;
        }
        for (int i = 23; i >= 0; i--)
        { //�������� ������� B
            if (NObits1.get(i)  == true)
            {
                coe2u = Math.pow(2, i);
                Bu += 1 * coe2u;
            }
        }       

        double R = su * Math.exp(-24 * Math.log(2)) * Bu * Math.exp((Au - 64) * Math.log(16));

        thisGRIB.setScanBegin(R); 
        
         log.info("������ �������: " +thisGRIB.getScanBegin());


        thisGRIB.setDataPackageLength(new BigInteger(1,readBytes(input, 1)).intValue());
         log.info("���������� ���, ���������� ������ �������� ��������: " + thisGRIB.getDataPackageLength());

        double E1 = Math.pow(2,thisGRIB.getScallingFactor());//���������� 2 � ������� E
        double D1 = Math.pow(10, thisGRIB.getD()); //���������� 10 � ������� D
        @SuppressWarnings("unused")
		int kol = thisGRIB.getParallelPointsCount() * thisGRIB.getMeridianPointsCountl(); //����������� ����� �����
        
        
        byte[] DataBytes = readBytes(input, thisGRIB.getPart4Length() - 11);        
        @SuppressWarnings("unused")
		int len = DataBytes.length;
        BitSet DataBits = BitSet.valueOf(DataBytes);  
        len = DataBits.length();
        
        double[] data = new double[ DataBits.length()/thisGRIB.getDataPackageLength()];
        len = data.length;
        int startBit = 0;
        int endBit = startBit + thisGRIB.getDataPackageLength()-1;
        int code;
        for (int i=0; i< data.length; i++) {  
        	
        	code = new BigInteger(1,DataBits.get(startBit, endBit).toByteArray()).intValue();
        	data[i] = (R + code * E1)/ D1;            // ������ �������� �� �������
        	startBit = endBit;
            endBit = startBit + thisGRIB.getDataPackageLength()-1;        	
		}

        int p = 0;
        
        if (thisGRIB.getDj() == -1 ) thisGRIB.setDj(thisGRIB.getDi());
        if (thisGRIB.getDi() == -1 ) thisGRIB.setDi(thisGRIB.getDj());
        
        int  Yu = thisGRIB.getFirstPointLatitude();
        int  Xu = thisGRIB.getFirstPointLongitude();
        
        int roundY = 0;
        int roundX = 0;
        if (thisGRIB.getLastPointLatitude()<thisGRIB.getFirstPointLatitude()) roundY= 90000;
        if (thisGRIB.getLastPointLongitude()<thisGRIB.getFirstPointLongitude()) roundX= 360000;
        
        int Ycmax = (thisGRIB.getLastPointLatitude() - Yu + roundY) / thisGRIB.getDj();       
        int Xcmax = (thisGRIB.getFirstPointLongitude() - Xu + roundX) / thisGRIB.getDi(); 
        

        
        	
        for (int Ycount = 0; Ycount<= Ycmax; Ycount++) //������ �� ����� 
        {   
            for (int  Xcount = 0; Xcount <=Xcmax; Xcount++)
            {

            	
            	if (p == data.length) break;            	
            
            	GRIB grib = new GRIB(Xu, Yu, data[p]);            	
            	
                grib.setDate(date);
                if (thisGRIB.isHasWindComponent_U())  grib.setType("U");
                if (thisGRIB.isHasWindComponent_V())  grib.setType("V");
                grib.setHeight(thisGRIB.getLevel());
                grib.setForecast(thisGRIB.getP1());

                p++;            
                Xu=Xu+thisGRIB.getDi();
                if (Xu == 360000) Xu = Xu - 360000;
                thisGRIB.getData().add(grib);


            }         
           
            Yu=Yu+thisGRIB.getDj();
            if (Yu == 90000) Yu = Yu - 90000;
        }
        log.info("����������� ����� �����: " + thisGRIB.getData().size());
        input.close();
        
		return thisGRIB;
        
        
	}

	private static byte[] readBytes(InputStream input, int length)
			throws IOException {
		byte[] data = new byte[length];
		input.read(data, 0, length);
		return data;
	}

	private static boolean isSet(byte[] arr, int bit) {
	    int index = bit / 8;  // Get the index of the array for the byte with this bit
	    int bitPosition = bit % 8;  // Position of this bit in a byte

	    return (arr[index] >> bitPosition & 1) == 1;
	}


	
	
}
