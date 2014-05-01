package gribs;

import java.util.Date;
import java.util.ArrayList;

public class Structure {
	
	private String filename;
	private boolean isGRIB;
	private int fullLength;
	private int version;
	
	private int part1Length;
	private boolean isInterantional;
	private int whereFrom; //4 - москва
	private int gridType; 	
	private boolean isPart2Included;
	private boolean isPart3Included;	
	private boolean hasWindComponent_U;
	private boolean hasWindComponent_V;	
	private int levelType; //100 изобарическа€ поверхность
	private int level;
	private Date date;	
	private int unitOfTimeIndex;	
	private int p1;
	private int p2;	
	private int timePointer;	
	private int numberOfCases;
	private int numberOfCasesLost;	
	private int century;	
	private int subcenterPointer;	
	private int D; //D
	
	private int part2Length;
	private int verticalCoordParamsCount;
	private byte PV; //if 255 - no one; else this is pointer to the location
	private int dataType; //0 - shirotno-dolgotn grid
	private int parallelPointsCount; //NI
	private int meridianPointsCountl; //Nj
	private int firstPointLatitude;
	private int firstPointLongitude;
	private boolean isIncrementsInTheDirectionsAreGiven;
	private boolean isEarthSpheroid; //else is Sphere;
	private boolean isDesignedWithRespectToTheReferenceGrid; //else —проект компоненты вект велечин u и v относ восточн и северн напр
	private int lastPointLatitude;
	private int lastPointLongitude;
	private int Di; //ѕриращение в направлении i
	private int Dj; //ѕриращение в направлении j	
	private boolean scanToMinusI; // else —канирование в направлении +i
	private boolean scanToPlusJ; // else —канирование в направлении -j
	private boolean neighborPointsInDirectionIareConsecutive; //else —оседние точки в напр j €вл€ютс€ последовательными	
	private int part4Length;
	private boolean isCoeffOfSphericalHarminicFunction; 
	private boolean isDataByPointsOfGrib;
	private boolean isSimplePackage; //else complex package
	private boolean isInt; //else double - ѕредставлены велечины с плавающей зап€той
	private boolean isAdditionalFlagBits; 
	
	private double scallingFactor; //E
	private double scanBegin; //начало отсчета
	private int dataPackageLength; // ол-во бит, содерж каждую пакетную велечин
	private ArrayList<GRIB> data = new ArrayList<GRIB>(); //ƒанные
	
	
	
	
	public Structure(String filename) {
		this.filename = filename;
	}




	public String getFilename() {
		return filename;
	}



	public boolean isGRIB() {
		return isGRIB;
	}




	public void setGRIB(boolean isGRIB) {
		this.isGRIB = isGRIB;
	}




	public int getLength() {
		return fullLength;
	}




	public void setLength(int length) {
		this.fullLength = length;
	}

	public int getFullLength() {
		return fullLength;
	}




	public void setFullLength(int fullLength) {
		this.fullLength = fullLength;
	}




	public int getVersion() {
		return version;
	}




	public void setVersion(int version) {
		this.version = version;
	}




	public int getPart1Length() {
		return part1Length;
	}




	public void setPart1Length(int part1Length) {
		this.part1Length = part1Length;
	}




	public boolean isInterantional() {
		return isInterantional;
	}




	public void setInterantional(boolean isInterantional) {
		this.isInterantional = isInterantional;
	}




	public int getWhereFrom() {
		return whereFrom;
	}




	public void setWhereFrom(int whereFrom) {
		this.whereFrom = whereFrom;
	}




	public int getGridType() {
		return gridType;
	}




	public void setGridType(int gridType) {
		this.gridType = gridType;
	}




	public boolean isPart2Included() {
		return isPart2Included;
	}




	public void setPart2Included(boolean isPart2Included) {
		this.isPart2Included = isPart2Included;
	}




	public boolean isPart3Included() {
		return isPart3Included;
	}




	public void setPart3Included(boolean isPart3Included) {
		this.isPart3Included = isPart3Included;
	}




