package com.cardiograph.constance;

import java.util.ArrayList;
import java.util.List;

import com.cardiograph.model.User;

/**
 * ȫ�ֲ�����,�ṩȫ���������ڵľ�̬����
 * 
 * @author ��ͬ��
 * 
 */
public class Parameters {
	private Parameters() {

	}

//	public static ShoudanRegisterInfo merchantInfo = null;
	
	public static String installationId="";

	/**
	 * �̻���
	 */
	public static String merchantName = "";
	/** �������� */
	public static final String CHANNEL_FLAG_AnZhi = "2";// 2�Ű�Ϊ�����г�
	/**
	 * �̻���
	 */
	public static String merchantNo = "";

	public static String rentInpan = "";

//	public static List<TransactionManager.TransType> chargeBusinesses = new ArrayList<TransactionManager.TransType>();
	/** Ӧ��ͼ�������ļ� **/
	public final static String appIconConfig = "appicon.config";

	/**
	 * 
	 * �̻�״̬
	 * 
	 * �̻�״̬,0:δ��ͨ;1:�ѿ�ͨ;2:����;3:���δͨ��; 4,�޸Ĺ�ע����Ϣ����ͨ�����
	 * 
	 */
	public static int merchantState = 0;

	/**
	 * 
	 * �û�״̬
	 * 
	 * trueδͨ��,���Ա��޸�(null����REJECT������NONE" �����ύ) falseͨ�����ɱ��޸�( PASS��APPLY�����ύ)
	 * 
	 */
	public static boolean authState = true;

	public static boolean isLphone = false;

	public static int connectType = 0;

	/**
	 * �Ƿ����ƥ��ˢ���� true ������ˢ�������رյ�ǰactivity
	 */
	public static boolean isGiveUp = false;

	/**
	 * �����ַ����
	 */
	public enum UrlType {
		TEST_INTRANET, // ��������
		TEST_INTERNET, // ��������
		PRODUCT, // ����
		RESERVE, // ����
	}

	/**
	 * �����ַ����
	 */
	public static UrlType urlType = UrlType.TEST_INTERNET;

	/**
	 * ���Ա�ǣ���ʽ�������Ϊ false �� �������Ժ����е�http���������� debug=1 ������
	 * �����Ƿ���ǩ����֤��Ϊtrueʱ��������ǩ����֤��Ϊfalse���ҵ��汾�Ŵ��ڵ���13ʱ������ǩ����֤
	 */
	public static final boolean debug = true;
	/**
	 * ����������ʶ,������ʱ����Ҫ���ݲ�ͬ�������޸Ĵ�ֵ
	 */
//	public static final String downloadFlag = Util.getChanel();
	public static final int uploadDelayMinutes = debug ? 1 : 10;

	/**
	 * ����&���Է�������ַ���� ��true��ʹ�ò��Է������� false��ʹ������������
	 */
	public static boolean useDeveloperURL = true;

	/**
	 * �Ƿ�ʹ����������������true ʹ��(��������)��false ʹ������������(��ʽ�ķ�����)��
	 */
	public static final boolean userLanServer = true;

	/**
	 * ������http����Ӧ����Ƿ���UserToken��Ч��
	 */
	public static boolean httpResponseAfterCheckToken = true;

	/** ����������ַ���õ�ַ�� initServerAddress() �г�ʼ���� */
	public static String serviceURL = "";

	/** �����ػݷ����ַ���õ�ַ�� initServerAddress() �г�ʼ���� */
	public static String bianlitehuiServiceUrl = "";

	/** ����ͳ����ص�ַ���õ�ַ�� initServerAddress() �г�ʼ���� */
	public static String statisticsURL = "";

	public final static String TAG = "lakala_tag";
	public final static String BUY_SHUAKAQI_RUL = "http://m.lakala.com/quote/buy_skb.html";
	public final static String WEIBO_URL = "http://www.weibo.cn/lakala";
	public final static String WINXINLOADDOWN = "http://weixin.qq.com/d";
	/** Ǯ����ֵҳ���ֵ˵��ҳ�ĵ�ַ */
	public final static String WALLET_RECHARGE_URL = useDeveloperURL ? "http://10.1.21.63:8080/film_ticket/czsm.jsp?platType=1" : "http://user.lakala.com/film_ticket/czsm.jsp?platType=1";

	/** ����ҳ��ǰ׺ */
	private final static String HELP_URL_PREFIX = "http://download.lakala.com.cn/lklmbl/html/";

	/** �������ҳ��ĵ�ַ */
	public final static String MORE_HELP_PAGE_URL = HELP_URL_PREFIX + "help.html";

