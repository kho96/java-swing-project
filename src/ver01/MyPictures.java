package ver01;

import java.awt.Color;

public class MyPictures {
	// 저장 될 좌푯값
	int startX, startY, movedX, movedY;
	
	// 저장 될 모양, 색 정보
	String userShape;
	Color userColor;
	public MyPictures(int startX, int startY, int movedX, int movedY, String userShape, Color userColor) {
		super();
		this.startX = startX;
		this.startY = startY;
		this.movedX = movedX;
		this.movedY = movedY;
		this.userShape = userShape;
		this.userColor = userColor;
	}
	
}