	public boolean isHasWindComponent_U() {
		return hasWindComponent_U;
	}




	public void setHasWindComponent_U(boolean hasWindComponent_U) {
		this.hasWindComponent_U = hasWindComponent_U;
	}




	public boolean isHasWindComponent_V() {
		return hasWindComponent_V;
	}




	public void setHasWindComponent_V(boolean hasWindComponent_V) {
		this.hasWindComponent_V = hasWindComponent_V;
	}




	public int getLevelType() {
		return levelType;
	}




	public void setLevelType(int levelType) {
		this.levelType = levelType;
	}




	public int getLevel() {
		return level;
	}




	public void setLevel(int Level) {
		this.level = Level;
	}





	public Date getDate() {
		return date;
	}




	public void setDate(Date date) {
		this.date = date;
	}




	public int getUnitOfTimeIndex() {
		return unitOfTimeIndex;
	}




	public void setUnitOfTimeIndex(int unitOfTimeIndex) {
		this.unitOfTimeIndex = unitOfTimeIndex;
	}




	public int getP1() {
		return p1;
	}




	public void setP1(int p1) {
		this.p1 = p1;
	}




	public int getP2() {
		return p2;
	}




	public void setP2(int p2) {
		this.p2 = p2;
	}




	public int getTimePointer() {
		return timePointer;
	}




	public void setTimePointer(int timePointer) {
		this.timePointer = timePointer;
	}




	public int getNumberOfCases() {
		return numberOfCases;
	}




	public void setNumberOfCases(int numberOfCases) {
		this.numberOfCases = numberOfCases;
	}




	public int getNumberOfCasesLost() {
		return numberOfCasesLost;
	}




	public void setNumberOfCasesLost(int numberOfCasesLost) {
		this.numberOfCasesLost = numberOfCasesLost;
	}




	public int getCentury() {
		return century;
	}




	public void setCentury(int century) {
		this.century = century;
	}




	public int getSubcenterPointer() {
		return subcenterPointer;
	}




	public void setSubcenterPointer(int subcenterPointer) {
		this.subcenterPointer = subcenterPointer;
	}




	public int getD() {
		return D;
	}




	public void setD(int d) {
		D = d;
	}




	public int getPart2Length() {
		return part2Length;
	}




	public void setPart2Length(int part2Length) {
		this.part2Length = part2Length;
	}




	public int getVerticalCoordParamsCount() {
		return verticalCoordParamsCount;
	}




	public void setVerticalCoordParamsCount(int verticalCoordParamsCount) {
		this.verticalCoordParamsCount = verticalCoordParamsCount;
	}




	public byte getPV() {
		return PV;
	}




	public void setPV(byte pV) {
		PV = pV;
	}




	public int getDataType() {
		return dataType;
	}




	public void setDataType(int dataType) {
		this.dataType = dataType;
	}




	public int getParallelPointsCount() {
		return parallelPointsCount;
	}




	public void setParallelPointsCount(int parallelPointsCount) {
		this.parallelPointsCount = parallelPointsCount;
	}




	public int getMeridianPointsCountl() {
		return meridianPointsCountl;
	}




	public void setMeridianPointsCountl(int meridianPointsCountl) {
		this.meridianPointsCountl = meridianPointsCountl;
	}




	public int getFirstPointLatitude() {
		return firstPointLatitude;
	}




	public void setFirstPointLatitude(int firstPointLatitude) {
		this.firstPointLatitude = firstPointLatitude;
	}




	public int getFirstPointLongitude() {
		return firstPointLongitude;
	}




	public void setFirstPointLongitude(int firstPointLongitude) {
		this.firstPointLongitude = firstPointLongitude;
	}




	public boolean isIncrementsInTheDirectionsAreGiven() {
		return isIncrementsInTheDirectionsAreGiven;
	}




	public void setIncrementsInTheDirectionsAreGiven(
			boolean isIncrementsInTheDirectionsAreGiven) {
		this.isIncrementsInTheDirectionsAreGiven = isIncrementsInTheDirectionsAreGiven;
	}




