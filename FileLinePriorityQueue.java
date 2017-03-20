/**
 * Semester:         CS367 Spring 2016 
 * PROJECT:          p3
 * FILE:             FileLinePiorityQueue
 *
 * Authors: Zexing Li(Richard)
 * Author1: Zexing Li(Richard), zli674wisc.edu, zexing, lec001
 *
 * ---------------- OTHER ASSISTANCE CREDITS 
 * Persons: N/A
 * 
 * Online sources: N/A
 */
import java.util.Comparator;

/*
 * An implementation of the MinPriorityQueueADT interface. This implementation stores FileLine objects.
 * See MinPriorityQueueADT.java for a description of each method. 
 */
public class FileLinePriorityQueue implements MinPriorityQueueADT<FileLine> {
    private FileLine[] queue = null;
    int size = 0;
    private Comparator<FileLine> cmp;
    private int maxSize;
    
    public FileLinePriorityQueue(int initialSize, Comparator<FileLine> cmp) {
		this.cmp = cmp;
		maxSize = initialSize;
		queue = new FileLine[maxSize];	
    }

    public FileLine removeMin() throws PriorityQueueEmptyException {
		//throw exception if the queue is empty
    	if(isEmpty()){
			throw new PriorityQueueEmptyException();
		}
		//reduce the size
    	size--;
    	//save the first node
    	FileLine min = queue[0];
    	//get the last node ready
    	FileLine tg = queue[size];
    	queue[size] = null;    //I want to save memory
    	//rearrange the queue
    	if(size != 0){
    		shiftDown(0, tg);
    	}
    	//return the first node
		return min;
    }
    private boolean shiftDown(int pos, FileLine tg) {
    	//get the deepest node which is not a leaf
    	int half = size / 2;
    	//while not leaf
    	while(pos < half){
    		//get left child
    		int leftchild = pos * 2 + 1;
    		//assume left child is larger
    		int child = leftchild;
    		FileLine cp = queue[leftchild];
    		//get right child
    		int rightchild = leftchild + 1;
    		//check the assumption
    		if((rightchild<size)&&cmp.compare(queue[rightchild], cp) < 0) {
    			child = rightchild;
    			cp = queue[rightchild];
    		}
    		//break if father node is larger
    		if(cmp.compare(tg, cp) <= 0) {
    			break;
    		}
    		//exchange child with current node
    		queue[pos] = cp;
    		pos = child; 		
    	}
    	//add the inserted node
    	queue[pos] = tg;
    	return true;
    }

    public void insert(FileLine fl) throws PriorityQueueFullException {
		if(size >= maxSize) {
			throw new PriorityQueueFullException();
		}
		if(fl==null){
			throw new NullPointerException();
		}
		if(size == 0) {
			queue[0] = fl;
		}
		else {
			shiftUp(size, fl);
		}
		size++;
    }
    private boolean shiftUp(int pos, FileLine fl) {
    	//while node has a father node
    	while(pos > 0) {
    		//get parent pos
    		int parent = (size - 1) / 2;
    		//get parent
    		FileLine p = queue[parent];
    		//if new node is greater than parent, break
    		if(cmp.compare(p, fl) <= 0) {
    			break;
    		}
    		//shiftdown father node
    		queue[pos] = p;
    		pos = parent;
    	}
    	//plug in new node
    	queue[pos] = fl;
    	return true;
    }
    
    public boolean isEmpty() {
		if(size == 0) {
			return true;
		}
		else {
			return false;
		}
    }
    
}

