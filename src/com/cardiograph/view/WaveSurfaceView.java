package com.cardiograph.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

/************************************************************
 *  ����ժҪ	��<p>
 *
 *  ����	��Ҷ����
 *  ����ʱ��	��2014-10-5 ����4:26:14 
 *  ��ǰ�汾�ţ�v1.0
 *  ��ʷ��¼	:
 *  	����	: 2014-10-5 ����4:26:14 	�޸��ˣ�
 *  	����	:
 ************************************************************/
public class WaveSurfaceView extends SurfaceView implements Callback, Runnable {

    /**
     *�Ƿ���� 
     */
    private boolean isMove = false;
    /**
     * �ƶ�����
     */
    private int orientation = 1;
    /**
     * �����ƶ�
     */
    public final static int MOVE_LEFT = 0;
    /**
     * �����ƶ�
     */
    public final static int MOVE_RIGHT = 1;
    /**
     * �ƶ��ٶȡ�1.5s���ƶ�һ��
     */
    private long speed = 100;
    /**
     *��Ļ���� 
     */
    private String content = "1111111111111111111111111111111111111111";
    
    /**
     * ��Ļ����ɫ
     * */
    private String bgColor = "#E7E7E7";
    
    /**
     * ��Ļ͸���ȡ�Ĭ�ϣ�60
     */
    private int bgalpha = 60;
    
    /**
     * ������ɫ ��Ĭ�ϣ���ɫ (#FFFFFF)
     */ 
    private String fontColor = "#FFFFFF";
    
    /**
     * ����͸���ȡ�Ĭ�ϣ���͸��(255) 
     */
    private int fontAlpha = 255;
    
    /**
     * �����С ��Ĭ�ϣ�20
     */ 
    private float fontSize = 20f;
    /**
     * ����
     */
    private SurfaceHolder mSurfaceHolder;
    /**
     * �߳̿���
     */
    private boolean loop = true;    
    /**
     * ���ݹ���λ����ʼ����
     */
    private float x=0;
    
    /**
     * @param context
     * <see>Ĭ�Ϲ���</see>
     */
    public WaveSurfaceView(Context context) {
        super(context);
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        //���û���������Ϊ��ɫ���̳�Surefaceʱ�����������͸��
        setZOrderOnTop(true);
        mSurfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
        //����ɫ
        setBackgroundColor(Color.parseColor(bgColor));
        //����͸��
        getBackground().setAlpha(bgalpha);
    }
    
    public WaveSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
    
	public WaveSurfaceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	/**
     * @param context
     * @param move
     * <see>�Ƿ����</see>
     */
    public WaveSurfaceView(Context context,boolean move) {
        this(context);
        this.isMove = move;
        setLoop(isMove());
    }
    public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {}

    public void surfaceCreated(SurfaceHolder holder) {
//        mSurfaceHolder = getHolder();
//        mSurfaceHolder.addCallback(this);
//        //���û���������Ϊ��ɫ���̳�Surefaceʱ�����������͸��
//        setZOrderOnTop(true);
//        mSurfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
//        //����ɫ
//        setBackgroundColor(Color.parseColor(bgColor));
//        //����͸��
//        getBackground().setAlpha(bgalpha);
//        setMove(true);
        Log.d("WIDTH:",""+getWidth());
        if(isMove){//����Ч��
            if(orientation == MOVE_LEFT){
                x = getWidth();
            }else{
                x = -(content.length()*10);
            }
            new Thread(this).start();
        }else{//������ֻ��һ��
            draw();
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        loop = false;
    }
    /**
     * ��ͼ 
     */
    private void draw(){
        //��������
        Canvas canvas = mSurfaceHolder.lockCanvas();
        if(mSurfaceHolder == null || canvas == null){
            return;
        }        
        Paint paint = new Paint();        
        //����
        canvas.drawColor(Color.TRANSPARENT,Mode.CLEAR);
        //���
        paint.setAntiAlias(true);
        //����
        paint.setTypeface(Typeface.SANS_SERIF);
        //�����С
        paint.setTextSize(fontSize);
        //������ɫ
        paint.setColor(Color.parseColor(fontColor));
        //����͸����
        paint.setAlpha(fontAlpha);
        //������
        canvas.drawText(content,x,(getHeight()/2+5), paint);        
        //������ʾ
        mSurfaceHolder.unlockCanvasAndPost(canvas);
        //����Ч��
        if(isMove){
            //������ռ����
            float conlen = paint.measureText(content);
            //������
            int w = getWidth();
            //����
            if(orientation == MOVE_LEFT){//����
                if(x< -conlen){
                    x = w;
                }else{
                    x -= 2;
                }
            }else if(orientation == MOVE_RIGHT){//����
                if(x >= w){
                    x = -conlen;
                }else{
                    x+=2;
                }
            }
        }        
    }
    public void run(){
        while(loop){            
            synchronized (mSurfaceHolder) {
                draw();
            }
            try{
                Thread.sleep(speed);
            }catch(InterruptedException ex){
                Log.e("TextSurfaceView",ex.getMessage()+"\n"+ex);
            }
        }
        content = null;
    }
    /******************************set get method***********************************/

    private int getOrientation() {
        return orientation;
    }

    /**
     * @param orientation
     *  <li>����ѡ���ྲ̬����</li>
     *  <li>1.MOVE_RIGHT ���� (Ĭ��)</li>
     *  <li>2.MOVE_LEFT  ����</li>
*/
    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    private long getSpeed() {
        return speed;
    }

    /**
     * @param speed
     * <li>�ٶ��Ժ�����������ƶ�֮���ʱ����</li>
     * <li>Ĭ��Ϊ 1500 ����</li>
     */
    public void setSpeed(long speed) {
        this.speed = speed;
    }
    public boolean isMove() {
        return isMove;
    }
    /**
     * @param isMove
     * <see>Ĭ�Ϲ���</see>
     */
    public void setMove(boolean isMove) {
        this.isMove = isMove;
    }
    public void setLoop(boolean loop) {
        this.loop = loop;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }
    public void setBgalpha(int bgalpha) {
        this.bgalpha = bgalpha;
    }
    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }
    public void setFontAlpha(int fontAlpha) {
        this.fontAlpha = fontAlpha;
    }
    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }
    
}