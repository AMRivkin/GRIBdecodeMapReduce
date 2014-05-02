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
            Value value = Value.createValue(valueArg.toString().getBytes());//???
            log.info("VALUE: "+valueArg.getBytes());
            log.info("VALUE: "+valueArg.toString().getBytes());

		//decode grib
		
		Structure thisGrib = Decode.BeginDecode(value.toByteArray(),key.getMinorPath().toString());

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