	/** ��ӰƱ��Ʊ����ҳ�ĵ�ַ */
	public final static String MOVIE_BUY_TICKET_HELP_URL = HELP_URL_PREFIX + "movie_help.html";

	/** ���ת��Э��ҳ�ĵ�ַ */
	public final static String BIG_AMOUNT_REMIT_PROTOCAL_URL = HELP_URL_PREFIX + "largetransfers.html";

	/** ��ͨ˵��ҳ�ĵ�ַ */
	public final static String BIG_AMOUNT_REMIT_DESCRIPTION_URL = HELP_URL_PREFIX + "open_instruction.html";

	/** ������� */
	public final static String BONUS = HELP_URL_PREFIX + "bonus.html";

	/** Ǯ������Э�� */
	public final static String WALLET_SERVICE = HELP_URL_PREFIX + "wallet_service.html";

	/** �����շѱ�׼ */
	public final static String TIXIAN_GUI_ZHE = HELP_URL_PREFIX + "the_charging_standard.html";

	/** �޿��������Э�� */
	public final static String NO_CARD_SERVICE = HELP_URL_PREFIX + "no_card_service.html";

	/** �ֻ��Ż�����Э�� */
	public final static String MOBILE_REMITTANCE_SERVICE = HELP_URL_PREFIX + "mobile_transfer.html";

	/** �ֻ��Ż�����˵�� */
	public final static String MOBILE_REMITTANCE_MORE_SERVICE = HELP_URL_PREFIX + "mobile_more.html";

	/** ��ͨ���ת�˵��շѱ�׼��ҳ�� */
	public final static String ZHUAN_ZHANG_FEE_URL = HELP_URL_PREFIX + "fee_scale.html";

	/** ��ͨ���ת�˵��շѱ�׼��ҳ�� */
	public final static String SHOU_KUAN_BAO_URL = HELP_URL_PREFIX + "skb_help.html";

	/** �տʹ�÷���Э�� */
	public final static String SHOU_KUAN_BAO_SERVICE_URL = HELP_URL_PREFIX + "skb_service.html";

	/** �տ���� */
	public final static String SHOU_KUAN_BAO_RATE_URL = HELP_URL_PREFIX + "skb_rate.html";

	/** �տ���׹��� */
	public final static String TRANSACTION_RULES_OF_SHOUDAN = HELP_URL_PREFIX + "skb_rate.html";

	/** ����ǩ��˵�� */
	public final static String ELECTRONIC_SIGNATRUE_HELP = HELP_URL_PREFIX + "skb_sign_help.html";

	/** �������� */
	public final static String COLLECTION_CANCEL_HELP = HELP_URL_PREFIX + "skb_abolish_help.html";

	/** ��������˵�� **/
	public final static String UPGRADE_HELP = HELP_URL_PREFIX + "skb_update.html";
	/**
	 * ��ע������
	 */
	public final static String FOLLOW_LAKALA = HELP_URL_PREFIX + "skb_lklwx.html";

	/**
	 * ��Լ�ɷ�˵��
	 */
	public final static String TEYUEJIAOFEI_DESCRIPTION = HELP_URL_PREFIX + "skb_teyue.html";

	/**
	 * ��Լ�ɷ�Э��
	 */
	public final static String TEYUEJIAOFEI_PRO = HELP_URL_PREFIX + "skb_teyuexieyi.html";

	/** ���Ӱ��� **/
	public final static String CONNECTION_HELP = HELP_URL_PREFIX + "skb_connect.html";
	/**
	 * �û�����Э��
	 */
	public final static String LAKALA_USER = HELP_URL_PREFIX + "lkl_user.html";

	/**
	 * ����
	 */
	public final static String HELP_SERVICE_URL = HELP_URL_PREFIX+"hebao_help.html";
	
	/**
	 * ����
	 */
	public final static String ABOUT_SERVICE_URL = HELP_URL_PREFIX+"hebao_about.html";

