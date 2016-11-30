/**
 * 
 */
package phonehome.leynew.com.phenehome.plan;

import java.io.Serializable;

public class PlanDeviceStore implements Serializable {
	
	private static final long serialVersionUID = 7243711873929880921L;
	private int _id;
	private int p_id;//
	private int d_id;//
	private boolean pds_status;//
	private int pds_brightness;//
	private int pds_colour;//
	private int pds_brightness2;//
	private int pds_colour_cool;//
	private int pds_colour_warm;
	private int pds_mode;//
	private int pds_speed;//
	private int pds_red = 254;  //R
	private int pds_green = 254; //G
	private int pds_blue = 254; //B
	private int pds_custom; //

	public int get_id(){
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	
	public int getP_id() {
		return p_id;
	}
	public void setP_id(int p_id) {
		this.p_id = p_id;
	}
	public int getD_id() {
		return d_id;
	}
	public void setD_id(int d_id) {
		this.d_id = d_id;
	}
	public boolean isPds_status() {
		return pds_status;
	}
	public void setPds_status(boolean pds_status) {
		this.pds_status = pds_status;
	}
	public int getPds_brightness() {
		return pds_brightness;
	}
	public void setPds_brightness(int pds_brightness) {
		this.pds_brightness = pds_brightness;
	}
	public int getPds_colour() {
		return pds_colour;
	}
	public void setPds_colour(int pds_colour) {
		this.pds_colour = pds_colour;
	}
	public int getPds_mode() {
		return pds_mode;
	}
	public void setPds_mode(int pds_mode) {
		this.pds_mode = pds_mode;
	}
	public int getPds_speed(){
		return pds_speed;
	}
	public void setPds_speed(int pds_speed) {
		this.pds_speed = pds_speed;
	}
	public int getPds_red() {
		return pds_red;
	}
	public void setPds_red(int pds_red) {
		this.pds_red = pds_red;
	}
	public int getPds_green() {
		return pds_green;
	}
	public void setPds_green(int pds_green) {
		this.pds_green = pds_green;
	}
	public int getPds_blue() {
		return pds_blue;
	}
	public void setPds_blue(int pds_blue) {
		this.pds_blue = pds_blue;
	}
	public int getPds_colour_cool() {
		return pds_colour_cool;
	}
	public void setPds_colour_cool(int pds_colour_cool) {
		this.pds_colour_cool = pds_colour_cool;
	}
	public int getPds_colour_warm() {
		return pds_colour_warm;
	}
	public void setPds_colour_warm(int pds_colour_warm) {
		this.pds_colour_warm = pds_colour_warm;
	}
	public int getPds_brightness2() {
		return pds_brightness2;
	}
	public void setPds_brightness2(int pds_brightness2) {
		this.pds_brightness2 = pds_brightness2;
	}
	public int getPds_custom() {
		return pds_custom;
	}
	public void setPds_custom(int pds_custom) {
		this.pds_custom = pds_custom;
	}
}
