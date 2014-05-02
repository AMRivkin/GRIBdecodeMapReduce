package gribs;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import oracle.kv.hadoop.KVInputFormat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.NullOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;




public class Main extends Configured implements Tool{
	

	/**
	 * @param args
	 * @throws Exception 
	 */
	private static final Logger log = Logger.getLogger(Main.class.getPackage()
			.getName());
	
	

	public static void main(String[] args) throws Exception {

		//java.nio.file.Path gribFile = Paths.get("C:\\Users\\arivkin\\Documents\\ÄÄ\\gribFiles\\HUJK70_EGRR_301200.dat");
		
		//Decode.BeginDecode(Files.readAllBytes(gribFile),gribFile.getFileName().toString());
		
		int res = ToolRunner.run(new Configuration(), new Main(), args);
		System.exit(res);

	}

	

		public int run(String[] args) throws Exception {
			
			Configuration conf = getConf();	
				
			if (args.length >0)	
			{
								
			String jobName = "GRIB analysis from NoSQL";
			Job job = new Job(conf, jobName);			
			
			job.setNumReduceTasks(0);

			job.setJarByClass(gribs.Map.class);
			job.setInputFormatClass(WholeFileInputFormat.class);
	        job.setInputFormatClass(KVInputFormat.class);

	        log.info("KVStoreName: "+args[0]);
	        KVInputFormat.setKVStoreName(args[0]);
	        log.info("NoSQL hosts: "+args[1]);
	        KVInputFormat.setKVHelperHosts(new String[] { args[1] });		
			
			job.setOutputFormatClass(NullOutputFormat.class);
			job.setMapperClass(gribs.Map.class);
			return job.waitForCompletion(true) ? 0 : 1;	
			}
			else
				System.out.println("Enter KVStoreName and NoSQL hosts");
				return 1;
		}
		
	}


	
	
	