	// ����������ʹ�ñ����ļ�
	public final static String LOCAL_HELP_URL = "http://download.lakala.com.cn/lklmbl/newhtml/";
	/**
	 * �̻��տ����8
	 */
	public final static String LOCAL_HELP_MERCHANT_COLLECTION = LOCAL_HELP_URL + "skb_help_shoukuan.html";
	/**
	 * �������װ���2
	 */
	public final static String LOCAL_HELP_CANCEL_TRANS = LOCAL_HELP_URL + "skb_help_chexiao.html";
	/**
	 * ���ÿ��������3
	 */
	public final static String LOCAL_HELP_HUANKUAN = LOCAL_HELP_URL + "skb_help_huankuan.html";
	/**
	 * ת�˻�����10
	 */
	public final static String LOCAL_HELP_TRANSFER = LOCAL_HELP_URL + "skb_help_zhuanzhang.html";
	/**
	 * ��Ʒ�������6
	 */
	public final static String LOCAL_HELP_PRODUCTADAPTER = LOCAL_HELP_URL + "skb_help_shipei.html";
	/**
	 * ��Ʒ���Ӱ���4
	 */
	public final static String LOCAL_HELP_PRODUCTCON = LOCAL_HELP_URL + "skb_help_lianjie.html";

	/**
	 * ע�Ὺͨ����11
	 */
	public final static String LOCAL_HELP_REGISTER_OPEN = LOCAL_HELP_URL + "skb_help_zhuce.html";
	/**
	 * ����ˢ������9
	 */
	public final static String LOCAL_HELP_ABOUT_SWIP = LOCAL_HELP_URL + "skb_help_shuaka.html";
	/**
	 * ���װ�ȫ1
	 */
	public final static String LOCAL_HELP_TRANSSAVE = LOCAL_HELP_URL + "skb_help_anquan.html";
	/**
	 * ������˺��޸�12
	 */
	public final static String LOCAL_HELP_INFO_UPDATE = LOCAL_HELP_URL + "skb_help_ziliao.html";
	/**
	 * �̻�����5
	 */
	public final static String LOCAL_HELP_MERCHANT_UP = LOCAL_HELP_URL + "skb_help_shengji.html";
	/**
	 * ��Ʒ�ۺ�7
	 */
	public final static String LOCAL_HELP_PRODUCT_SERVICE = LOCAL_HELP_URL + "skb_help_shouhou.html";

	public final static String NIGHT_HELP = LOCAL_HELP_URL + "skb_help_bak.html";

	public static final String DESCRIPTION = "http://download.lakala.com.cn/lklmbl/newhtml/shengji/";
	public final static String DES_REMITTANCE_OPEN_APPLY = DESCRIPTION + "zhaohangykt.html";
	public final static String DES_MERCHANT_UP_LEVEL = DESCRIPTION + "up.html";

	public static final String SETTLEMENT_MERCHANT_CHANGE = DESCRIPTION + "zhaohang.html";
	
	/** Ӧ�ó������ݴ洢·�� */
//	public static String appDataPath = "/data/data/" + ApplicationExtension.getInstance().getPackageName();

	/** ����ҳ��ʼ����������button����ײ��������루�Դ�Ϊ�������ֱ��������м���ʵ�ʾ��룩 **/
	public static int marginToBottom = 115;

	/** ����ǰһ�β����ˢ��ͷ�Ĵ��� **/
	public static String preSwiperNo = "";

	/** ǰһ�β���ˢ��ͷ��״̬ **/
//	public static ESwiperState preSwiperState = ESwiperState.unusable;

	/** ǰһ�β���ˢ��ͷ��Ӧ���û��� **/
	public static String preUserForSwiper = "";

	/** ��ǰˢ��ͷ�Ĵ��� **/
	public static String swiperNo = "";

	/** ��ǰˢ��ͷ�Ƿ�ͨ **/
//	public static ESwiperState swiperState = ESwiperState.unusable;

	/** ��ǰˢ��ͷ��Ӧ���û� **/
	public static String userForSwiper = "";

	public static String serverTel = "";

	/** ��Ļ�ֱ���,��Ҫ��Ϊ���е� */
//	public static EScreenDensity screenDensity = EScreenDensity.MDPI;

	public static int densityDpi = 240;

	/** ��Ļ��� **/
	public static int screenWidth;

	/** ��Ļ�߶� **/
	public static int screenHeight;
	/** ��������ػ���ƷID ����PVͳ�� */
	public static int productId = -1;
	/** ����ˢ������ʽ */
	public static int GouMai_Type = -1;

	/** ����ͳ�ƻ�����Ʒ��Pid */
	public static int pid = -1;
	/** ����ͳ�ƻ�����ƷpCode */
	public static int pCode = -1;
	/** Dialogͣ����ʱ�� */
	public static int dialogResidenceTime = 2000;

