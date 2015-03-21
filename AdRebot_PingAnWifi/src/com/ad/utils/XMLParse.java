package com.ad.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.os.Environment;

import com.ad.vo.AdItem;

public class XMLParse {
	
	
	//��ȡ�����Ϣ
	public static List<AdItem> getAds(InputStream inputStream) throws Exception
    {
        List<AdItem> list=new ArrayList<AdItem>();
        //��ȡ���������Լ�ͨ��DOM���������ȡDOMBuilder����
        DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
        DocumentBuilder builder=factory.newDocumentBuilder();
        //����XML���������õ�Document���󣬱�ʾһ��XML�ĵ�
        Document document=builder.parse(inputStream);
        //����ĵ��еĴ��Լ��ڵ㣬persons
        Element element=document.getDocumentElement();
        // ��ȡElement��һ����person�ڵ㼯�ϣ���NodeList����ʽ��š�
        NodeList adNodes=element.getElementsByTagName("item");
        for(int i=0;i<adNodes.getLength();i++)
        {
            //ѭ����ȡ����Ϊi��item�ڵ�
            Element adElement=(Element) adNodes.item(i);
            AdItem adItem=new AdItem();
            
            //��ȡ����i��item�ڵ��µ��ӽڵ㼯��
            NodeList itemNodes=adElement.getChildNodes();
            for(int j=0;j<itemNodes.getLength();j++)
            {
                //ѭ������ÿ��person�µ��ӽڵ㣬����жϽڵ�������ELEMENT_NODE���Ϳ������ݽڵ����Ƹ������
                if(itemNodes.item(j).getNodeType()==Node.ELEMENT_NODE)
                {
                    if("adName".equals(itemNodes.item(j).getNodeName()))
                    {
                        //��Ϊ�ı�Ҳ��һ���ı��ڵ㣬
                        //���������ȡ��name�ڵ��ʱ��
                        //ͨ��getFirstChild()����ֱ�ӻ��name�ڵ���µĵ�һ���ڵ㣬����name�ڵ����ı��ڵ�
                        //ȡ��valueֵ�������ı�������
                        adItem.setAdName(itemNodes.item(j).getFirstChild().getNodeValue()); 
                    }
                    else if("adPkg".equals(itemNodes.item(j).getNodeName()))
                    {
                    		adItem.setAdPkg(itemNodes.item(j).getFirstChild().getNodeValue()); 
                    }
                    else if("delay".equals(itemNodes.item(j).getNodeName()))
                    {
                    		adItem.setDelay(Integer.parseInt(itemNodes.item(j).getFirstChild().getNodeValue())); 
                    }
                }
            }
            //�ѽ�����person��������list������
            list.add(adItem);
        }
        return list;
    }
	

}
