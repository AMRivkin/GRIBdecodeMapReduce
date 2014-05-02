package gribs;

import java.io.IOException;
import java.util.ArrayList;

import oracle.kv.Key;
import oracle.kv.Value;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;



public class Map extends Mapper<Text, Text, Text, Text>{

	
	private static final Logger log = Logger.getLogger(Map.class.getPackage()
			.getName());
	
    public void map(Text keyArg, Text valueArg, Context context)
            throws IOException, InterruptedException {

            Key key = Key.fromString(keyArg.toString());
            log.info("KEY: "+key.toString());            	
            Value value = Value.createValue(valueArg.toString().getBytes());//Error
           
		//decode grib
            byte[] gribEnd = new byte[]{(byte)0x37,(byte)0x37,(byte)0x37,(byte)0x37}; //7777

            ArrayList<byte[]> gribs = Utils.splitGRIB(value.toByteArray(), gribEnd);
    				
    		for (byte[] gribBytes : gribs) {
    			Decode.BeginDecode(gribBytes,key.toString());
    			
    			Structure thisGrib = Decode.BeginDecode(valueArg.toString().getBytes(),key.getMinorPath().toString());

    			log.info("======================GRIB=====================");
    			//get list of data
    			for(GRIB element : thisGrib.getData())
    			{
    				log.info("Coordinate X: "+element.getCoordX() + " Coordinate Y: "+element.getCoordY());
    				log.info("Height: "+element.getHeight());
    				log.info("Type: "+element.getType());
    				log.info("Value: "+element.getValue());
    				log.info("===================================");			

    			}

    		}    
            
        
	
	
	}
	public static String bytesToHex(byte[] bytes) {
		char[] hexArray = "0123456789ABCDEF".toCharArray();	
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
}