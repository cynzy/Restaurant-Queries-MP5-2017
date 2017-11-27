// Generated from MP5Query.g4 by ANTLR 4.7
package ca.ece.ubc.cpen221.mp5.Query;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MP5QueryLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		AND=1, OR=2, LPAREN=3, RPAREN=4, GT=5, GTE=6, LT=7, LTE=8, EQ=9, NUM=10, 
		IN=11, CATEGORY=12, NAME=13, RATING=14, PRICE=15, STR=16;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"AND", "OR", "LPAREN", "RPAREN", "GT", "GTE", "LT", "LTE", "EQ", "NUM", 
		"IN", "CATEGORY", "NAME", "RATING", "PRICE", "STR"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'&&'", "'||'", "'('", "')'", "'>'", "'>='", "'<'", "'<='", "'='", 
		null, "'in'", "'category'", "'name'", "'rating'", "'price'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "AND", "OR", "LPAREN", "RPAREN", "GT", "GTE", "LT", "LTE", "EQ", 
		"NUM", "IN", "CATEGORY", "NAME", "RATING", "PRICE", "STR"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public MP5QueryLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "MP5Query.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\22^\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\3\2\3\2\3"+
		"\2\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\t\3\t\3\t"+
		"\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16"+
		"\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\21\6\21[\n\21\r\21\16\21\\\2\2\22\3\3\5\4\7\5\t\6\13"+
		"\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22\3\2\4\3\2"+
		"\63\67\4\2C\\c|\2^\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13"+
		"\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2"+
		"\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2"+
		"!\3\2\2\2\3#\3\2\2\2\5&\3\2\2\2\7)\3\2\2\2\t+\3\2\2\2\13-\3\2\2\2\r/\3"+
		"\2\2\2\17\62\3\2\2\2\21\64\3\2\2\2\23\67\3\2\2\2\259\3\2\2\2\27;\3\2\2"+
		"\2\31>\3\2\2\2\33G\3\2\2\2\35L\3\2\2\2\37S\3\2\2\2!Z\3\2\2\2#$\7(\2\2"+
		"$%\7(\2\2%\4\3\2\2\2&\'\7~\2\2\'(\7~\2\2(\6\3\2\2\2)*\7*\2\2*\b\3\2\2"+
		"\2+,\7+\2\2,\n\3\2\2\2-.\7@\2\2.\f\3\2\2\2/\60\7@\2\2\60\61\7?\2\2\61"+
		"\16\3\2\2\2\62\63\7>\2\2\63\20\3\2\2\2\64\65\7>\2\2\65\66\7?\2\2\66\22"+
		"\3\2\2\2\678\7?\2\28\24\3\2\2\29:\t\2\2\2:\26\3\2\2\2;<\7k\2\2<=\7p\2"+
		"\2=\30\3\2\2\2>?\7e\2\2?@\7c\2\2@A\7v\2\2AB\7g\2\2BC\7i\2\2CD\7q\2\2D"+
		"E\7t\2\2EF\7{\2\2F\32\3\2\2\2GH\7p\2\2HI\7c\2\2IJ\7o\2\2JK\7g\2\2K\34"+
		"\3\2\2\2LM\7t\2\2MN\7c\2\2NO\7v\2\2OP\7k\2\2PQ\7p\2\2QR\7i\2\2R\36\3\2"+
		"\2\2ST\7r\2\2TU\7t\2\2UV\7k\2\2VW\7e\2\2WX\7g\2\2X \3\2\2\2Y[\t\3\2\2"+
		"ZY\3\2\2\2[\\\3\2\2\2\\Z\3\2\2\2\\]\3\2\2\2]\"\3\2\2\2\4\2\\\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}