package gribs;

import java.io.IOException;

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
            Value value = Value.createValue(valueArg.toString().getBytes("ISO-8859-1"));//Error
  


   		 	byte[] gribEnd = new byte[]{(byte)0x37,(byte)0x37,(byte)0x37,(byte)0x37}; //7777

   		 	Utils.splitGRIB(value.getValue(), gribEnd,key.toString());
   	      



    		}    
            
        
	

	
}