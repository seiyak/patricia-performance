package org.patricia.performance;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.session.extractor.Session;
import org.session.extractor.SessionExtractor;
import org.springframework.util.StopWatch;

import patricia.trie.PatriciaTrie;

public class PatriciaPerformance {

	private final SessionExtractor sessionExtractor;
	private final List<Session> allSessions;
	private final PatriciaTrie patricia;
	private final List<String> speakerSessionNames;
	private static Logger log = Logger.getLogger( PatriciaPerformance.class );

	public PatriciaPerformance() {
		sessionExtractor = new SessionExtractor();
		allSessions = sessionExtractor.getAllSessions();
		patricia = new PatriciaTrie();
		speakerSessionNames = new ArrayList<String>();

		initializePatricia();
		intiailizeSpeakerSessionNames();

		checkPerformance();
	}

	private void initializePatricia() {

		log.debug( "about to initialize patricia ..." );
		for ( Session session : allSessions ) {
			patricia.insert( session.getSessionName() );
			patricia.insert( session.getSpeaker() );
		}

		log.debug( "done with initializing patricia" );
	}

	private void intiailizeSpeakerSessionNames() {

		log.debug( "about to initialize speaker and session names ..." );
		for ( Session session : allSessions ) {
			speakerSessionNames.add( session.getSessionName() );
			speakerSessionNames.add( session.getSpeaker() );
		}
		log.debug( "done with initializing speaker and session names" );
	}

	private void checkPerformance() {

		log.debug( "about to check the performance ..." );
		doPerformance( "B" );
		doPerformance( "C" );
		doPerformance( "W" );
		doPerformance( "Why" );
		doPerformance( "Beyond the Basics" );
		doPerformance( "Everything you need to know" );
	}

	private void doPerformance(String prefix) {
		StopWatch stopWatch = new StopWatch();
		List<String> results = null;

		stopWatch.start( "patricia with prefix,'" + prefix + "'" );
		results = patricia.searchPrefix( prefix );
		stopWatch.stop();

		results.clear();

		stopWatch.start( "easy way with prefix,'" + prefix + "'" );
		for ( int i = 0; i < speakerSessionNames.size(); i++ ) {
			if ( speakerSessionNames.get( i ).startsWith( prefix ) ) {
				results.add( speakerSessionNames.get( i ) );
			}
		}
		stopWatch.stop();
		System.out.println( stopWatch.prettyPrint() );

	}

	public static void main(String[] args) {

		PatriciaPerformance patriciaPerformance = new PatriciaPerformance();
	}
}
