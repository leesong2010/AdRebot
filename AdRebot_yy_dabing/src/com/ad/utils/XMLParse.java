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
	
	
	//获取广告信息
	public static List<AdItem> getAds(InputStream inputStream) throws Exception
    {
        List<AdItem> list=new ArrayList<AdItem>();
        //获取工厂对象，以及通过DOM工厂对象获取DOMBuilder对象
        DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
        DocumentBuilder builder=factory.newDocumentBuilder();
        //解析XML输入流，得到Document对象，表示一个XML文档
        Document document=builder.parse(inputStream);
        //获得文档中的次以及节点，persons
        Element element=document.getDocumentElement();
        // 获取Element下一级的person节点集合，以NodeList的形式存放。
        NodeList adNodes=element.getElementsByTagName("item");
        for(int i=0;i<adNodes.getLength();i++)
        {
            //循环获取索引为i的item节点
            Element adElement=(Element) adNodes.item(i);
            AdItem adItem=new AdItem();
            
            //获取索引i的item节点下的子节点集合
            NodeList itemNodes=adElement.getChildNodes();
            for(int j=0;j<itemNodes.getLength();j++)
            {
                //循环遍历每个person下的子节点，如果判断节点类型是ELEMENT_NODE，就可以依据节点名称给予解析
                if(itemNodes.item(j).getNodeType()==Node.ELEMENT_NODE)
                {
                    if("adName".equals(itemNodes.item(j).getNodeName()))
                    {
                        //因为文本也是一个文本节点，
                        //所以这里读取到name节点的时候，
                        //通过getFirstChild()可以直接获得name节点的下的第一个节点，就是name节点后的文本节点
                        //取其value值，就是文本的内容
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
            //把解析的person对象加入的list集合中
            list.add(adItem);
        }
        return list;
    }
	

}
