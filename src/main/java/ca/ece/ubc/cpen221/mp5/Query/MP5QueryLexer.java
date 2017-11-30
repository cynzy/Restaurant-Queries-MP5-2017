// Generated from MP5QueryLexer.g4 by ANTLR 4.7
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
		AND=1, OR=2, LPAREN=3, GT=4, GTE=5, LT=6, LTE=7, EQ=8, NUM=9, IN=10, CATEGORY=11, 
		NAME=12, RATING=13, PRICE=14, WS=15, STR=16, RPAREN=17;
	public static final int
		WORD=1;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE", "WORD"
	};

	public static final String[] ruleNames = {
		"AND", "OR", "LPAREN", "GT", "GTE", "LT", "LTE", "EQ", "NUM", "IN", "CATEGORY", 
		"NAME", "RATING", "PRICE", "WS", "STR", "RPAREN"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'&&'", "'||'", "'('", "'>'", "'>='", "'<'", "'<='", "'='", null, 
		"'in'", "'category'", "'name'", "'rating'", "'price'", null, null, "')'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "AND", "OR", "LPAREN", "GT", "GTE", "LT", "LTE", "EQ", "NUM", "IN", 
		"CATEGORY", "NAME", "RATING", "PRICE", "WS", "STR", "RPAREN"
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
	public String getGrammarFileName() { return "MP5QueryLexer.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\23l\b\1\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\3\2\3\2\3\2\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\5\3\5\3\6\3\6\3\6\3\7\3\7\3"+
		"\b\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f"+
		"\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3"+
		"\17\3\17\3\17\3\17\3\17\3\20\6\20^\n\20\r\20\16\20_\3\20\3\20\3\21\6\21"+
		"e\n\21\r\21\16\21f\3\22\3\22\3\22\3\22\2\2\23\4\3\6\4\b\5\n\6\f\7\16\b"+
		"\20\t\22\n\24\13\26\f\30\r\32\16\34\17\36\20 \21\"\22$\23\4\2\3\5\3\2"+
		"\63\67\5\2\"\"\61\61vv\5\2\"\"C\\c|\2l\2\4\3\2\2\2\2\6\3\2\2\2\2\b\3\2"+
		"\2\2\2\n\3\2\2\2\2\f\3\2\2\2\2\16\3\2\2\2\2\20\3\2\2\2\2\22\3\2\2\2\2"+
		"\24\3\2\2\2\2\26\3\2\2\2\2\30\3\2\2\2\2\32\3\2\2\2\2\34\3\2\2\2\2\36\3"+
		"\2\2\2\2 \3\2\2\2\3\"\3\2\2\2\3$\3\2\2\2\4&\3\2\2\2\6)\3\2\2\2\b,\3\2"+
		"\2\2\n\60\3\2\2\2\f\62\3\2\2\2\16\65\3\2\2\2\20\67\3\2\2\2\22:\3\2\2\2"+
		"\24<\3\2\2\2\26>\3\2\2\2\30A\3\2\2\2\32J\3\2\2\2\34O\3\2\2\2\36V\3\2\2"+
		"\2 ]\3\2\2\2\"d\3\2\2\2$h\3\2\2\2&\'\7(\2\2\'(\7(\2\2(\5\3\2\2\2)*\7~"+
		"\2\2*+\7~\2\2+\7\3\2\2\2,-\7*\2\2-.\3\2\2\2./\b\4\2\2/\t\3\2\2\2\60\61"+
		"\7@\2\2\61\13\3\2\2\2\62\63\7@\2\2\63\64\7?\2\2\64\r\3\2\2\2\65\66\7>"+
		"\2\2\66\17\3\2\2\2\678\7>\2\289\7?\2\29\21\3\2\2\2:;\7?\2\2;\23\3\2\2"+
		"\2<=\t\2\2\2=\25\3\2\2\2>?\7k\2\2?@\7p\2\2@\27\3\2\2\2AB\7e\2\2BC\7c\2"+
		"\2CD\7v\2\2DE\7g\2\2EF\7i\2\2FG\7q\2\2GH\7t\2\2HI\7{\2\2I\31\3\2\2\2J"+
		"K\7p\2\2KL\7c\2\2LM\7o\2\2MN\7g\2\2N\33\3\2\2\2OP\7t\2\2PQ\7c\2\2QR\7"+
		"v\2\2RS\7k\2\2ST\7p\2\2TU\7i\2\2U\35\3\2\2\2VW\7r\2\2WX\7t\2\2XY\7k\2"+
		"\2YZ\7e\2\2Z[\7g\2\2[\37\3\2\2\2\\^\t\3\2\2]\\\3\2\2\2^_\3\2\2\2_]\3\2"+
		"\2\2_`\3\2\2\2`a\3\2\2\2ab\b\20\3\2b!\3\2\2\2ce\t\4\2\2dc\3\2\2\2ef\3"+
		"\2\2\2fd\3\2\2\2fg\3\2\2\2g#\3\2\2\2hi\7+\2\2ij\3\2\2\2jk\b\22\4\2k%\3"+
		"\2\2\2\6\2\3_f\5\4\3\2\b\2\2\4\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}