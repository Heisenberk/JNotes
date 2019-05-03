package fr.uvsq.jnotes.index;

import  org.apache.lucene.queryParser.ParseException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.util.Version;

/**
 * QueryParser qui permet de traiter les dates et les durees
 *
 */
public class NumericDateRangeQueryParser extends QueryParser {
	public NumericDateRangeQueryParser(Version matchVersion,
				String field, Analyzer a) {
		super(matchVersion, field, a);
	}
	
	@Override
	public org.apache.lucene.search.Query getRangeQuery(	
			String field,
			String part1,
			String part2,
			boolean inclusive) throws ParseException{
		TermRangeQuery query = (TermRangeQuery)
				super.getRangeQuery(field, part1, part2, inclusive);
		
		if("date".equals(field)) {
			//System.out.println(query.getLowerTerm());

			//System.out.println(query.getUpperTerm());
			
			return NumericRangeQuery.newIntRange(	
					"date", 
					Integer.parseInt(query.getLowerTerm()),
					Integer.parseInt(query.getUpperTerm()),
					query.includesLower(),
					query.includesUpper());
		} else {
			return query;
		}
	}
}
