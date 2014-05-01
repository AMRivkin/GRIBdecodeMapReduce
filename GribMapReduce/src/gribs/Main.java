package gribs;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

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

		//File gribFile = new File("C:\\Users\\arivkin\\Documents\\ÄÄ\\gribFiles\\HUJK70_EGRR_301200.dat");
		
		//Map.BeginDecode(gribFile);
		
		int res = ToolRunner.run(new Configuration(), new Main(), args);
		System.exit(res);

	}

	

		public int run(String[] args) throws Exception {
			
			Configuration conf = getConf();	
				
			if (args.length >0)	
			{
			log.info(args[0]);
			FileSystem fs = FileSystem.get(conf) ;
			Path[] inputPaths = getRecursivePaths(fs,args[0]);
			log.info("inputPaths: "+inputPaths.length);
								
			
			String jobName = "GRIB analysis";
			Job job = new Job(conf, jobName);
			
			job.setNumReduceTasks(0);

			FileInputFormat.setInputPaths(job, inputPaths);

			job.setJarByClass(gribs.Map.class);
			job.setInputFormatClass(WholeFileInputFormat.class);
			job.setOutputFormatClass(NullOutputFormat.class);
			//FileOutputFormat.setOutputPath(job, new Path(args[1]));
			job.setMapperClass(gribs.Map.class);
			return job.waitForCompletion(true) ? 0 : 1;	
			}
			else
				System.out.println("Enter path");
				return 1;
		}
		

	   

	public static Path[] getRecursivePaths(FileSystem fs, String basePath) 
		  throws IOException, URISyntaxException {
		    List<Path> result = new ArrayList<Path>();
		    basePath = fs.getUri() + basePath;
		    FileStatus[] listStatus = fs.globStatus(new Path(basePath+"/*"));
		    for (FileStatus fstat : listStatus) {
		      readSubDirectory(fstat, basePath, fs, result);
		    }
		    return (Path[]) result.toArray(new Path[result.size()]);  
		}

		private static void readSubDirectory(FileStatus fileStatus, String basePath,
		  FileSystem fs, List<Path> paths) throws IOException, URISyntaxException {
		  if (!fileStatus.isDirectory()) {			  	  
					  paths.add(fileStatus.getPath());
		  }
		  else {
		    String subPath = fileStatus.getPath().toString();
		    FileStatus[] listStatus = fs.globStatus(new Path(subPath + "/*"));
		    if (listStatus.length == 0) {
					 paths.add(fileStatus.getPath());				  
		    }
		    for (FileStatus fst : listStatus) {
		      readSubDirectory(fst, subPath, fs, paths);
		    }
		  }
		}
		


		
		

		
		
		
		
		

	}


	
	
	


