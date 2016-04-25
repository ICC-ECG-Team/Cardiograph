package com.cardiograph.component;

import com.example.cardiograph.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 *  ������
 *
 *  +--------------------------+
 *  |  back    title     action|
 *  +--------------------------+
 */
public class NavigationBar extends LinearLayout implements View.OnClickListener{

    private int background;         //���屳��
    private int bottomBg;           //�ײ�������
    private int backBg;             //���ذ�ť����
    private String backText;        //���ذ�ť����
    private String actionText;      //������ť����
    private int actionBg;           //������ť����
    private int textColor;          //������ɫ������title�Ͳ�����ť��������ɫ
    private float btnTextSize;      //��ť���ִ�С
    private float titleTextSize;    //title���ִ�С
    private int titleMaxLength;    //title��󳤶�


    private TextView backButton;     //���ذ�ť
    private TextView actionButton;        //������ť
    private TextView titleText;         //title�ı�
    private ImageView bottomImage;      //�ײ�imageview���������±ߵĻ�ɫ��
    private ProgressBar mProgress;      //�Ҳ������ʾbar

    private OnNavBarClickListener onNavBarClickListener;    //����������¼�������

    public enum NavigationBarItem{
        back,   //���ذ�ť
        title,  //title����
        action  //������ť�������ұߵİ�ť
    }

    /**
     * ��������¼������ӿ�
     */
    public interface OnNavBarClickListener{
        public void onNavItemClick(NavigationBarItem navBarItem);
    }

    /**
     * ���õ������¼�������
     * @param onNavBarClickListener ���������
     */
    public void setOnNavBarClickListener(OnNavBarClickListener onNavBarClickListener) {
        this.onNavBarClickListener = onNavBarClickListener;
    }

    /**
     * �����¼�������
     * @param view  �������view
     */
    @Override
    public void onClick(View view) {
        if(onNavBarClickListener == null) return;

        if (view.equals(backButton)){
            onNavBarClickListener.onNavItemClick(NavigationBarItem.back);
        }else if (view.equals(titleText)){
            onNavBarClickListener.onNavItemClick(NavigationBarItem.title);
        }else if (view.equals(actionButton)){
            onNavBarClickListener.onNavItemClick(NavigationBarItem.action);
        }
    }

    public NavigationBar(Context context){
        super(context);
    }

    public NavigationBar(Context context,AttributeSet attrs){
        super(context,attrs);
        if (isInEditMode()){
            return;
        }
        initAttrs(context,attrs);
        initView();
    }

    /**
     * ���÷��ذ�ť����
     * @param backText ��ť����
     */
    public void setBackText(String backText){
        backButton.setText(backText);
    }

    /**
     * ���÷��ذ�ť����
     * @param resId ��ť����id
     */
    public void setBackText(int resId){
        String text = getContext().getString(resId);
        setBackText(text);
    }

    /**
     * ���ñ���
     * @param title ��������
     */
    public void setTitle(String title){
        titleText.setText(title);
    }

    /**
     * ���ñ���
     * @param resId ��������id
     */
    public void setTitle(int resId){
        String text = getContext().getString(resId);
        setTitle(text);
    }

    /**
     * ��ȡ��������
     * @return �����ַ���
     */
    public String getTitle(){
        return titleText.getText().toString();
    }

    /**
     * ��textview���ͼ��
     * @param left     ���ͼ��id
     * @param top      �ϲ�ͼ��id
     * @param right    �Ҳ�ͼ��id
     * @param bottom   �ײ�ͼ��id
     */
    public void setTitleCompoundDrawablesWithIntrinsicBounds(int left,int top,int right,int bottom){
        titleText.setCompoundDrawablesWithIntrinsicBounds(left,top,right,bottom);
    }

    /**
     * ���ñ������ִ�С
     * @param size �������ִ�С
     * �˷����رգ�����ÿ���˶������м�������ִ�С�����»���
     */
//    public void setTitleTextSize(int unit,float size){
//        titleText.setTextSize(unit,size);
//    }

    /**
     * ���ò�����ť����
     * @param actionText
     */
    public void setActionBtnText(String actionText){
        actionButton.setVisibility(View.VISIBLE);
        actionButton.setText(actionText);
    }

    /**
     * ��ȡ������ť������
     * @return
     */
    public String getActionBtnText(){
       return actionButton.getText().toString();
    }

    /**
     * ���ò�����ť����
     * @param resId ������ť����id
     */
    public void setActionBtnText(int resId){
        setActionBtnText(getContext().getString(resId));
    }

