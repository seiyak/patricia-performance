package org.patricia.performance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.util.StopWatch;

import patricia.trie.PatriciaTrie;

public class PatriciaPerformance2 {

	private final PatriciaTrie patricia;
	private final List<String> words;
	private static final String TEST_INPUT_FILE_NAME = "/home/seiyak/Documents/EnglishWords.txt";

	public PatriciaPerformance2() {

		patricia = new PatriciaTrie();
		words = new LinkedList<String>();
		initializeWords();
		initializePatricia();
		checkPerformance();
	}

	private void initializeWords() {

		BufferedReader br = null;
		try {
			br = new BufferedReader( new FileReader( new File( TEST_INPUT_FILE_NAME ) ) );
			String str = "";
			try {
				int total = 0;
				while ( ( str = br.readLine() ) != null ) {
					words.add( str );
					total++;
				}

				System.out.println( "total number for English words: " + total );
			}
			catch ( IOException e ) {
				e.printStackTrace();
			}
		}
		catch ( FileNotFoundException e ) {
			e.printStackTrace();
		}
		finally {
			try {
				if ( br != null ) {
					br.close();
				}
			}
			catch ( IOException e ) {
				e.printStackTrace();
			}
		}
	}

	private void initializePatricia() {

		for ( String word : words ) {
			patricia.insert( word );
		}
	}

	private void checkPerformance() {

		System.out.println( "about to check the performance ..." );
		doPerformance( "b" );
		doPerformance( "c" );
		doPerformance( "w" );
		doPerformance( "y" );
		doPerformance( "zoom" );
	}

	private void doPerformance(String prefix) {
		StopWatch stopWatch = new StopWatch();
		List<String> results = null;

		stopWatch.start( "patricia with prefix,'" + prefix + "'" );
		results = patricia.searchPrefix( prefix );
		stopWatch.stop();

		results.clear();

		stopWatch.start( "easy way with prefix,'" + prefix + "'" );
		for ( int i = 0; i < words.size(); i++ ) {
			if ( words.get( i ).startsWith( prefix ) ) {
				results.add( words.get( i ) );
			}
		}
		stopWatch.stop();
		System.out.println( stopWatch.prettyPrint() );
	}

	public static void main(String[] args) {

		PatriciaPerformance2 patriciaPerformance2 = new PatriciaPerformance2();
	}
}
