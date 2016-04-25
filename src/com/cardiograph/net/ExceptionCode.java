package com.cardiograph.net;

public final class ExceptionCode {
	
	/**
	 * �������������ݴ���
	 */
	public static final int ServerResultDataError = 0x00001000;
	
	/**
	 * Http���� ,�ô������ BaseException ��statusCode ����http�����롣
	 */
	public static final int ServerHttpError = 0x00001001;

	/**
	 * �û���û�е�¼
	 */
	public static final int HaveNotLoggedIn = 0x00001002;
}
