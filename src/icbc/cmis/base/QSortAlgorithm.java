package icbc.cmis.base;

/**
 * 
 *   @(#) *.java	1.0 05/04/2000
 *   Copyright (c) 1999 EChannel R&D. All Rights Reserved.
 *  
 *  
 *   @version 1.0 05/04/2000
 *   @author  ZhongMingChang
 *   
 */
public class QSortAlgorithm {

//the int arry contain the finalposition of the sorted array
	private int[] finalPosition;
/**
 * QSortAlgorithm constructor comment.
 */
public QSortAlgorithm() {
	super();
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/04/2000
 *  
 * 
 * @return int
 * @param s1 java.lang.String
 * @param s2 java.lang.String
 */
private int compare(String s1, String s2) {
	try{
		if( s1 == null && s2 == null)
			return 0;
		else if( s1 == null && s2 != null)
		{
			return "null".compareTo( s2);
		}
		else if( s1!=null && s2 == null)
		{
			return s1.compareTo( "null");
		}
		return s1.compareTo( s2 );
	}catch(Exception e)
	{
		System.out.println( s1 + s2 + e);
		return 0;
	}
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/04/2000
 *  
 * 
 * @return int[]
 */
public int[] getFinalPosition() {
	return finalPosition;
}
   /** This is a generic version of C.A.R Hoare's Quick Sort 
	* algorithm.  This will handle arrays that are already
	* sorted, and arrays with duplicate keys.<BR>
	*
	* If you think of a one dimensional array as going from
	* the lowest index on the left to the highest index on the right
	* then the parameters to this function are lowest index or
	* left and highest index or right.  The first time you call
	* this function it will be with the parameters 0, a.length - 1.
	*
	* @param a       an integer array
	* @param lo0     left boundary of array partition
	* @param hi0     right boundary of array partition
	*/
	
private  void QuickSort(int a[], int lo0, int hi0) throws Exception
   {
	  int lo = lo0;
	  int hi = hi0;
	  int mid;

	  if ( hi0 > lo0)
	  {

		 /* Arbitrarily establishing partition element as the midpoint of
		  * the array.
		  */
		 mid = a[ ( lo0 + hi0 ) / 2 ];

		 // loop through the array until indices cross
		 while( lo <= hi )
		 {
			/* find the first element that is greater than or equal to 
			 * the partition element starting from the left Index.
			 */
	     while( ( lo < hi0 ) && ( a[lo] < mid ))
		 ++lo;

			/* find an element that is smaller than or equal to 
			 * the partition element starting from the right Index.
			 */
	     while( ( hi > lo0 ) && ( a[hi] > mid ))
		 --hi;

			// if the indexes have not crossed, swap
			if( lo <= hi ) 
			{
			   swap(a, lo, hi);
			   ++lo;
			   --hi;
			}
		 }

		 /* If the right index has not reached the left side of array
		  * must now sort the left partition.
		  */
		 if( lo0 < hi )
			QuickSort( a, lo0, hi );

		 /* If the left index has not reached the right side of array
		  * must now sort the right partition.
		  */
		 if( lo < hi0 )
			QuickSort( a, lo, hi0 );

	  }
  }
   /** This is a generic version of C.A.R Hoare's Quick Sort 
	* algorithm.  This will handle arrays that are already
	* sorted, and arrays with duplicate keys.<BR>
	*
	* If you think of a one dimensional array as going from
	* the lowest index on the left to the highest index on the right
	* then the parameters to this function are lowest index or
	* left and highest index or right.  The first time you call
	* this function it will be with the parameters 0, a.length - 1.
	*
	* @param a       an integer array
	* @param lo0     left boundary of array partition
	* @param hi0     right boundary of array partition
	*/
	
private  void QuickSort(String a[], int lo0, int hi0) throws Exception
   {
	  int lo = lo0;
	  int hi = hi0;
	  String mid;

	  if ( hi0 > lo0)
	  {

		 /* Arbitrarily establishing partition element as the midpoint of
		  * the array.
		  */
		 mid = a[ ( lo0 + hi0 ) / 2 ];

		 // loop through the array until indices cross
		 while( lo <= hi )
		 {
			/* find the first element that is greater than or equal to 
			 * the partition element starting from the left Index.
			 */
	     while( ( lo < hi0 ) && compare( a[lo],  mid )<0 ) //a[lo]<mid
		 ++lo;

			/* find an element that is smaller than or equal to 
			 * the partition element starting from the right Index.
			 */
	     while( ( hi > lo0 ) && compare(a[hi], mid) > 0) // ( a[hi] > mid ))
		 --hi;

			// if the indexes have not crossed, swap
			if( lo <= hi ) 
			{
			   swap(a, lo, hi);
			   ++lo;
			   --hi;
			}
		 }

		 /* If the right index has not reached the left side of array
		  * must now sort the left partition.
		  */
		 if( lo0 < hi )
			QuickSort( a, lo0, hi );

		 /* If the left index has not reached the right side of array
		  * must now sort the right partition.
		  */
		 if( lo < hi0 )
			QuickSort( a, lo, hi0 );

	  }
  }
public void sort(int a[]) throws Exception
{
	finalPosition = new int[a.length];
	for( int i=0; i<a.length; i++)
		finalPosition[i] = i;
		
	QuickSort(a, 0, a.length - 1);
}
public void sort(String a[]) throws Exception
{
	finalPosition = new int[a.length];
	for( int i=0; i<a.length; i++)
		finalPosition[i] = i;
		
	QuickSort(a, 0, a.length - 1);
}
private void swap(int a[], int i, int j)
{
	int T;
	T = a[i];
	a[i] = a[j];
	a[j] = T;
	swapPosition(i, j);
}
private void swap(String a[], int i, int j)
{
	String T;
	T = a[i];
	a[i] = a[j];
	a[j] = T;
	swapPosition(i, j);
}
private void swapPosition(int i, int j)
{
	int T;
	T = finalPosition[i];
	finalPosition[i] = finalPosition[j];
	finalPosition[j] = T;
}
}
