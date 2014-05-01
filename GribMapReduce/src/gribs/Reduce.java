package gribs;

import java.io.IOException;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.log4j.Logger;


	public class Reduce extends Reducer<Text, Text, NullWritable, NullWritable>{

		
		@SuppressWarnings("unused")
		private static final Logger log = Logger.getLogger(Reduce.class.getPackage()
				.getName());
		
		public void reduce(NullWritable key, BytesWritable value, Context context) throws IOException, InterruptedException 
		{		
			
			
		}
	}