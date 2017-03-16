import java.util.Comparator;

/**
 * The WeatherRecord class is the child class of Record to be used when merging weather data. Station and Date
 * store the station and date associated with each weather reading that this object stores.
 * l stores the weather readings, in the same order as the files from which they came are indexed.
 */
public class WeatherRecord extends Record{
    private int ID;
    private int date;
    double[] info = new double[getNumFiles()];

	/**
	 * Constructs a new WeatherRecord by passing the parameter to the parent constructor
	 * and then calling the clear method()
	 */
    public WeatherRecord(int numFiles) {
		super(numFiles);
		clear();
    }
	
	/**
	 * This comparator should first compare the stations associated with the given FileLines. If 
	 * they are the same, then the dates should be compared. 
	 */
    private class WeatherLineComparator implements Comparator<FileLine> {
		public int compare(FileLine l1, FileLine l2) {
			String[] line1 = l1.getString().split(",");
			String[] line2 = l2.getString().split(",");
			int[] info1 = new int[3];
			int[] info2 = new int[3];
			
			for (int i = 0; i <= line1.length || i <= line2.length; i++) {
				info1[i] = Integer.parseInt(line1[i]);
				info2[i] = Integer.parseInt(line2[i]);
			}
			
			if (info1[0] == info2[0]) {
				return info1[1] - info2[1];
			}
			else {
				return info1[0] - info2[0];
			}
		}
		
		public boolean equals(Object o) {
			FileLine data = (FileLine) o;
			String[] line1 = data.getString().split(",");
			int Id = Integer.parseInt(line1[0]);
			int d8 = Integer.parseInt(line1[1]);
			
			if (Id == ID && d8 == date) {
				return true;
			}
			else {
				return false;
			}
		}
    }
    
	/**
	 * This method should simply create and return a new instance of the WeatherLineComparator
	 * class.
	 */
    public Comparator<FileLine> getComparator() {
		return new WeatherLineComparator();
    }
	
	/**
	 * This method should fill each entry in the data structure containing
	 * the readings with Double.MIN_VALUE
	 */
    public void clear() {
    	for (int i = 0; i < info.length; i++) {
    		info[1] = Double.MIN_VALUE;
    	}
    }

	/**
	 * This method should parse the String associated with the given FileLine to get the station, date, and reading
	 * contained therein. Then, in the data structure holding each reading, the entry with index equal to the parameter 
	 * FileLine's index should be set to the value of the reading. Also, so that
	 * this method will handle merging when this WeatherRecord is empty, the station and date associated with this
	 * WeatherRecord should be set to the station and date values which were similarly parsed.
	 */
    public void join(FileLine li) {
		String[] line = li.getString().split(",");
		int ID = Integer.parseInt(line[0]);
		int date = Integer.parseInt(line[1]);
		double info = Double.parseDouble(line[2]);
		
		this.ID = ID;
		this.date = date;
		this.info[li.getFileIterator().getIndex()] = info;
    }
	
	/**
	 * See the assignment description and example runs for the exact output format.
	 */
    public String toString() {
		String product = (ID + "," + date + ",");
		for (double data : info) {
			if (data == Double.MIN_VALUE) {
				product += ("-,");
			}
			else {
				product += (data + ",");
			}
		}
		product = product.substring(0, product.length() - 1);
		return product;
    }
}
