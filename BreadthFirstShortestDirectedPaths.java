package d6;

import java.util.ArrayList;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

/*************************************************************************
 *  Compilation:  javac BreadthFirstDirectedPaths.java
 *  Execution:    java BreadthFirstDirectedPaths V E
 *  Dependencies: Digraph.java Queue.java Stack.java
 *
 *  Run breadth first search on a digraph.
 *  Runs in O(E + V) time.
 *
 *  % java BreadthFirstDirectedPaths tinyDG.txt 3
 *  3 to 0 (2):  3->2->0
 *  3 to 1 (3):  3->2->0->1
 *  3 to 2 (1):  3->2
 *  3 to 3 (0):  3
 *  3 to 4 (2):  3->5->4
 *  3 to 5 (1):  3->5
 *  3 to 6 (-):  not connected
 *  3 to 7 (-):  not connected
 *  3 to 8 (-):  not connected
 *  3 to 9 (-):  not connected
 *  3 to 10 (-):  not connected
 *  3 to 11 (-):  not connected
 *  3 to 12 (-):  not connected
 *
 *************************************************************************/

/**
 *  The <tt>BreadthDirectedFirstPaths</tt> class represents a data type for finding
 *  shortest paths (number of edges) from a source vertex <em>s</em>
 *  (or set of source vertices) to every other vertex in the digraph.
 *  <p>
 *  This implementation uses breadth-first search.
 *  The constructor takes time proportional to <em>V</em> + <em>E</em>,
 *  where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 *  It uses extra space (not including the digraph) proportional to <em>V</em>.
 *  <p>
 *  For additional documentation, see <a href="/algs4/41graph">Section 4.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class BreadthFirstShortestDirectedPaths {
    private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked;  // marked[v] = is there an s->v path?
    private int[] edgeTo;      // edgeTo[v] = last edge on shortest s->v path
    private int[] distTo;      // distTo[v] = length of shortest s->v path

    /**
     * Computes the shortest path from <tt>s</tt> and every other vertex in graph <tt>G</tt>.
     * @param G the digraph
     * @param s the source vertex
     */
    public BreadthFirstShortestDirectedPaths(Digraph G, int s) {
        marked = new boolean[G.V()];
        distTo = new int[G.V()];
        edgeTo = new int[G.V()];
        for (int v = 0; v < G.V(); v++) distTo[v] = INFINITY;
        bfs(G, s);
    }

    /**
     * Computes the shortest path from any one of the source vertices in <tt>sources</tt>
     * to every other vertex in graph <tt>G</tt>.
     * @param G the digraph
     * @param sources the source vertices
     */
    public BreadthFirstShortestDirectedPaths(Digraph G, Iterable<Integer> sources) {
        marked = new boolean[G.V()];
        distTo = new int[G.V()];
        edgeTo = new int[G.V()];
        for (int v = 0; v < G.V(); v++) distTo[v] = INFINITY;
        bfs(G, sources);
    }

    // BFS from single source
    private void bfs(Digraph G, int s) {
        Queue<Integer> q = new Queue<Integer>();
        marked[s] = true;
        distTo[s] = 0;
        q.enqueue(s);
        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    q.enqueue(w);
                }
            }
        }
    }

    // BFS from multiple sources
    private void bfs(Digraph G, Iterable<Integer> sources) {
        Queue<Integer> q = new Queue<Integer>();
        for (int s : sources) {
            marked[s] = true;
            distTo[s] = 0;
            q.enqueue(s);
        }
        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    q.enqueue(w);
                }
            }
        }
    }

    /**
     * Is there a directed path from the source <tt>s</tt> (or sources) to vertex <tt>v</tt>?
     * @param v the vertex
     * @return <tt>true</tt> if there is a directed path, <tt>false</tt> otherwise
     */
    public boolean hasPathTo(int v) {
        return marked[v];
    }

    /**
     * Returns the number of edges in a shortest path from the source <tt>s</tt>
     * (or sources) to vertex <tt>v</tt>?
     * @param v the vertex
     * @return the number of edges in a shortest path
     */
    public int distTo(int v) {
        return distTo[v];
    }

    /**
     * Returns a shortest path from <tt>s</tt> (or sources) to <tt>v</tt>, or
     * <tt>null</tt> if no such path.
     * @param v the vertex
     * @return the sequence of vertices on a shortest path, as an Iterable
     */
    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<Integer>();
        int x;
        for (x = v; distTo[x] != 0; x = edgeTo[x])
            path.push(x);
        path.push(x);
        return path;
    }
    
    //Function that will return count of shortest paths from s to end
    public int countShortest(int s, int end, Digraph G)
    {
    	
    	int shortest = distTo(end);
    	//call helper function recursively
    	return countShortest(s, end, G, shortest - 1);
    }
    
    //Helper function for countShortest
    private int countShortest(int v, int end, Digraph G, int shortest)
    {
    	//If function call reaches end point then return 1
    	if(v == end)
		{
			return 1;
		}
    	int count = 0;
    	//For every adjacent to v
    	for(int w : G.adj(v))
    	{
    		//Create bfs for w in digraph G
    		BreadthFirstShortestDirectedPaths bfs = new BreadthFirstShortestDirectedPaths(G, w);
    		//If adjacent vertex has no path to end point then check next adjacent vertex
    		if(!bfs.hasPathTo(end)) continue;
    		
    		//check if distance from adjacent vertex to end point is the same as the shortest path 
    		//from s to end - number of function call
    		if(bfs.distTo(end) == shortest)
    		{
    			//Get number of shortest paths from adjacents
    			count+= countShortest(w, end, G, shortest - 1);
    		}
    		
    	}
    	
    	//Return count of shortest paths
    	return count;
    }

    /**
     * Unit tests the <tt>BreadthFirstDirectedPaths</tt> data type.
     */
    public static void main(String[] args) {
        int V = StdIn.readInt();
        int E = StdIn.readInt();
    	
        Digraph G = new Digraph(V);
        
        for(int i = 0; i < E; i++)
        {
        	G.addEdge(StdIn.readInt(), StdIn.readInt());
        }
        //Counter for number of shortest paths
        int count = 0;
    	//Source vertex
        int s = 0;
    	//end vertex
        int end = 0;
    	//Number of checks for number of shortest paths
    	int N = StdIn.readInt();
    	
    	for(int i = 0; i < N; i++)
    	{
    		s = StdIn.readInt();
        	end = StdIn.readInt();
        	
        	BreadthFirstShortestDirectedPaths bfs = new BreadthFirstShortestDirectedPaths(G, s);
        	
        	
        	count = bfs.countShortest(s, end, G);
        	
            
            StdOut.println(count);
    	}
        
    	
    	
    	
        
        //int numbChecks = StdIn.readInt();
        
      
       
    }


}
