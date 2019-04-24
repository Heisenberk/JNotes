package fr.uvsq.jnotes.index;

import  org.apache.lucene.queryParser.ParseException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.util.Version;

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
		System.out.println("get range query");
		TermRangeQuery query = (TermRangeQuery)
				super.getRangeQuery(field, part1, part2, inclusive);
		if("date".equals(field)) {

			System.out.println("date is field");

			System.out.println(query.getLowerTerm());

			System.out.println(query.getUpperTerm());
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
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
