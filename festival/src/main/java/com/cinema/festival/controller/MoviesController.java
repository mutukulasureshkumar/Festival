package com.cinema.festival.controller;

import com.cinema.festival.model.Movies;
import com.cinema.festival.service.MoviesService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping( value = "movies")
public class MoviesController {

	@Autowired
	private MoviesService moviesService;
	
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ArrayList<Movies> getAllMovies(){
		return moviesService.getAllMovies();
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<Movies> getAllMoviesFromTodaay(){
		return moviesService.getAllMovies();
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Movies add(@RequestBody Movies movies){
		return moviesService.save(movies);
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public void delete(@RequestBody Movies movies){
		moviesService.delete(movies);
	}

	@RequestMapping(value = "/load", method = RequestMethod.GET)
	public String load() {
		BufferedReader br = null;
		FileReader fr = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
			ClassLoader classLoader = this.getClass().getClassLoader();
			File file = new File(classLoader.getResource("ipl.xlsx").getFile());
			FileInputStream excelFile = new FileInputStream(file);
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();
				Movies movie = new Movies();
				while (cellIterator.hasNext()) {
					Cell currentCell = cellIterator.next();
					if (currentCell.getCellType() == currentCell.CELL_TYPE_STRING) {
						if(currentCell.getColumnIndex() == 1){
							String[] actres = currentCell.getStringCellValue().toUpperCase().split("VS");
							if(actres.length == 2){
								movie.setHero(actres[0]);
								movie.setHeroin(actres[1]);
							}
						}
						if(currentCell.getColumnIndex() == 2){
							String[] dateMonthAndTime=null;
							if(currentCell.getStringCellValue().contains("th")) {
								dateMonthAndTime = currentCell.getStringCellValue().toUpperCase().split("TH");
							}
							if(currentCell.getStringCellValue().contains("rd")) {
								dateMonthAndTime = currentCell.getStringCellValue().toUpperCase().split("RD");
							}
							if(currentCell.getStringCellValue().contains("st")) {
								dateMonthAndTime = currentCell.getStringCellValue().toUpperCase().split("ST");
							}
							if(currentCell.getStringCellValue().contains("nd")) {
								dateMonthAndTime = currentCell.getStringCellValue().toUpperCase().split("ND");
							}
							int date = Integer.parseInt(dateMonthAndTime[0]);
							int year = 2019;
							int month=0;
							if(currentCell.getStringCellValue().toUpperCase().contains("MARCH"))
								month=2;
							if(currentCell.getStringCellValue().toUpperCase().contains("APRIL"))
								month=3;
							if(currentCell.getStringCellValue().toUpperCase().contains("MAY"))
								month=4;
							Calendar calendar = new GregorianCalendar(2019,month,date);
							movie.setDate(sdf.format(calendar.getTime()));
						}
						if(currentCell.getColumnIndex() == 3){
							if(currentCell.getStringCellValue().contains("8"))
								movie.setTime("20:00");
							else
								movie.setTime("16:00");
						}
						if(currentCell.getColumnIndex() == 4){
							movie.setTheater(currentCell.getStringCellValue());
						}
					}
				}
				moviesService.save(movie);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return "Data Loaded in Database !!";
	}
}
