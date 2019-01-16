import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class DataGenerator {
	public static List<Point> readData( String path ) throws IOException {
		List<Point> data = new ArrayList<Point>();

		FileReader fr = new FileReader( path );
		BufferedReader br = new BufferedReader(fr);

		String line = "";
		while( (line = br.readLine()) != null ) {
			String[] divs = line.split("\t");
			
			double tsne1 = Double.valueOf( divs[0] );
			double tsne2 = Double.valueOf( divs[1] );
			String cl = divs[2];
			
			Point p = new Point( "Group" + cl, tsne1, tsne2, -1 );
			data.add( p );
		}
		br.close();
		fr.close();
		
		return data;
	}

	public static List<Point> makeData( int size ) {
		List<Point> data = new ArrayList<Point>();
	
		
//		double g1 = Math.random() * 0.5;
//		double g2 = (1 - g1) * (Math.random() * 0.5);
//		double g3 = (1 - g1 - g2) * (Math.random() * 0.5);

		double g1 = 0.40;
		double g2 = 0.37;
		double g3 = 0.18;
		
		int nG1 = (int) Math.floor( g1 * size );
		int nG2 = (int) Math.floor( g2 * size );
		int nG3 = (int) Math.floor( g3 * size );
		int nG4 = size - (nG1 + nG2 + nG3);
		
//		data.addAll( DataGenerator.makePoints("Group1", nG1, DataGenerator.getRandomMeanValue(200),	DataGenerator.getRandomVarValue(20),		DataGenerator.getRandomMeanValue(200),	DataGenerator.getRandomVarValue(20),	DataGenerator.getRandomMeanValue(200), DataGenerator.getRandomVarValue(20)) );
//		data.addAll( DataGenerator.makePoints("Group2", nG2, DataGenerator.getRandomMeanValue(200),	DataGenerator.getRandomVarValue(20),		DataGenerator.getRandomMeanValue(200),	DataGenerator.getRandomVarValue(20),	DataGenerator.getRandomMeanValue(200), DataGenerator.getRandomVarValue(20)) );
//		data.addAll( DataGenerator.makePoints("Group3", nG3, DataGenerator.getRandomMeanValue(200),	DataGenerator.getRandomVarValue(20),		DataGenerator.getRandomMeanValue(200),	DataGenerator.getRandomVarValue(20),	DataGenerator.getRandomMeanValue(200), DataGenerator.getRandomVarValue(20)) );
//		data.addAll( DataGenerator.makePoints("Group4", nG4, DataGenerator.getRandomMeanValue(200),	DataGenerator.getRandomVarValue(80),		DataGenerator.getRandomMeanValue(200),	DataGenerator.getRandomVarValue(80),	DataGenerator.getRandomMeanValue(200), DataGenerator.getRandomVarValue(80)) );
		
		
		data.addAll( DataGenerator.makePoints("Group1", nG1, 150,	23,		12,		3,	28, 12) );
		data.addAll( DataGenerator.makePoints("Group2", nG2, 250,	18,		153,	25,	94, 27) );
		data.addAll( DataGenerator.makePoints("Group3", nG3, 85,	13,		220,	17,	54, 9) );
		data.addAll( DataGenerator.makePoints("Group4", nG4, 100,	80,		112,	80,	100, 80) );
		
		return data;
	}
	
	private static double getRandomMeanValue( int size ) {
		return Math.random() * size;
	}
	
	private static double getRandomVarValue( int size ) {
		return Math.random() * size;
	}
	
	private static List<Point> makePoints( String groupName, int size, double xMean, double xVariance, double yMean, double yVariance, double zMean, double zVariance ) {
		List<Point> points = new ArrayList<Point>();

		long a = System.currentTimeMillis();
		Random rand = new Random();
		for(int i=0; i<size; i++) {
			double x = rand.nextGaussian() * xVariance + xMean;
			double y = rand.nextGaussian() * yVariance + yMean;
			double z = rand.nextGaussian() * zVariance + zMean;
			
			Point point = new Point( groupName, x, y, z);
			
			points.add( point );
		}
		long b = System.currentTimeMillis();

		String time = ((float)(b-a) / 1000) + "sec";
		System.out.println( groupName + " data is generated : " + points.size() + "("+ time +")");
		
		return points;
	}
	
	public static Boundary getBoundary( List<Point> data ) {
		double minX = Double.MAX_VALUE;
		double maxX = Double.MIN_VALUE;
		double minY = Double.MAX_VALUE;
		double maxY = Double.MIN_VALUE;
		double minZ = Double.MAX_VALUE;
		double maxZ = Double.MIN_VALUE;
		
		for( Point point : data ) {
			if( point.getX() < minX ) minX = point.getX();
			if( point.getX() > maxX ) maxX = point.getX();
			
			if( point.getY() < minY ) minY = point.getY();
			if( point.getY() > maxY ) maxY = point.getY();
			
			if( point.getZ() < minZ ) minZ = point.getZ();
			if( point.getZ() > maxZ ) maxZ = point.getZ();
		}
		
		return new Boundary( minX, maxX, minY, maxY, minZ, maxZ );
	}
	
	public static List<Point> compactDataToPlot( List<Point> data, double xAxisLength, double yAxisLength, double zAxisLength ) {
		Boundary bound = DataGenerator.getBoundary(data);

		Map<String, Point> mp = new LinkedHashMap<String, Point>();
		for(Point point : data) {
			int relativeX = (int) Math.floor( ((point.getX() - bound.getMinX()) / (bound.getMaxX() - bound.getMinX())) * xAxisLength );
			int relativeY = (int) Math.floor( ((point.getY() - bound.getMinY()) / (bound.getMaxY() - bound.getMinY())) * yAxisLength );
			int relativeZ = (int) Math.floor( ((point.getZ() - bound.getMinZ()) / (bound.getMaxZ() - bound.getMinZ())) * zAxisLength );
			
			String key = relativeX + "-" + relativeY + "-" + relativeZ;
			
			if( ! mp.containsKey(key) ) mp.put( key, point );
		}
		
		return new ArrayList<Point>( mp.values() );
	}
}
