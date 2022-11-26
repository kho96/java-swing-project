package ver01;

import java.util.Date;

public class PaintVo {
	private String u_id, userShape, userColor;
	private int startX, startY, movedX, movedY;
	private Date paint_date;
	
	public PaintVo(String u_id, String userShape, String userColor, int startX, int startY, int movedX, int movedY) {
		super();
		this.u_id = u_id;
		this.userShape = userShape;
		this.userColor = userColor;
		this.startX = startX;
		this.startY = startY;
		this.movedX = movedX;
		this.movedY = movedY;
	}

	public Date getPaint_date() {
		return paint_date;
	}

	public void setPaint_date(Date paint_date) {
		this.paint_date = paint_date;
	}

	public String getU_id() {
		return u_id;
	}

	public void setU_id(String u_id) {
		this.u_id = u_id;
	}

	public String getUserShape() {
		return userShape;
	}

	public void setUserShape(String userShape) {
		this.userShape = userShape;
	}

	public String getUserColor() {
		return userColor;
	}

	public void setUserColor(String userColor) {
		this.userColor = userColor;
	}

	public int getStartX() {
		return startX;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public int getStartY() {
		return startY;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public int getMovedX() {
		return movedX;
	}

	public void setMovedX(int movedX) {
		this.movedX = movedX;
	}

	public int getMovedY() {
		return movedY;
	}

	public void setMovedY(int movedY) {
		this.movedY = movedY;
	}

	@Override
	public String toString() {
		return "PaintVo [u_id=" + u_id + ", userShape=" + userShape + ", userColor=" + userColor + ", startX=" + startX
				+ ", startY=" + startY + ", movedX=" + movedX + ", movedY=" + movedY + "]";
	}
	
	
}
