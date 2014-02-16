package csv.search;

import org.apache.commons.cli.*;

import java.io.IOException;

/**
 * Программа осуществляет поиск в указанной колонке по заданному значению
 */
public class Run {

	private static final String CMD_IN = "in";
	private static final String CMD_OUT = "out";
	private static final String CMD_ENC = "enc";
	private static final String CMD_COL = "col";
	private static final String CMD_EXP = "exp";
	public static final String DESCRIPTION_INPUT_FILE = "input file name";
	public static final String DESCRIPTION_OUTPUT_FILE = "output file name";
	public static final String DESCRIPTION_FILE_ENCODE = "file encode";
	public static final String DESCRIPTION_COLUMN_NAME = "column name";
	public static final String DESCRIPTION_SEARCH_STRING = "search string";


	/**
	 *
	 * @param options
	 */
	private static void printHelp(Options options) {
		HelpFormatter help = new HelpFormatter();
		help.printHelp("search_csv.jar", options, true);
	}

	/**
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// Create options
		Options options = new Options();
		//Option o = OptionBuilder.withDescription("input file name").hasArg().withArgName("input_file").create(CMD_IN);
		//options.addOption(OptionBuilder.withDescription("input file name").hasArg().withArgName("input_file").create(CMD_IN));
		options.addOption(CMD_IN, true, DESCRIPTION_INPUT_FILE);
		options.addOption(CMD_OUT, true, DESCRIPTION_OUTPUT_FILE);
		options.addOption(CMD_ENC, true, DESCRIPTION_FILE_ENCODE);
		options.addOption(CMD_COL, true, DESCRIPTION_COLUMN_NAME);
		options.addOption(CMD_EXP, true, DESCRIPTION_SEARCH_STRING);

		// parse command line
		CommandLineParser cmdParser = new PosixParser();
		CommandLine cmd = null;
		try {
			cmd = cmdParser.parse(options, args);
		} catch (ParseException e) {
			//e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			Run.printHelp( options );
		}

		if(cmd.hasOption(CMD_IN) && cmd.hasOption(CMD_OUT) && cmd.hasOption(CMD_COL) && cmd.hasOption(CMD_EXP)) {
			String inputFileName = cmd.getOptionValue(CMD_IN);
			String outputFileName = cmd.getOptionValue(CMD_OUT);
			String columnName = cmd.getOptionValue(CMD_COL);
			String searchString = cmd.getOptionValue(CMD_EXP);
			String fileEncode = cmd.getOptionValue(CMD_ENC);

			SearchCSV search = new SearchCSV(inputFileName, outputFileName, fileEncode);
			try {
				search.searchValue(searchString, columnName);
			} catch (ESearchException e) {
				e.printLocalizedMessage();
				//e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			} catch (IOException e) {
				System.err.println(e.getLocalizedMessage());
				//e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			}
			System.out.println(inputFileName);
			System.out.println(outputFileName);

		} else { Run.printHelp(options); }
	}
}
