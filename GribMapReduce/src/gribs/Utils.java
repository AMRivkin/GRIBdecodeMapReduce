package gribs;

import java.io.IOException;
import java.util.Arrays;

import org.apache.log4j.Logger;

public class Utils {

	private static final Logger log = Logger.getLogger(Utils.class.getPackage()
			.getName());
	
	public static void splitGRIB(byte[] data, byte[] pattern,String fileNameString) throws IOException
	{
		
	
		int positionStart = 0;
		int positionEnd = 0;
		byte[] newdata = data;

		while (true)
		{
			newdata = Arrays.copyOf(newdata,newdata.length - positionEnd);
	 		positionEnd = indexOf(newdata, pattern);
		if (positionEnd>0)
		{
			positionEnd = positionEnd + pattern.length;
			
			Decode.BeginDecode(Arrays.copyOfRange(newdata, positionStart, positionEnd),fileNameString);
		}
		else
		break;
		}
		
		
	}
	
	
	
	
	
	
	
	 public static int indexOf(byte[] data, byte[] pattern) {
	        int[] failure = computeFailure(pattern);

	        int j = 0;
	        if (data.length == 0) return -1;

	        for (int i = 0; i < data.length; i++) {
	            while (j > 0 && pattern[j] != data[i]) {
	                j = failure[j - 1];
	            }
	            if (pattern[j] == data[i]) { j++; }
	            if (j == pattern.length) {
	                return i - pattern.length + 1;
	            }
	        }
	        return -1;
	    }
	 
	 private static int[] computeFailure(byte[] pattern) {
	        int[] failure = new int[pattern.length];

	        int j = 0;
	        for (int i = 1; i < pattern.length; i++) {
	            while (j > 0 && pattern[j] != pattern[i]) {
	                j = failure[j - 1];
	            }
	            if (pattern[j] == pattern[i]) {
	                j++;
	            }
	            failure[i] = j;
	        }

	        return failure;
	    }
}
