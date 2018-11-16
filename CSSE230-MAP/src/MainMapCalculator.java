import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MainMapCalculator {
	private static final double EARTH_RADIUS = 6378.137;
	private static File EXCELFILE = new File("CityData/CItyData.xlsx");
	protected static HashMap<String, Object> CITYDATA;
	protected ArrayList<String> CityList;
	protected ArrayList<String> CategoryList;
	private AVLTree aContainer = new AVLTree();

	private int i = 0;

	public MainMapCalculator() throws IOException {
		this.CITYDATA = new HashMap();
		this.CityList = new ArrayList<>();
		this.CategoryList = new ArrayList<>();
		this.extractData();
	}

	public ArrayList<String> getCityList() {
		return this.CityList;
	}

	public ArrayList<String> getCategoryList() {
		return this.CategoryList;
	}

	public HashMap getCityData() {
		return this.CITYDATA;//
	}

	public ArrayList<String> getCategoryNCity(String category) {
		ArrayList<String> temp = new ArrayList<>();
		for (String key : this.CITYDATA.keySet()) {
			if (((HashMap) this.CITYDATA.get(key)).containsValue(category)) {
				temp.add(key);
			}
		}

		return temp;
	}

	private void extractData() throws IOException {
		FileInputStream fis = new FileInputStream(EXCELFILE);

		XSSFWorkbook workbook = new XSSFWorkbook(fis);

		XSSFSheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIt = sheet.iterator();
		int o = 0;
		ArrayList<String> infoName = new ArrayList<String>();
		boolean isTitle = true;
		while (rowIt.hasNext()) {

			Row row = rowIt.next();

			if (isTitle) {
				Iterator<Cell> cellIterato = row.cellIterator();
				isTitle = false;
				while (cellIterato.hasNext()) {
					Cell cell = cellIterato.next();
					String name = cell.toString();
					infoName.add(name);
				}
			} else {
				String locationName = row.getCell(0).toString();
				if (!CITYDATA.containsKey(locationName)) {
					HashMap infoValue = new HashMap();
					HashMap neighInfoValue = new HashMap();
					ArrayList<Double> cost = new ArrayList();
					for (int j = 1; j < 6; j++) {
						if (j < 3) {
							infoValue.put(infoName.get(j), row.getCell(j).toString());

						} else if (2 < j && j < 5) {
							double coordinate = Double.parseDouble(row.getCell(j).toString());
							infoValue.put(infoName.get(j), coordinate);
						} else if (j == 5) {
							double d = Double.parseDouble(row.getCell(6).toString());
							double t = Double.parseDouble(row.getCell(7).toString());
							cost.add(d);
							cost.add(t);
							neighInfoValue.put(row.getCell(5).toString(), cost);
							infoValue.put(infoName.get(5), neighInfoValue);
						}
					}
					this.CITYDATA.put(row.getCell(0).toString(), infoValue);
					if (!this.CityList.contains(infoValue.get("city"))) {
						this.CityList.add((String) infoValue.get("city"));
					}
					if (!this.CategoryList.contains(infoValue.get("category"))) {
						this.CategoryList.add((String) infoValue.get("category"));
					}
				} else {
					HashMap temp = (HashMap) ((HashMap) CITYDATA.get(locationName)).get("neighbour");
					double d = Double.parseDouble(row.getCell(6).toString());
					double t = Double.parseDouble(row.getCell(7).toString());
					ArrayList<Double> cost = new ArrayList();
					cost.add(d);
					cost.add(t);
					temp.put(row.getCell(5).toString(), cost);
				}
			}
		}
		workbook.close();
		fis.close();

	}

	public HashMap<String, ArrayList<Double>> getNeighbour(String name) {
		HashMap<String, ArrayList<Double>> temp = new HashMap<String, ArrayList<Double>>();
		temp = (HashMap) ((HashMap) this.CITYDATA.get(name)).get("neighbour");

		return temp;

	}

	public double calStraightLineDis(String from, String to) {

		double n1 = (double) ((HashMap<String, Object>) this.CITYDATA.get(from)).get("coordinateN");
		double e1 = (double) ((HashMap<String, Object>) this.CITYDATA.get(from)).get("coordinateE");
		double n2 = (double) ((HashMap<String, Object>) this.CITYDATA.get(to)).get("coordinateN");
		double e2 = (double) ((HashMap<String, Object>) this.CITYDATA.get(to)).get("coordinateE");
		double radLat1 = rad(n1);
		double radLat2 = rad(n2);
		double a = radLat1 - radLat2;
		double b = rad(e1) - rad(e2);
		double s = 2 * Math.asin(Math.sqrt(
				Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		return s;

	}

	private double rad(double d) {

		return d * Math.PI / 180;
	}

	public ArrayList<Object> findShortestPath(String fr, String t) {
		String from = fr;
		String to = t;
		double dis = 0;
		double time = 0;
		AVLTree.BinaryNode minInfo = null;
		if (from.equals(to)) {
			return null;
		}
		if (this.aContainer.root != null) {
			minInfo = this.aContainer.getMin();
			from = minInfo.city;
			dis = (double) minInfo.getElement() - this.calStraightLineDis(from, t);
			time = (double) minInfo.time;
			this.aContainer.remove(minInfo.getElement());
		}

		HashMap<String, ArrayList<Double>> costToNeighbour = (HashMap<String, ArrayList<Double>>) this
				.getNeighbour(from);
		for (String k : costToNeighbour.keySet()) {
			ArrayList<Object> path = new ArrayList<Object>();
			if (minInfo != null) {
				path = (ArrayList<Object>) minInfo.path.clone();
			}
			if (path.isEmpty() || !path.get(path.size() - 1).equals(k)) {
				path.add(from);
				double halfWayDis = dis + costToNeighbour.get(k).get(0) + this.calStraightLineDis(k, to);
				double timeCost = costToNeighbour.get(k).get(1) + time;
				this.aContainer.insert(halfWayDis, k, path, timeCost);
				if (k.compareTo(to) == 0) {
					this.aContainer.root = null;
					path.add(k);
					path.add(halfWayDis);
					path.add(timeCost);
					return path;
				}
			}
		}
		return findShortestPath(from, t);

	}

	public ArrayList<Object> suggestionDistance(String place, double dis) {
		ArrayList placeList = new ArrayList<String>();
		for (String key : this.CITYDATA.keySet()) {
			if (calStraightLineDis(place, key) < dis && !place.equals(key)) {
				ArrayList l = (ArrayList) this.findShortestPath(key, place).clone();
				if ((double) l.get(l.size() - 2) < dis) {
					placeList.add(key);
					placeList.add(l.get(l.size() - 2));
					placeList.add(l.get(l.size() - 1));

				}
			}
		}
		return placeList;
	}

}
