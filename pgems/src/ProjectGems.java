import java.util.Arrays;
import java.util.Random;

public class ProjectGems {

	public static class Student implements Comparable<Student>{
		
		public Student(Integer sat, Double gpa){
			this._sat = sat;
			this._gpa = gpa;
		}
		
		public static Double MAX_GPA = 4.0;
		public static Integer MAX_SAT = 100;
		
		private Integer _sat;
		public Integer sat(){
			return _sat;
		}
		
		private Double _gpa;
		public Double gpa(){
			return _gpa;
		}
		
		@Override
		public int compareTo(Student obj)
		{
			return this._sat.compareTo(obj.sat());
		}
		
		@Override
		public String toString()
		{
			return "SAT: " + _sat + " , " + "GPA: " + _gpa;
		}
	}
	
	private static class Pair implements Comparable<Pair>{
		public Pair(int dist, int prev)
		{
			this.dist = dist;
			this.prev = prev;
		}
		
		public int dist;
		public int prev;
		
		@Override
		public int compareTo(Pair obj)
		{
			return ( new Integer(this.dist).compareTo(obj.dist) );
		}
	}
	
	private static Student[] generateSampleDataset(int numOfSamples)
	{
		Student[] dataset = new Student[numOfSamples];
		Random random = new Random();
		/*randomly generate 100 students with gpa and sat scores*/
		for ( int i=0; i < numOfSamples; i++ )
		{
			int sat = random.nextInt(Student.MAX_SAT + 1);
			double gpa = Student.MAX_GPA * random.nextDouble();
			dataset[i] = new Student( sat, gpa );
		}
		
		return dataset;
	}
	
	private static void printElementsInArray(Object[] arr)
	{
		for( int count = 0; count<arr.length; count++)
		{
			System.out.println( arr[count].toString() );
		}
	}
	
	private static void initDist()
	{
		for( int count=0; count < _distances.length; count ++ )
		{
			_distances[count] = new Pair(1,-1);
		}
	}
	
	private static void calculateDist()
	{
		/* O(n^2) solution*/
		_maxDist = 0;
		_indexOfMaxDist = -1;
		int dataSize = _dataset.length;
		for( int j=1; j< dataSize; j++ )
		{
			for ( int k=j-1; k>=0; k-- )
			{
				if( ( _dataset[j].gpa() < _dataset[k].gpa() ) && ( _distances[j].dist <= _distances[k].dist ))
				{
					_distances[j].dist = _distances[k].dist + 1;
					if(_distances[j].dist > _maxDist)
					{
						_maxDist = _distances[j].dist;
						_indexOfMaxDist = j;
					}
					_distances[j].prev = k;
				}
			}
		}
	}
	
	private static void printResults()
	{
		int parent = _indexOfMaxDist;
		String result = "";
		while(parent>=0)
		{
			result = _dataset[parent].toString() +'\n' + result;
			
			parent = _distances[parent].prev;
		}
		System.out.println( "Results: " );
		System.out.print(result);
	}
	
	private static int _maxDist = 0;
	private static int _indexOfMaxDist = -1;
	private static Student[] _dataset = null;
	private static Pair[] _distances = null; 
	public static void main(String args[]){
		
		int totalStudents = 10;
		
		_dataset = generateSampleDataset(totalStudents);
		
		Arrays.sort(_dataset); /*sort the students in increasing order by their sat scores in O(n *log (n)) time*/
		printElementsInArray(_dataset);
		
		_distances = new Pair[totalStudents];
		initDist();
		calculateDist();
		printResults();
	}
}
