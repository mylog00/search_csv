package csv.search;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.*;

public class SearchCSV {

	private static final char DEFAULT_SEPARATOR = ';';
	private static final char DEFAULT_QUOTE_CHAR = '"';
	private static final String DEFAULT_LINE_END = "\r\n";
	public static final String DEFAULT_CHARSET_NAME = "cp1251";
	private String FILE_ENCODE;
	private String SEARCH_FILE_PATH;
	private String RESULT_FILE_PATH;

	/**
	 *
	 * @param inputFilePath
	 */
	public SearchCSV( String inputFilePath, String outputFilePath, String fileEncode) {
		SEARCH_FILE_PATH = inputFilePath;
		RESULT_FILE_PATH = outputFilePath;
		FILE_ENCODE = fileEncode;
	}

	/**
	 *
	 * @param searchValue
	 * @param columnName
	 * @throws ESearchException
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	public void searchValue(String searchValue ,String columnName) throws ESearchException, IOException,
			FileNotFoundException, UnsupportedEncodingException {

		int columnIndex;
		String[] row;
		//FileReader inputFile = null;
		//FileWriter outputFile = null;
		InputStreamReader inputFile = null;
		OutputStreamWriter outputFile = null;
		CSVReader in = null;
		CSVWriter out = null;



		try {
			inputFile = new InputStreamReader (new FileInputStream(SEARCH_FILE_PATH), FILE_ENCODE);
			in = new CSVReader(inputFile, DEFAULT_SEPARATOR, DEFAULT_QUOTE_CHAR, DEFAULT_QUOTE_CHAR);

			// read columns name and type
			row = in.readNext();
			if( row == null) { throw new ESearchException("Invalid file format"); }

			columnIndex = getColumnIndex(columnName, row);

			outputFile = new OutputStreamWriter( new FileOutputStream(RESULT_FILE_PATH, false), FILE_ENCODE);
			out = new CSVWriter(outputFile, DEFAULT_SEPARATOR, DEFAULT_QUOTE_CHAR, DEFAULT_LINE_END);
			out.writeNext(row);

			while ( (row = in.readNext()) != null ) {
				if ( searchValue.equals( row[columnIndex] ) ) {
					out.writeNext(row);
				}
			}

		}
//		catch (IOException e) {
//			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//
//		}
		finally {
			if( in != null) {
//				try {
					in.close();
//				} catch (IOException e) {
//					e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//				}
			}

			if( inputFile != null) {
//				try {
					inputFile.close();
//				} catch (IOException e) {
//					e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//				}
			}

			if( out != null) { out.close(); }

			if( outputFile != null) { outputFile.close(); }
		}
	}

	/**
	 *
	 * @param searchColumnName
	 * @param colName
	 * @return
	 * @throws ESearchException
	 */
	private int getColumnIndex(String searchColumnName, String[] colName) throws ESearchException {
		for (int index = 0; index < colName.length; index++) {
			String name = colName[index].split(" ")[0];
			if( name.equals(searchColumnName)) {
				return index;
			}
		}
		throw new ESearchException("Column name not found");
	}


}