	/** ��������ɹ��󷵻ص�״̬�� */
	public static final String successRetCode = "0000";
	/** ��������,������ */
	public static final String networkProblem = "0009";
	/** ��֤����֤ʧ�� */
	public static final String vercodeError = "1001";
	/** �û������� */
	public static final String userNoExists = "1002";
	/** �û��Ѿ����� */
	public static final String userHasExists = "1003";
	/** �û������������ */
	public static final String userOrPWError = "1004";
	/** ��������Ϊ�� */
	public static final String reqDataNull = "1007";
	/** ϵͳ�쳣 */
	public static final String sysAbnormal = "1010";
	/** ������� */
	public static final String pwError = "1013";
	/** ��Ч��Token (���ϴε�¼ʱ������������µ�¼)*/
	public static final String tokenOutOfDate = "1023";
	// ��ȡ�˵������б�Ϊ��
	public static final String tradeRecordNull = "1027";
	// ���׳�ʱͳһcode
	public static final String tranTimeOut = "1030";
	// ����ÿ�ն����޶�
	public static final String msgNoSendAmount = "1032";
	// ����ͨȦ�治֧�����ÿ�
	public static final String creditCardNotSupport = "00Re";
	// �ݲ�֧�ֻ��������Ŀ�
	public static final String cardNotSupport = "00RT";
	/** Android �ͻ��� ID */
	 public static final String androidClientID = "HHHwsaAnroid_sdfa@a22222sfasd1$%^&&882**(2";
//	public static final String androidClientID = "asdfas@weeweaPos@saqwqwqqw228228()#44%";

	// ���Ի������䱣����Կ
	public static String debugTransferKey = "11112222333344445555666677778888";

	public static User user = new User();

	public final static String PACKAGENAME = "com.chinamobile.schebao.lakala";
	// ���ط����͹㲥:��ʼ����
	public final static String DOWNLOAD_SERVICE_DOWNLOAD_START_ACTION = PACKAGENAME.concat(".broadcast.download.start");
	// ���ط����͹㲥�����½�����
	public final static String DOWNLOAD_SERVICE_DOWNLOAD_PROGRESS_ACTION = PACKAGENAME.concat(".broadcast.download.progress");
	// ���ط����͹㲥:�����Ѿ����
	public final static String DOWNLOAD_SERVICE_DOWNLOAD_COMPLETE_ACTION = PACKAGENAME.concat(".broadcast.download.complete");
	// ���ط����͹㲥:������״̬
	public final static String DOWNLOAD_SERVICE_DISCONNECT_NETWORK_ACTION = PACKAGENAME.concat(".broadcast.download.error");
	// ���ط����͹㲥:ȡ������
	public final static String DWONLOAD_SERVICE_DOWNLOAD_CANCEL_ACTION = PACKAGENAME.concat(".broadcast.download.cancel");
	/** ������Ϣ��ѯʱ�� */
	public final static int pushMessagegTime = debug ? 5 : 60;

	/** ͼƬ����·�� **/
	public final static String imageCachePath = "/lakala/imgCache/";

	/**
	 * ��ʼ����������ַ
	 */
	public static void initServerAddress(){
        switch (urlType){
            case PRODUCT:
//              serviceURL = "https://mpos.lakala.com/apos/";
//                serviceURL = "https://mobile.lakala.com/android/";
                serviceURL = "https://mobile.lakala.com/HBA/";
                useDeveloperURL = false;
                break;
                
            case RESERVE:
                useDeveloperURL = false;
                serviceURL = "https://mobile.lakala.com:6443/apos/";
                break;
                
            case TEST_INTERNET:
                useDeveloperURL = true;
//                serviceURL = "http://180.166.12.107:8280/mpos/apos/";
                serviceURL = "http://1.202.150.4:8880/lakalaukeyRest/android/";
                break;
                
            case TEST_INTRANET:
                useDeveloperURL = true;
//                serviceURL = "http://10.7.111.32:8280/mpos/apos/";
                serviceURL = "http://10.5.12.66:8080/lakalaukeyRest/android/";
                break;
                
            default:
                break;
        }
	}

	public static void clear() {
//		Parameters.user.clear();
//		Parameters.userForSwiper = "";
//		Parameters.swiperNo = "";
//		Parameters.merchantName = "";
//		Parameters.merchantNo = "";
//		Parameters.merchantState = 0;
//		Parameters.authState = false;
//		Parameters.merchantInfo = null;
//		Parameters.rentInpan = "";
//		chargeBusinesses.clear();
//		DBManager.getInstance().destroy();
//		TerminalKey.clear();
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//
//				SwiperManager swiperManeger = SwiperManager.getInstance();
//				swiperManeger.setIsSwiperValid(false);
//				swiperManeger.deleteSwiper();
//			}
//		}).start();
	}

}