    /**
     * ���ò�����ť������ɫ
     * @param color
     */
    public void setActionBtnTextColor(int color){
       actionButton.setTextColor(color);
    }

    /**
     * ���ò�����ť�Ƿ����
     * @param isEnable
     */
    public void setActionBtnEnabled(boolean isEnable){
       actionButton.setEnabled(isEnable);
    }

    /**
     * ���÷��ذ�ť�Ƿ�ɼ�
     * @param visibility
     */
    public void setBackBtnVisibility(int visibility){
        backButton.setVisibility(visibility);
    }

    /**
     * ���ò�����ť�Ƿ�ɼ�
     * @param visibility
     */
    public void setActionBtnVisibility(int visibility){
        actionButton.setVisibility(visibility);
    }

    /**
     * ���ò�����ť����
     * @param resourceId  ��Դid
     */
    public void setActionBtnBackground(int resourceId){
        actionButton.setBackgroundResource(resourceId);
    }

    /**
     * ��ʾ�Ҳ������ʾbar
     */
    public void showRightProgress(){
        actionButton.setVisibility(GONE);
        mProgress.setVisibility(VISIBLE);
    }

    /**
     * �����Ҳ������ʾbar
     */
    public void hideRightProgress(){
        mProgress.setVisibility(GONE);
    }
    /**
     * ��ʼ������ֵ
     * @param context ������
     * @param attrs ����
     */
    private void initAttrs(Context context,AttributeSet attrs){
        //��ȡxml�����õ�������Դ
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NavigationBar);
        try{
            background      = typedArray.getResourceId(R.styleable.NavigationBar_navBg,0);
            bottomBg        = typedArray.getResourceId(R.styleable.NavigationBar_bottomBg,0);
            backBg          = typedArray.getResourceId(R.styleable.NavigationBar_backBg, 0);
            actionText      = typedArray.getString(R.styleable.NavigationBar_actionText);
            backText        = typedArray.getString(R.styleable.NavigationBar_backText);
            actionBg        = typedArray.getResourceId(R.styleable.NavigationBar_actionBg, 0);
            textColor       = typedArray.getColor(R.styleable.NavigationBar_textColor, 0xFFFFFFFF);
            btnTextSize     = typedArray.getDimension(R.styleable.NavigationBar_btnTextSize, 0.0f);
            titleTextSize   = typedArray.getDimension(R.styleable.NavigationBar_titleTextSize,0.0f);
            titleMaxLength  = typedArray.getInt(R.styleable.NavigationBar_titleMaxLength, 9);
        }finally {
            typedArray.recycle();
        }
    }

    /**
     * ��ʼ���������е����Ԫ��
     */
    private void initView(){
        LayoutInflater.from(getContext()).inflate(R.layout.l_navigation_bar,this,true);

        backButton   = (TextView)findViewById(R.id.nav_back);
        actionButton = (TextView)findViewById(R.id.nav_right_btn);
        titleText    = (TextView)findViewById(R.id.nav_center_text);
        bottomImage  = (ImageView)findViewById(R.id.nav_bottom_image);
        mProgress    = (ProgressBar)findViewById(R.id.nav_right_progress);

        //����
        RelativeLayout relativeLayout = (RelativeLayout)backButton.getParent();
        relativeLayout.setBackgroundResource(background);
        //�ײ���ɫ����
        bottomImage.setBackgroundResource(bottomBg);

        //���ذ�ť
        backButton.setBackgroundResource(backBg);
        backButton.setText(backText);
        backButton.setTextColor(textColor);

        //������ť
        actionButton.setBackgroundResource(actionBg);
        actionButton.setText(actionText);
        actionButton.setTextColor(textColor);

        //��������
        titleText.setTextColor(textColor);
        InputFilter.LengthFilter lengthFilter = new InputFilter.LengthFilter(titleMaxLength);
        InputFilter[] filters = {lengthFilter};
        titleText.setFilters(filters);

        //�����С
        if (Float.compare(btnTextSize,0.0f)>0){
            backButton.setTextSize(TypedValue.COMPLEX_UNIT_PX,btnTextSize);
            actionButton.setTextSize(TypedValue.COMPLEX_UNIT_PX,btnTextSize);
        }

        if (Float.compare(titleTextSize,0.0f)>0)
            titleText.setTextSize(TypedValue.COMPLEX_UNIT_PX,titleTextSize);

        //�����¼�������
        backButton.setOnClickListener(this);
        titleText.setOnClickListener(this);
        actionButton.setOnClickListener(this);
    }
}
