package com.ad.miidi;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HtmlParse {

	public static void main(String[] args) {
		
		String str = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\"><head>	<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />	<meta name=\"viewport\" content=\"width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no\">	<title>�׵ϻ���ǽ</title>	<link href=\"./styles/css.css\" rel=\"stylesheet\" type=\"text/css\" />	<script type=\"text/javascript\" src=\"./js/jquery-1.6.4.min.js\"></script>	<script type=\"text/javascript\" src=\"./js/jquery.cookies.2.2.0.min.js\"></script>	<script type=\"text/javascript\" > 		function clickTest(event)  		{   			initApkList();   		}  		  		function openApp(packageName)  		{  		if(  window.hasOwnProperty('androidCallBack')  )  			{  				androidCallBack.openApp(packageName);  			}  		}  		  		  		function install(id)  		{ 			if(  window.hasOwnProperty('androidCallBack')  )  			{  				androidCallBack.testInstall(id);  			}  		}  		  		function initApkList()  		{  			$('li[packagename]').add('li[cpaid]').each(function(index, element)  			{  				var packageName = $(element).attr('packagename');  				var cpaId = $(element).attr('cpaid');  				var name = $(element).attr('apkname');  				var runAppScore = $(element).attr('runAppScore');  				  				var moneyname = $(element).attr('moneyname');  				  				var showScore =$(element).attr('showScore');  				var alreadyInstalled =$(element).attr('alreadyInstalled');  				var apkname =$(element).attr('apkname');   				var apknameid =\"app_\"+$(element).attr('cpaid');   				var actnameid =\"act_\"+$(element).attr('cpaid');   				var detailid =\"detail_\"+$(element).attr('cpaid');		   		var nodeid =\"node_\"+$(element).attr('cpaid');		   				   				//var style=\"sjf3\";   				var style=\"jieshao-b2\";   				  				var openscore = $(element).attr('openscore');  				if(window.hasOwnProperty('androidCallBack'))  				{  					//��ѯ�Ƿ��Ѿ���װ  					var flg = androidCallBack.checkInstalled(packageName);  										var appint = null; 					appint = $.cookies.get(packageName); 					 					if ( appint != null ){ 						alreadyInstalled=\"yes\"; 					}	  					if(Boolean(flg)){			 			var jcookies = jaaulde.utils.cookies; 						jcookies.set(packageName, 1, { hoursToLive : 86500 });  						//��������Ѿ���װ   						$(\"#\"+detailid.toString()).attr(\"href\", $(element).attr('openurl') );   						$(\"#\"+actnameid.toString()).attr(\"href\", $(element).attr('openurl') );	  	  				if(alreadyInstalled==\"yes\"){		   					$(\"#\"+detailid.toString()).attr(\"href\", $(element).attr('openurl') );	  						//�Ѿ���װ������server�˼�¼��װ�� Ҫ��ʾ����	   							$(\"#\"+actnameid.toString()).html( \"����\" );	   							$(\"#\"+actnameid.toString()).attr(\"class\", \"jieshao-b2\" );	   						$(\"#\"+actnameid.toString()).attr(\"href\", $(element).attr('openurl') );	  						//$(\"#\"+apknameid.toString()).html(apkname.toString()+\"(�Ѱ�װ)\" );   						}else{						  //	$(\"#\"+nodeid.toString()).remove();							// server��û�м�¼��װ  							// $(\"#\"+actnameid.toString()).html( \"�������\" );  							// reg b2   							 $(\"#\"+actnameid.toString()).attr(\"class\", \"jieshao-b2\" );	  						 $(\"#\"+actnameid.toString()).html(\"�Ѱ�װ\" );   						}  						 					}else{  						//����û�а�װ�� ������ǰ��װ���ģ� �Ͳ����û��ڰ�װ�ˣ�					//	$(\"#\"+nodeid.toString()).remove();  						if(alreadyInstalled==\"yes\"){  					//	alert( \"kkkk: \" + packageName + \"  test , \" +  Boolean(flg) + \" \" + appint );  							$(\"#\"+actnameid.toString()).attr(\"class\", \"jieshao-b2\" );	  						$(\"#\"+actnameid.toString()).html(\"�Ѱ�װ\" );	  						//1 ok	  						if (1==1){	 							$(\"#\"+detailid.toString()).attr(\"href\", \"#\" );	  						}	  					}  											}  				};   			}  			);  	  		}  		  		$(function(){  			initApkList();  		});	</script>  </head>   <body><div class=\"header\">    <div class=\"fanhui\"><a href=\"javascript:androidCallBack.goBack();\"><img src=\"images/bottom_03.png\" width=\"48\" height=\"30\" /></a></div>    <div class=\"bt\"><font>     ��ѻ�ȡ����    </font></div>          <div class=\"jifen\"><img src=\"images/jf_07.png\" width=\"22\" height=\"28\" /><font>5</font></div></div><div class=\"shiyan\">  <div class=\"shiyan_a\">�׵Ϲ�潱�������б�</div></div><div class=\"content1\">	<ul  id=\"apklistview\" >					<li cpaid=\"633\"  			runAppScore=\"yes\"  			showScore=\"yes\"  		  alreadyInstalled=\"no\"   		packagename=\"com.changba\" apkname=\"����\"  installscore=\"30\"   		openscore=\"5\" openurl=\"mopen://f9850145f21ab59bc5ac854322edd2d84bae50263dc716ea99986670b27027f91cde26069c3565aa2cee195f74780347e495386f4792ed45c7bf4e0b418e9e222c9204406dbb58ac\" moneyname=\"����\">	</li>	 	<div class=\"content-top1\" id=\"node_633\" > 		<a class=\"xzlj\" name=\"detail\" id=\"detail_633\" href=\"http://ads.miidi.net/appscore4//cpapagedetail.bin?productId=18770&unionId=8000&imei=869421015119926&channelId=0&adFrom=3&downAppid=633\" >   		<div class=\"tubiao\"><img src=\"http://www.miidi.net/appsoft/cpa/imgs/icon_com.changba_20131217160505.png\" width=\"48\" height=\"48\" /></div>			 		<div class=\"jianjie\">  			<p><b id=\"app_633\" >����</b>  			<font class=\"jieshao_reg\"	id=\"act_633\" name=\"redirect\">ע����30����</font></p>  			<span>�������K������������ĸ����ܴ�ܶ����ˣ�</span>  <br><font color=\"red\">�״ΰ�װ���Գ�����һ�׸�</font>		</div>		</a>   	 </div>						<li cpaid=\"201\"  			runAppScore=\"yes\"  			showScore=\"yes\"  		  alreadyInstalled=\"no\"   		packagename=\"com.baidu.appsearch\" apkname=\"�ٶ��ֻ�����\"  installscore=\"30\"   		openscore=\"5\" openurl=\"mopen://9a6a036092383ef66fbd0d7f2714275590e2fa284cd605f73ca164bde0b99fa8e6716962584cf830e9afda687171566b719a0251d8c5f02bb20c3a11788a417e8b4fd8cafad5d2e0\" moneyname=\"����\">	</li>	 	<div class=\"content-top1\" id=\"node_201\" > 		<a class=\"xzlj\" name=\"detail\" id=\"detail_201\" href=\"http://ads.miidi.net/appscore4//cpapagedetail.bin?productId=18770&unionId=8000&imei=869421015119926&channelId=0&adFrom=3&downAppid=201\" >   		<div class=\"tubiao\"><img src=\"http://www.miidi.net/appsoft/cpa/1366362490439167.png\" width=\"48\" height=\"48\" /></div>			 		<div class=\"jianjie\">  			<p><b id=\"app_201\" >�ٶ��ֻ�����</b>  			<font class=\"jieshao-b1\"	id=\"act_201\" name=\"redirect\">��װ��30����</font></p>  			<span>����Ӧ�ã������������ͣ���������ˬ����</span>  <br><font color=\"red\">�״��������ã�24Сʱ���ٴ�����һ��</font>		</div>		</a>   	 </div>						<li cpaid=\"358\"  			runAppScore=\"yes\"  			showScore=\"yes\"  		  alreadyInstalled=\"no\"   		packagename=\"cn.ninegame.gamemanager\" apkname=\"������Ϸ����\"  installscore=\"35\"   		openscore=\"5\" openurl=\"mopen://9603eae02a30b69f4597d9a58b53dc884162856e1ca4230399986670b27027f91cde26069c3565aa2cee195f74780347e495386f4792ed45c7bf4e0b418e9e222c9204406dbb58ac\" moneyname=\"����\">	</li>	 	<div class=\"content-top1\" id=\"node_358\" > 		<a class=\"xzlj\" name=\"detail\" id=\"detail_358\" href=\"http://ads.miidi.net/appscore4//cpapagedetail.bin?productId=18770&unionId=8000&imei=869421015119926&channelId=0&adFrom=3&downAppid=358\" >   		<div class=\"tubiao\"><img src=\"http://www.miidi.net/appsoft/cpa/1386920405645117.png\" width=\"48\" height=\"48\" /></div>			 		<div class=\"jianjie\">  			<p><b id=\"app_358\" >������Ϸ����</b>  			<font class=\"jieshao-b1\"	id=\"act_358\" name=\"redirect\">��װ��35����</font></p>  			<span>�ֻ���Ϸһ���򾡣����Ŵ�������</span>  <br><font color=\"red\">�״����أ���������һ����Ϸ</font>		</div>		</a>   	 </div>						<li cpaid=\"37\"  			runAppScore=\"yes\"  			showScore=\"yes\"  		  alreadyInstalled=\"no\"   		packagename=\"com.jingdong.app.mall\" apkname=\"����\"  installscore=\"35\"   		openscore=\"5\" openurl=\"mopen://5967fee05cd1f3da2c3df7f50397b5ec41204e5dec533335d8ce594554a9a228dd2a9498ffbb1b71fcd7763cc64f9d7ee386f659ffe1ecd1000ded96c7abe1743b3041eabc31d6ef\" moneyname=\"����\">	</li>	 	<div class=\"content-top1\" id=\"node_37\" > 		<a class=\"xzlj\" name=\"detail\" id=\"detail_37\" href=\"http://ads.miidi.net/appscore4//cpapagedetail.bin?productId=18770&unionId=8000&imei=869421015119926&channelId=0&adFrom=3&downAppid=37\" >   		<div class=\"tubiao\"><img src=\"http://www.miidi.net/appsoft/cpa/imgs/icon_com.jingdong.app.mall_20131220143049.png\" width=\"48\" height=\"48\" /></div>			 		<div class=\"jianjie\">  			<p><b id=\"app_37\" >����</b>  			<font class=\"jieshao-b1\"	id=\"act_37\" name=\"redirect\">��װ��35����</font></p>  			<span>�Ͼ�������Ʒ�л�������Ʊ����������</span>  <br><font color=\"red\">�״����أ�����</font>		</div>		</a>   	 </div>						<li cpaid=\"996\"  			runAppScore=\"yes\"  			showScore=\"yes\"  		  alreadyInstalled=\"no\"   		packagename=\"cn.opda.a.phonoalbumshoushou\" apkname=\"�ٶ��ֻ���ʿ\"  installscore=\"30\"   		openscore=\"5\" openurl=\"mopen://e2a5a306e1bb23a7c9ab0171dee84bf0a8552b6028637bf899986670b27027f91cde26069c3565aa2cee195f74780347e495386f4792ed45c7bf4e0b418e9e222c9204406dbb58ac\" moneyname=\"����\">	</li>	 	<div class=\"content-top1\" id=\"node_996\" > 		<a class=\"xzlj\" name=\"detail\" id=\"detail_996\" href=\"http://ads.miidi.net/appscore4//cpapagedetail.bin?productId=18770&unionId=8000&imei=869421015119926&channelId=0&adFrom=3&downAppid=996\" >   		<div class=\"tubiao\"><img src=\"http://www.miidi.net/appsoft/cpa/imgs/icon_cn.opda.a.phonoalbumshoushou_20140109190058.png\" width=\"48\" height=\"48\" /></div>			 		<div class=\"jianjie\">  			<p><b id=\"app_996\" >�ٶ��ֻ���ʿ</b>  			<font class=\"jieshao-b1\"	id=\"act_996\" name=\"redirect\">��װ��30����</font></p>  			<span>����һ����ʹ�õ��ֻ���ȫ���ߣ���������ģ�</span>  <br><font color=\"red\">�״����أ�����</font>		</div>		</a>   	 </div>						<li cpaid=\"609\"  			runAppScore=\"yes\"  			showScore=\"yes\"  		  alreadyInstalled=\"no\"   		packagename=\"com.haodou.recipe\" apkname=\"�ö�����\"  installscore=\"30\"   		openscore=\"5\" openurl=\"mopen://5de20a89b1c8df71244e1977adbacd35524a0f4af54de09b99986670b27027f91cde26069c3565aa2cee195f74780347e495386f4792ed45c7bf4e0b418e9e222c9204406dbb58ac\" moneyname=\"����\">	</li>	 	<div class=\"content-top1\" id=\"node_609\" > 		<a class=\"xzlj\" name=\"detail\" id=\"detail_609\" href=\"http://ads.miidi.net/appscore4//cpapagedetail.bin?productId=18770&unionId=8000&imei=869421015119926&channelId=0&adFrom=3&downAppid=609\" >   		<div class=\"tubiao\"><img src=\"http://www.miidi.net/appsoft/cpa/icon_recipe-dp1_286.png\" width=\"48\" height=\"48\" /></div>			 		<div class=\"jianjie\">  			<p><b id=\"app_609\" >�ö�����</b>  			<font class=\"jieshao-b1\"	id=\"act_609\" name=\"redirect\">��װ��30����</font></p>  			<span>�й�������ֻ���ʳ���������û��֮һ</span>  <br><font color=\"red\">�״��������ã�24Сʱ���ٴ�����һ�λ�ȡ����</font>		</div>		</a>   	 </div>						<li cpaid=\"256\"  			runAppScore=\"yes\"  			showScore=\"yes\"  		  alreadyInstalled=\"no\"   		packagename=\"com.tmall.wireless\" apkname=\"��è\"  installscore=\"30\"   		openscore=\"5\" openurl=\"mopen://610b2a5a2057aeac34197d732e2e4b7722404e5d823dc3a43ca164bde0b99fa8e6716962584cf830e9afda687171566b719a0251d8c5f02bb20c3a11788a417e8b4fd8cafad5d2e0\" moneyname=\"����\">	</li>	 	<div class=\"content-top1\" id=\"node_256\" > 		<a class=\"xzlj\" name=\"detail\" id=\"detail_256\" href=\"http://ads.miidi.net/appscore4//cpapagedetail.bin?productId=18770&unionId=8000&imei=869421015119926&channelId=0&adFrom=3&downAppid=256\" >   		<div class=\"tubiao\"><img src=\"http://www.miidi.net/appsoft/cpa/1345705731094.png\" width=\"48\" height=\"48\" /></div>			 		<div class=\"jianjie\">  			<p><b id=\"app_256\" >��è</b>  			<font class=\"jieshao_reg\"	id=\"act_256\" name=\"redirect\">ע����30����</font></p>  			<span>��è�����Ĺ���</span>  <br><font color=\"red\">�״����أ�����</font>		</div>		</a>   	 </div>						<li cpaid=\"1698\"  			runAppScore=\"yes\"  			showScore=\"yes\"  		  alreadyInstalled=\"no\"   		packagename=\"com.tongcheng.android\" apkname=\"ͬ������\"  installscore=\"40\"   		openscore=\"5\" openurl=\"mopen://8abb888803037568bf6e1a0cc4aeceb8947c880afa0ea01e770e653d6967219d14342d1c3b82c09b506e1c2e8d7ef43da91cf072d354a90bcf85328fa3110ec609f1d7b5ad58f4d110393e4ca5709159d9f9595de75a8131\" moneyname=\"����\">	</li>	 	<div class=\"content-top1\" id=\"node_1698\" > 		<a class=\"xzlj\" name=\"detail\" id=\"detail_1698\" href=\"http://ads.miidi.net/appscore4//cpapagedetail.bin?productId=18770&unionId=8000&imei=869421015119926&channelId=0&adFrom=3&downAppid=1698\" >   		<div class=\"tubiao\"><img src=\"http://www.miidi.net/appsoft/cpa/imgs/201407/icon_com.tongcheng.android_20140701155410.png\" width=\"48\" height=\"48\" /></div>			 		<div class=\"jianjie\">  			<p><b id=\"app_1698\" >ͬ������</b>  			<font class=\"jieshao-b1\"	id=\"act_1698\" name=\"redirect\">��װ��40����</font></p>  			<span>ͬ������ʮ���꣬�ͼ������б��ϣ�</span>  <br><font color=\"red\">�״����أ�����</font>		</div>		</a>   	 </div>						<li cpaid=\"1445\"  			runAppScore=\"yes\"  			showScore=\"yes\"  		  alreadyInstalled=\"no\"   		packagename=\"com.jiami.mahjong\" apkname=\"�����齫\"  installscore=\"40\"   		openscore=\"5\" openurl=\"mopen://1fff3e86c86cb68507057d154f2b2c36c9a6f80a8e2f57b6d7aa11f9db1226ea1d9ac1c8e97165f145140cba10dd410a6ae0ed1cfca17541742153f2b6223bc29b1e27a44c6882f40a9f2acd5127303e16bb39c0a593d3fa\" moneyname=\"����\">	</li>	 	<div class=\"content-top1\" id=\"node_1445\" > 		<a class=\"xzlj\" name=\"detail\" id=\"detail_1445\" href=\"http://ads.miidi.net/appscore4//cpapagedetail.bin?productId=18770&unionId=8000&imei=869421015119926&channelId=0&adFrom=3&downAppid=1445\" >   		<div class=\"tubiao\"><img src=\"http://www.miidi.net/appsoft/cpa/imgs/201405/icon_com.jiami.mahjong_20140530174023.png\" width=\"48\" height=\"48\" /></div>			 		<div class=\"jianjie\">  			<p><b id=\"app_1445\" >�����齫</b>  			<font class=\"jieshao_reg\"	id=\"act_1445\" name=\"redirect\">ע����40����</font></p>  			<span>�����齫�����ǲ�һ�����齫Ŷ��</span>  <br><font color=\"red\">�״����أ�ע������3���ӻ�ȡ����</font>		</div>		</a>   	 </div>						<li cpaid=\"1982\"  			runAppScore=\"yes\"  			showScore=\"yes\"  		  alreadyInstalled=\"no\"   		packagename=\"com.ms.ezqxXYH\" apkname=\"��ս����\"  installscore=\"40\"   		openscore=\"5\" openurl=\"mopen://c32e2281cf0e1aad97c39fc7047c4f5b0ab0bdac9b98fd8f770e653d6967219d14342d1c3b82c09b506e1c2e8d7ef43da91cf072d354a90bcf85328fa3110ec609f1d7b5ad58f4d110393e4ca5709159d9f9595de75a8131\" moneyname=\"����\">	</li>	 	<div class=\"content-top1\" id=\"node_1982\" > 		<a class=\"xzlj\" name=\"detail\" id=\"detail_1982\" href=\"http://ads.miidi.net/appscore4//cpapagedetail.bin?productId=18770&unionId=8000&imei=869421015119926&channelId=0&adFrom=3&downAppid=1982\" >   		<div class=\"tubiao\"><img src=\"http://www.miidi.net/appsoft/cpa/imgs/201408/icon_com.ms.ezqxXYH_20140827111242.png\" width=\"48\" height=\"48\" /></div>			 		<div class=\"jianjie\">  			<p><b id=\"app_1982\" >��ս����</b>  			<font class=\"jieshao-b1\"	id=\"act_1982\" name=\"redirect\">��װ��40����</font></p>  			<span>һ�������Ϸ</span>  <br><font color=\"red\">�״����أ�����</font>		</div>		</a>   	 </div>				</ul></div><div class=\"content1\">  <div class=\"shiyan_a\">   С����ǻ���ϲ���� �����Ƽ��޻��ֽ��� ��   </div>              <div class=\"content-top1\" >                <div class=\"tubiao\"><img src=\"http://www.miidi.net/appsoft/cpa/imgs/201405/icon_com.youyuan.market_20140506152152.png\" width=\"48\" height=\"48\" /></div>                <div class=\"jianjie\">                        <p><b >ͬ��ҹԼ��</b>                        <a class=\"xzlj\" name=\"detail\" href=\"mcpa://5c380c3af62a007b14c98b9f5f260d6ef1fd86b44a6f3c56abfc7fa5fae55604669d55989504ae0542eaf68caf9e673eda284b312db489a75d9aca027088d982947028b3add58e696d77af8bb3fd50cb1a5009c9d660ac510a9f2acd5127303e16bb39c0a593d3fa\" > <font class=\"jieshao-b1\" >��Ѱ�װ</font></p> </a>                        <span>ͬ�ǿ�����Ů������Լ��</span>                </div>         </div>            <div class=\"content-top1\" >                <div class=\"tubiao\"><img src=\"http://www.miidi.net/appsoft/cpa/imgs/201402/icon_com.youyuan.yhb_20140227175936.png\" width=\"48\" height=\"48\" /></div>                <div class=\"jianjie\">                        <p><b >Լ���</b>                        <a class=\"xzlj\" name=\"detail\" href=\"mcpa://846a2fae04c888e304b657475e070a08ce110fa31cc2189cabfc7fa5fae55604669d55989504ae0542eaf68caf9e673eda284b312db489a75d9aca027088d982947028b3add58e696d77af8bb3fd50cb1a5009c9d660ac510a9f2acd5127303e16bb39c0a593d3fa\" > <font class=\"jieshao-b1\" >��Ѱ�װ</font></p> </a>                        <span>��ʱ�����������ߵ�Ե�� </span>                </div>         </div>            <div class=\"content-top1\" >                <div class=\"tubiao\"><img src=\"http://www.miidi.net/appsoft/cpa/imgs/201406/icon_com.jiatang.ztt_20140627184210.png\" width=\"48\" height=\"48\" /></div>                <div class=\"jianjie\">                        <p><b >ȫ������</b>                        <a class=\"xzlj\" name=\"detail\" href=\"mcpa://fd066f433c0ed7776c560d53e3f636ac4026e5480f245444b7da61292e87e33f2d0f6460ad48d1077b5bec9957f4870495f3ac1f94b60fb8022a5577f749c431a6c73ba9d746002f5eb7a7ec80baba9ea2514f0394af4f7944dac8d6020e04153d50e4b09ea463d9\" > <font class=\"jieshao-b1\" >��Ѱ�װ</font></p> </a>                        <span>�����Ľ����Ϳ�����Ϸ�����Ա����Ը���Ҫ</span>                </div>         </div>            <div class=\"content-top1\" >                <div class=\"tubiao\"><img src=\"http://www.miidi.net/appsoft/cpa/imgs/201405/icon_com.feiliu.gamecenter_20140522195718.png\" width=\"48\" height=\"48\" /></div>                <div class=\"jianjie\">                        <p><b >������Ϸ����</b>                        <a class=\"xzlj\" name=\"detail\" href=\"mcpa://788157f5986bda672816bb6f2e5dd10194696a30b15f1d4eb7da61292e87e33f2d0f6460ad48d1077b5bec9957f4870495f3ac1f94b60fb8022a5577f749c431a6c73ba9d746002f5eb7a7ec80baba9ea2514f0394af4f7944dac8d6020e04153d50e4b09ea463d9\" > <font class=\"jieshao-b1\" >��Ѱ�װ</font></p> </a>                        <span>���ҵ���Ϸ����һ��,��������Ѱ���ѹ���</span>                </div>         </div>    <div class=\"fankui\"><a class=\"jfsm\" >�׵Ϲ��</a><a class=\"fklya\" href=\"./jifengmemo.html\">����˵��</a><a class=\"fkly\" href=\"./feedback.jsp?imei=869421015119926&productId=18770\">��������</a></div></body></html>";
		parseAdList(str);
		
		
	
	
	}
	
	//������ʽ����������url
	public static String parseDetailUrl(String html)
	{
		String url = "";
		try {
			Pattern pt = Pattern.compile("adview.php?(.*?)\">");
			Matcher m = pt.matcher(html);
			if(m.find()){
				url = "http://a.dianjoy.com/dev/api/adlist/adview.php"  +  m.group(1);
			//	System.out.println(m.group(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
	
	//��ȡ����ҳ���е�downAct
	public static String parseDownAppidEncode(String str){
		String result = "";
		try {
			Pattern pt = Pattern.compile("mcpa://?(.*?)\"");
			Matcher m = pt.matcher(str);
			if(m.find()){
				result = m.group(0);
				if(result.lastIndexOf("\"") > 0)
					result = result.substring(0, result.length()-1);
				result = result.replaceAll("mcpa://", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//�������ص�ַ
	public static String parseDownUrl(String str){
		String result = "";
		try {
			Pattern pt = Pattern.compile("http://ads.miidi.net/appscore4//miidiadcore.bin?(.*?)\"");
			Matcher m = pt.matcher(str);
			if(m.find()){
				result = m.group(0);
				if(result.lastIndexOf("\"") > 0)
					result = result.substring(0, result.length()-1);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//��������б�-WIFI
	public static List<MDAd> parseAdList(String html)
	{
		List<MDAd> list = new ArrayList<MDAd>();
		try {
			//�������
			Pattern pt = Pattern.compile("packagename=\"?(.*?)\"");
			Matcher m = pt.matcher(html);
			while(m.find()){
				MDAd ad = new MDAd();
				ad.setAdPkg(m.group(1));
				list.add(ad);
			}
			
			pt = Pattern.compile("http://ads.miidi.net/appscore4//cpapagedetail.bin?\"?(.*?)\"");
			m = pt.matcher(html);
			int j = 0;
			while(m.find()){
				MDAd ad = list.get(j);
				String url = m.group(0);
				if(url.lastIndexOf("\"") > 0)
					url = url.substring(0, url.length()-1);
				ad.setDetailUrl(url);
				
				j++;
			}
			
			pt =  Pattern.compile("cpaid=\"?(.*?)\"");
			m = pt.matcher(html);
			j = 0;
			while(m.find()){
				MDAd ad = list.get(j);
				ad.setCpaID(m.group(1));
				j++;
			}
			
			pt =  Pattern.compile("apkname=\"?(.*?)\"");
			m = pt.matcher(html);
			j = 0;
			while(m.find()){
				MDAd ad = list.get(j);
				ad.setAdName(m.group(0));
				j++;
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	
	}
	
	public static String parseIpAddr(String html,int which)
	{
		String ip = "";
		if(which == 1)
		{
			Pattern pt = Pattern.compile("<code>(.*?)</code>");
			//Pattern pt = Pattern.compile("<body>(.*?)<!");
			Matcher m = pt.matcher(html);
			if(m.find()){
				ip = m.group(1);
			}
		}
		if(which == 2)
		{
			ip = html.split(" ")[0];
		}
		
		return ip.trim();
	}
	
	
	
}
