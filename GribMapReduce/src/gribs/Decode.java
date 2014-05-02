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
          
          //Поиск GRIB в GRIB файле
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
			log.info("Это GRIB.");
		} else {
			log.info("Файл не является GRIB файлом.");
			return null;
		}
		
		log.info("Раздел определения продукции."); //Раздел определения продукции
		thisGRIB.setLength(new BigInteger(1,readBytes(input, 3)).intValue());
		log.info("Длина GRIB файла: " + thisGRIB.getLength()); 
		
		thisGRIB.setVersion(new BigInteger(1,readBytes(input, 1)).intValue());
		log.info("Версия GRIB файла: " + thisGRIB.getVersion()); 	
		
		thisGRIB.setPart1Length(new BigInteger(1,readBytes(input,	3)).intValue());	
        log.info("Длина раздела определения продукции: " +thisGRIB.getPart1Length());
        
        if (new BigInteger(1,readBytes(input,	1)).intValue() == 2) {
        	thisGRIB.setInterantional(true); log.info("Международный обмен.");
        }
        else
        {
        	thisGRIB.setInterantional(false); log.info("Локальный обмен.");
        }
        
        thisGRIB.setWhereFrom(new BigInteger(1,readBytes(input, 1)).intValue());
        if (thisGRIB.getWhereFrom() == 4) log.info("Файл получен из Москвы :)");
        if (thisGRIB.getWhereFrom() == 74) log.info("Метеорологическое бюро СК - Брекнелл.");
       
        @SuppressWarnings("unused")
		byte[] empty = readBytes(input, 1);
             
        thisGRIB.setGridType(new BigInteger(1,readBytes(input, 1)).intValue());
        log.info("Тип сетки: " + thisGRIB.getGridType());


        byte[] Flag = readBytes(input,1);
          
        thisGRIB.setPart2Included(isSet(Flag, 7));
        thisGRIB.setPart3Included(isSet(Flag, 6));
        
        if (thisGRIB.isPart2Included()) 
        	log.info("Раздел 2 включен."); 
        else 
        	log.info("Раздел 2 не включен.");
        
        if (thisGRIB.isPart3Included()) 
       	 	log.info("Раздел 3 включен."); 
       else 
       		log.info("Раздел 3 не включен.");
       

        int UkazParam = new BigInteger(1,readBytes(input,1)).intValue(); 

        if (UkazParam == 33)
        {
             log.info("Файл содержит компонент ветра U.");
             thisGRIB.setHasWindComponent_U(true);
             thisGRIB.setHasWindComponent_V(false);
        }
        if (UkazParam == 34)
        {
             log.info("Файл содержит компонент ветра V.");
             thisGRIB.setHasWindComponent_U(false);
             thisGRIB.setHasWindComponent_V(true);
        }

        thisGRIB.setLevelType(new BigInteger(1,readBytes(input,1)).intValue()); 
        if (thisGRIB.getLevelType() == 100)
             log.info("Тип уровня - изобарическая поверхность.");

        
        thisGRIB.setLevel(new BigInteger(1,readBytes(input,2)).intValue());
        log.info("Высота: "+thisGRIB.getLevel());

        //дата

        int Year = new BigInteger(1,readBytes(input,1)).intValue();
        int Months = new BigInteger(1,readBytes(input,1)).intValue();
        int Day = new BigInteger(1,readBytes(input,1)).intValue();
        int Hours = new BigInteger(1,readBytes(input,1)).intValue();
        int Minutes = new BigInteger(1,readBytes(input,1)).intValue();  
          
         
         thisGRIB.setUnitOfTimeIndex(new BigInteger(1,readBytes(input,1)).intValue());

        if (thisGRIB.getUnitOfTimeIndex() == 1)
             log.info("Указатель единицы времени: Час");
        else
        {
             log.info("Указатель единицы времени: " + thisGRIB.getUnitOfTimeIndex());
        }
        
        
        
         thisGRIB.setP1(new BigInteger(1,readBytes(input,1)).intValue());
         log.info("P1 - период времени: " + thisGRIB.getP1());
         
         thisGRIB.setP2(new BigInteger(1,readBytes(input,1)).intValue());
         log.info("P2 - период времени: " + thisGRIB.getP2());

         thisGRIB.setTimePointer(new BigInteger(1,readBytes(input,1)).intValue());
         log.info("Указатель времени: " + thisGRIB.getTimePointer());

         
         thisGRIB.setNumberOfCases(new BigInteger(1,readBytes(input,2)).intValue());
         log.info("Число случаев: " + thisGRIB.getNumberOfCases());

         thisGRIB.setNumberOfCasesLost(new BigInteger(1,readBytes(input,1)).intValue());
         log.info("Число утраченных случаев: " + thisGRIB.getNumberOfCasesLost());

         thisGRIB.setCentury((new BigInteger(1,readBytes(input,1)).intValue()));
         log.info("Век: " + thisGRIB.getCentury());
         
         DateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yyyy");
         Date date = null;
		try {
			date = dateFormat.parse(Hours + ":" + Minutes + " " + Day + "." + Months + "." + ((thisGRIB.getCentury()-1)*100 + Year));
		} catch (ParseException e) {
			e.printStackTrace();
		}		
         
         thisGRIB.setDate(date);
         log.info("Время подготовки данных: "+ thisGRIB.getDate()); 

         thisGRIB.setSubcenterPointer((new BigInteger(1,readBytes(input,1)).intValue()));
         log.info("Указатель подцентра: " + thisGRIB.getSubcenterPointer());

       
        thisGRIB.setD((new BigInteger(1,readBytes(input,2)).intValue()));
         log.info("Масштабный коэффициент: " + thisGRIB.getD());

         log.info("Раздел описания сетки."); //Раздел описания сетки
         int p2l = 0;
         
         thisGRIB.setPart2Length((new BigInteger(1,readBytes(input,3)).intValue())); p2l=p2l-3;
         log.info("Длина раздела описания сетки: " + thisGRIB.getPart2Length()); p2l = p2l + thisGRIB.getPart2Length();

        thisGRIB.setVerticalCoordParamsCount(new BigInteger(1,readBytes(input,1)).intValue()); p2l--;
         log.info("Количесвто параметров вертикальной координаты: " + thisGRIB.getVerticalCoordParamsCount());

        thisGRIB.setPV(readBytes(input,1)[0]);p2l--;
        if (thisGRIB.getPV() == -1)
             log.info("Нету ни одного местоположения перечня парам. вертик. коорд.");
        else
        {
             log.info("Местоположение перечня парамаетров вертикальной координаты: " + thisGRIB.getPV());
        }

        
        thisGRIB.setDataType(new BigInteger(1,readBytes(input,1)).intValue()); p2l--;
        if (thisGRIB.getDataType() == 0)
             log.info("Тип данных: Широтно-долготная сетка.");
        else
        {
             log.info("Тип данных: " + thisGRIB.getDataType());
        }


        thisGRIB.setParallelPointsCount(new BigInteger(1,readBytes(input,2)).intValue()); p2l=p2l -2;
        if (thisGRIB.getParallelPointsCount() == 65535)   thisGRIB.setParallelPointsCount(-1);
        
         log.info("Количество точек вдоль параллели: " +   thisGRIB.getParallelPointsCount());

        thisGRIB.setMeridianPointsCountl(new BigInteger(1,readBytes(input,2)).intValue()); p2l=p2l -2;
         log.info("Количество точек вдоль меридиана: " + thisGRIB.getMeridianPointsCountl());

         
         Flag = readBytes(input,3);  
         BitSet tempSet =  BitSet.valueOf(Flag);
         int tempInt = 1;
         
         if ( tempSet.get(7) == true ) //бит 1 обозначает южную широту
         {
         	tempSet.set(7,false);
         	tempInt = -1;         			
         }
        thisGRIB.setFirstPointLatitude(new BigInteger(1,tempSet.toByteArray()).intValue()*tempInt);p2l=p2l -3;
        log.info("Широта первой точки сетки: " + thisGRIB.getFirstPointLatitude());

         
         Flag = readBytes(input,3);  
         tempSet =  BitSet.valueOf(Flag);
         tempInt =1;
         
         if ( tempSet.get(7) == true )//бит 1 обозначает западную долготу
         {
         	tempSet.set(7,false);
         	tempInt = -1;         			
         }
        	
        thisGRIB.setFirstPointLongitude(new BigInteger(1,tempSet.toByteArray()).intValue()*tempInt);p2l=p2l -3;
         log.info("Долгота первой точки сетки: " + thisGRIB.getFirstPointLongitude());

        byte[] Flag2 = readBytes(input,1);p2l--;

        thisGRIB.setIncrementsInTheDirectionsAreGiven(isSet(Flag2,7));
    
        if (thisGRIB.isIncrementsInTheDirectionsAreGiven())
             log.info("Приращения по направлениям даются.");
        else
        {  log.info("Приращения по направлениям не даются."); }
        
        thisGRIB.setEarthSpheroid(isSet(Flag2, 6));
        if (thisGRIB.isEarthSpheroid())
             log.info("Земля - сфероид.");
        else
        {  log.info("Земля - Шар."); }
        
        thisGRIB.setDesignedWithRespectToTheReferenceGrid(isSet(Flag2, 3));
        
        if (thisGRIB.isDesignedWithRespectToTheReferenceGrid())
             log.info("Спроектированне компоненты векторных велечин u и v относительно определенно сетки в направлениях увеличения соответственно координаты x и y");
        else
        {  log.info("Спроект компоненты вект велечин u и v относительно восточного и северного направлений"); }

        
        Flag = readBytes(input,3);  
        tempSet =  BitSet.valueOf(Flag);
        tempInt =1;
        
        if ( tempSet.get(7) == true )//бит 1 обозначает южную широту
        {
        	tempSet.set(7,false);
        	tempInt = -1;         			
        }
        
        thisGRIB.setLastPointLatitude(new BigInteger(1,tempSet.toByteArray()).intValue()*tempInt); p2l=p2l -3;
         log.info("Широта последней точки сетки: " + thisGRIB.getLastPointLatitude());
         
         Flag = readBytes(input,3);  
         tempSet =  BitSet.valueOf(Flag);
         tempInt =1;
         
         if ( tempSet.get(7) == true )//бит 1 обозначает западную долготу
         {
         	tempSet.set(7,false);
         	tempInt = -1;         			
         }
        	
        thisGRIB.setLastPointLongitude(new BigInteger(1,tempSet.toByteArray()).intValue()*tempInt); p2l=p2l -3;
         log.info("Долгота последней точки сетки: " + thisGRIB.getLastPointLongitude());

        thisGRIB.setDi(new BigInteger(1,readBytes(input,2)).intValue()); p2l=p2l -2;
        if (thisGRIB.getDi() == 65535)   thisGRIB.setDi(-1);
        
        log.info("Приращение в направлении i: " + thisGRIB.getDi());
         
    

         thisGRIB.setDj(new BigInteger(1,readBytes(input,2)).intValue()); p2l=p2l -2;
         log.info("Приращение в направлении j: " + thisGRIB.getDj());

        byte[] Flag3 = readBytes(input,1); p2l--;

   
        thisGRIB.setScanToMinusI(isSet(Flag3, 7));
        
        if (thisGRIB.isScanToMinusI())
             log.info("Сканирование в направлении -i.");
        else
        {  log.info("Сканирование в направлении +i."); }
        thisGRIB.setScanToPlusJ(isSet(Flag3, 6));        
        
        if (thisGRIB.isScanToPlusJ())
             log.info("Сканирование в направлении +j.");
        else
        	{  log.info("Сканирование в направлении -j."); }
        
        thisGRIB.setNeighborPointsInDirectionIareConsecutive(isSet(Flag3, 5));
        if (thisGRIB.isNeighborPointsInDirectionIareConsecutive())
             log.info("Соседние точки в направлении j являются последовательными.");
        else
        {  log.info("Соседние точки в направлении i являются последовательными."); }



        @SuppressWarnings("unused")
		byte[] rezerv = readBytes(input, p2l);
        	
        
        

         log.info("Раздел двоичных данных.");
         thisGRIB.setPart4Length(new BigInteger(1,readBytes(input, 3)).intValue());
         log.info("Длина раздела двоичных данных: " + thisGRIB.getPart4Length());



        byte[] Flag5 = readBytes(input, 1);

        
        thisGRIB.setCoeffOfSphericalHarminicFunction(isSet(Flag5, 7));
        thisGRIB.setDataByPointsOfGrib(!isSet(Flag5, 7));

        if (thisGRIB.isCoeffOfSphericalHarminicFunction())
             log.info("Коэффициенты сферических гармонических функций.");
        if (thisGRIB.isDataByPointsOfGrib())
        {  log.info("Данные по точкам сетки."); }
        
        thisGRIB.setSimplePackage(!isSet(Flag5, 6));        
        if (thisGRIB.isSimplePackage())
        	log.info("Простая упаковка."); 
        else
        {  log.info("Сложная упаковка.");}
        
        thisGRIB.setInt(isSet(Flag5, 5));
        
        if (thisGRIB.isInt())
             log.info("Представлены целые числа.");
        else
        {  log.info("Представлены величины с плавающей запятой."); }
        
        thisGRIB.setAdditionalFlagBits(isSet(Flag5, 4));
        if (thisGRIB.isAdditionalFlagBits())
             log.info("Есть дополнительные биты флагов.");
        else
        {  log.info("Нет дополнительных флагов."); }

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
             //log.info("Будет -");
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
             //log.info("Будет +");
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
        log.info("Масштабный коэффициент: " + thisGRIB.getScallingFactor());
        
        byte[] NOU = readBytes(input, 4);
        new BitSet();
		BitSet NObits = BitSet.valueOf(NOU);  
		BitSet NObits1 = new BitSet(24);

        double su = 0;
        double Au = 0;
        double Bu = 0;
        double coe2u;
        if (NObits.get(7) == true) su = -1; //Если нулевой бит равен 1, то s= -1. 
        for (int i = 6; i >= 0; i--)
        {//Значение смещенной экспоненты А
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
        { //Значение мантисы B
            if (NObits1.get(i)  == true)
            {
                coe2u = Math.pow(2, i);
                Bu += 1 * coe2u;
            }
        }       

        double R = su * Math.exp(-24 * Math.log(2)) * Bu * Math.exp((Au - 64) * Math.log(16));

        thisGRIB.setScanBegin(R); 
        
         log.info("Начало отсчета: " +thisGRIB.getScanBegin());


        thisGRIB.setDataPackageLength(new BigInteger(1,readBytes(input, 1)).intValue());
         log.info("Количество бит, содержащих каждую пакетную велечину: " + thisGRIB.getDataPackageLength());

        double E1 = Math.pow(2,thisGRIB.getScallingFactor());//Вычисление 2 в степени E
        double D1 = Math.pow(10, thisGRIB.getD()); //Вычисление 10 в степени D
        @SuppressWarnings("unused")
		int kol = thisGRIB.getParallelPointsCount() * thisGRIB.getMeridianPointsCountl(); //Колличество точек сетки
        
        
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
        	data[i] = (R + code * E1)/ D1;            // Расчет значения по формуле
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
        

        
        	
        for (int Ycount = 0; Ycount<= Ycmax; Ycount++) //проход по сетке 
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
        log.info("Колличество точек сетки: " + thisGRIB.getData().size());
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
