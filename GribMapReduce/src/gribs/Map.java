package gribs;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.log4j.Logger;



public class Map extends Mapper<NullWritable, BytesWritable, MapKey, Text>{

	
	private static final Logger log = Logger.getLogger(Map.class.getPackage()
			.getName());
	
	public void map(NullWritable key, BytesWritable value, Context context) throws IOException, InterruptedException 
	{		
		//get GRIB	
		Path filePath= ((FileSplit)context.getInputSplit()).getPath();
		long fileSizeLong= ((FileSplit)context.getInputSplit()).getLength();		
		
		String fileNameString = filePath.getName();

		log.info("FILEPATH: "+filePath.toString());
		log.info("FILESIZE: "+fileSizeLong);			


		 byte[] gribEnd = new byte[]{(byte)0x37,(byte)0x37,(byte)0x37,(byte)0x37}; //7777

	     Utils.splitGRIB(value.copyBytes(), gribEnd,fileNameString);
	      




			
	
	
	}
}