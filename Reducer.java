import java.io.*;
import java.util.*;
import java.lang.*;
/**
 * Reducer solves the following problem: given a set of sorted input files (each
 * containing the same type of data), merge them into one sorted file. 
 *
 */
public class Reducer {
    // list of files for stocking the PQ
    private List<FileIterator> fileList;
    private String type,dirName,outFile;

    public static void main(String[] args){
		if (args.length != 3) {
			System.out.println("Usage: java Reducer <weather|thesaurus> <dir_name> <output_file>");
			System.exit(1);
		}

		String type = args[0];
		String dirName = args[1];
		String outFile = args[2];

		Reducer r = new Reducer(type, dirName, outFile);
		r.run();
	
    }

	/**
	 * Constructs a new instance of Reducer with the given type (a string indicating which type of data is being merged),
	 * the directory which contains the files to be merged, and the name of the output file.
	 */
    public Reducer(String type, String dirName, String outFile) {
		this.type = type;
		this.dirName = dirName;
		this.outFile = outFile;
    }

	/**
	 * Carries out the file merging algorithm described in the assignment description. 
	 */
public void run(){
		File dir = new File(dirName);
		File[] files = dir.listFiles();
		Arrays.sort(files);

		Record r = null;

		// list of files for stocking the PQ
		fileList = new ArrayList<FileIterator>();

		for(int i = 0; i < files.length; i++) {
			File f = files[i];
			if(f.isFile() && f.getName().endsWith(".txt")) {
				fileList.add(new FileIterator(f.getAbsolutePath(), i));
			}
		}

		switch (type) {
		case "weather":
			r = new WeatherRecord(fileList.size());
			break;
		case "thesaurus":
			r = new ThesaurusRecord(fileList.size());
			break;
		default:
			System.out.println("Invalid type of data! " + type);
			System.exit(1);
		}
		
		// Make priority queue to hold fileLines, fill queue
		FileLinePriorityQueue q = 
				new FileLinePriorityQueue(files.length, r.getComparator());
		for(int i = 0;i < files.length;i++) 
		{
			try
			{
				q.insert(fileList.get(i).next());
			}
			catch (PriorityQueueFullException e)
			{
				System.out.println(e.getMessage());
			}
		}
		
		
		try
		{
			boolean emptyRecord = true;
			FileWriter fWriter = new FileWriter(outFile);
			PrintWriter pWriter = new PrintWriter(fWriter);
			while(!q.isEmpty()) 
			{

				FileLine e = q.removeMin();
				if (emptyRecord || e.equals(r.getComparator()))
				{
					r.join(e);
					emptyRecord = false;
				}
				else
				{
					pWriter.println(r);
					r.clear();
					emptyRecord = true;
					r.join(e);
				}
				FileLine f = e.getFileIterator().next();
				q.insert(f);
			}
			pWriter.println(r);
			pWriter.close();
		}
		catch (PriorityQueueEmptyException E)
		{
			System.out.println(E.getMessage());
		}
		catch (PriorityQueueFullException F)
		{
			System.out.println(F.getMessage());
		}
		catch (IOException I)
		{
			System.out.println(I.getMessage());
		}
    }
}