	public boolean isEarthSpheroid() {
		return isEarthSpheroid;
	}




	public void setEarthSpheroid(boolean isEarthSpheroid) {
		this.isEarthSpheroid = isEarthSpheroid;
	}




	public boolean isDesignedWithRespectToTheReferenceGrid() {
		return isDesignedWithRespectToTheReferenceGrid;
	}




	public void setDesignedWithRespectToTheReferenceGrid(
			boolean isDesignedWithRespectToTheReferenceGrid) {
		this.isDesignedWithRespectToTheReferenceGrid = isDesignedWithRespectToTheReferenceGrid;
	}




	public int getLastPointLatitude() {
		return lastPointLatitude;
	}




	public void setLastPointLatitude(int lastPointLatitude) {
		this.lastPointLatitude = lastPointLatitude;
	}




	public int getLastPointLongitude() {
		return lastPointLongitude;
	}




	public void setLastPointLongitude(int lastPointLongitude) {
		this.lastPointLongitude = lastPointLongitude;
	}




	public int getDi() {
		return Di;
	}




	public void setDi(int di) {
		Di = di;
	}




	public int getDj() {
		return Dj;
	}




	public void setDj(int dj) {
		Dj = dj;
	}




	public boolean isScanToMinusI() {
		return scanToMinusI;
	}




	public void setScanToMinusI(boolean scanToMinusI) {
		this.scanToMinusI = scanToMinusI;
	}




	public boolean isScanToPlusJ() {
		return scanToPlusJ;
	}




	public void setScanToPlusJ(boolean scanToPlusJ) {
		this.scanToPlusJ = scanToPlusJ;
	}




	public boolean isNeighborPointsInDirectionIareConsecutive() {
		return neighborPointsInDirectionIareConsecutive;
	}




	public void setNeighborPointsInDirectionIareConsecutive(
			boolean neighborPointsInDirectionIareConsecutive) {
		this.neighborPointsInDirectionIareConsecutive = neighborPointsInDirectionIareConsecutive;
	}




	public int getPart4Length() {
		return part4Length;
	}




	public void setPart4Length(int part4Length) {
		this.part4Length = part4Length;
	}




	public boolean isCoeffOfSphericalHarminicFunction() {
		return isCoeffOfSphericalHarminicFunction;
	}




	public void setCoeffOfSphericalHarminicFunction(
			boolean isCoeffOfSphericalHarminicFunction) {
		this.isCoeffOfSphericalHarminicFunction = isCoeffOfSphericalHarminicFunction;
	}




	public boolean isDataByPointsOfGrib() {
		return isDataByPointsOfGrib;
	}




	public void setDataByPointsOfGrib(boolean isDataByPointsOfGrib) {
		this.isDataByPointsOfGrib = isDataByPointsOfGrib;
	}




	public boolean isSimplePackage() {
		return isSimplePackage;
	}




	public void setSimplePackage(boolean isSimplePackage) {
		this.isSimplePackage = isSimplePackage;
	}




	public boolean isInt() {
		return isInt;
	}




	public void setInt(boolean isInt) {
		this.isInt = isInt;
	}




	public boolean isAdditionalFlagBits() {
		return isAdditionalFlagBits;
	}




	public void setAdditionalFlagBits(boolean isAdditionalFlagBits) {
		this.isAdditionalFlagBits = isAdditionalFlagBits;
	}




	public double getScallingFactor() {
		return scallingFactor;
	}




	public void setScallingFactor(double scallingFactor) {
		this.scallingFactor = scallingFactor;
	}




	public double getScanBegin() {
		return scanBegin;
	}




	public void setScanBegin(double r) {
		this.scanBegin = r;
	}




	public int getDataPackageLength() {
		return dataPackageLength;
	}




	public void setDataPackageLength(int dataPackageLength) {
		this.dataPackageLength = dataPackageLength;
	}




	public ArrayList<GRIB> getData() {
		return data;
	}




	public void setData(ArrayList<GRIB> data) {
		this.data = data;
	}




	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
}

