package gribs;


public class MapKey {
	
	  private long coordX;
      private long coordY;
      private int height;
      private int forecast;
      
	public MapKey(long coordX, long coordY, int height, int forecast) {
		this.coordX = coordX;
		this.coordY = coordY;
		this.height = height;
		this.forecast = forecast;
	}

	public long getCoordX() {
		return coordX;
	}

	public void setCoordX(long coordX) {
		this.coordX = coordX;
	}

	public long getCoordY() {
		return coordY;
	}

	public void setCoordY(long coordY) {
		this.coordY = coordY;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getForecast() {
		return forecast;
	}

	public void setForecast(int forecast) {
		this.forecast = forecast;
	}
	
	

}
