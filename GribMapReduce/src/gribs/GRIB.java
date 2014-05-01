package gribs;

import java.util.Date;

public class GRIB
{
	  //Структура Гриб: Координата Х, Координата Y, Значение в данной точке.
    
        private long coordX;
        private long coordY;
        private double value;
        public Date date;
        public String type;
        public int height;
        public int forecast;
        
		public GRIB(long coordX, long coordY, double value) {
			this.coordX = coordX;
			this.coordY = coordY;
			this.value = value;
		}
		
		public GRIB() {

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

		public double getValue() {
			return value;
		}

		public void setValue(double value) {
			this.value = value;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public String getType() {
			return type;
		}

		public void setType(String string) {
			this.type = string;
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
