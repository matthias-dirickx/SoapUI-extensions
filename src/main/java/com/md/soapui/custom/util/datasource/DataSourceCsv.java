package com.md.soapui.custom.util.datasource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.md.soapui.custom.util.datasource.DataSourceException;

public class DataSourceCsv extends AbstractDataSource {
	
	private String seperator = ";";
	private List<String[]> splitLines;
	private int DEFAULT_LAST_COLUMN_INDEX;
	
	private int lastColumnIndex;
	
	public DataSourceCsv(File file) {
		List<String[]> splitLines = new ArrayList<>();
		String line = "";
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                String[] splittedLine = line.split(seperator);
                splitLines.add(splittedLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		this.splitLines = splitLines;
    }

	@Override
	public void loadDataset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Map<Integer, String> getLineWithoutHeaders(int rowNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Map<String, String> getLineWithHeaders(int rowNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setDatasetCountValues() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AbstractDataSource getTheSheetWithName(String sheetName) throws DataSourceException {
		throw new DataSourceException("This operation is not allowed for this type." );
	}

	@Override
	public AbstractDataSource getTheSheetWithIndex(int sheetIndex) throws DataSourceException {
		throw new DataSourceException("This operation is not allowed for this type." );
	}

	@Override
	public AbstractDataSource startAtRow(int index) {
		this.setStartAtRow(index);
		return this;
	}

	@Override
	public AbstractDataSource stopAtRow(int index) {
		this.setStopAtRow(index);
		return this;
	}

	@Override
	public AbstractDataSource startAtColumn(int index) {
		this.setStartAtColumn(index);
		return this;
	}

	@Override
	public AbstractDataSource stopAtColumn(int index) {
		this.setStopAtColumn(index);
		return this;
	}

	@Override
	public AbstractDataSource useHeadersAtRow(int index) {
		this.setHeaderRowNumber(index);
		return this;
	}

	@Override
	public AbstractDataSource setSeperatorTo(String seperator) throws DataSourceException {
		this.seperator = seperator;
		return null;
	}

}