package com.udacity.newsfromhell.URLRes;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.interfaces.ECPublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import com.udacity.newsfromhell.URLRes.bean.News;

/**
 * Created by 佳伟 on 2017/1/5 0005.
 */

public class ResUtils {

    /**
     * 获得网页的源码
     *
     * @param path 网页http路径
     * @return 源码 null if exception occured
     */
    public static List<News> getRes(final String path) throws Exception {
        //String result = null;
        List<News> newses = null;
        URL url = new URL(path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //超时和请求方式
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        //200
        int code = connection.getResponseCode();
        if (code == 200) {
            InputStream is = connection.getInputStream();
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            int len = -1;
//            byte[] buffer = new byte[1024];
//            while ((len = is.read(buffer)) != -1) {
//                baos.write(buffer, 0, len);
//            }
            // result = baos.toString("gb2312");
            newses = handleXml(is);
            // is.close();
//            baos.close();
        }
        return newses;
    }
    //从输入流获得xml元素
    private static List<News> handleXml(InputStream is) throws XmlPullParserException, IOException {
        InputStreamReader isr = new InputStreamReader(is, "gb2312");
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(isr);
//        char[] buf=new char[1024];
//        int len=-1;
//        StringBuffer line=new StringBuffer();
//        while ((len=isr.read(buf))!=-1){
//            line.append(new String(buf,0,len));
//        }
//         String result =line.toString();
//        Log.e("HAPPY",result);
        for (int i = 0; i < 33; i++) {
            parser.next();
        }
        int type = parser.getEventType();
//        <item>
//        <title><![CDATA[巴西越狱犯网络发文挑衅 警方矢言本周抓回]]></title>
//        <link><![CDATA[http://world.people.com.cn/n1/2017/0105/c1002-29000803.html]]></link>
//        <pubDate><![CDATA[2017-01-05T02:57:27.000Z]]></pubDate>
//        <source><![CDATA[人民网]]></source>
//        <author><![CDATA[人民网]]></author>
//        <description><![CDATA[
//                巴西监狱发生敌对帮派火拼暴动，造成56名囚犯死亡。巴西警方在当地时间1月3日展开大规模搜索，缉拿上百名趁乱越狱的罪犯。不过，据法国国际广播电台1月4日报道，越狱的犯人竟在社交媒体发文挑衅。   报道称，巴西当局指出，敌对毒枭发生血腥复仇火拼，亚马逊州两座监狱共184名囚犯越狱。根据最新统...<br />
//        <a href="http://world.people.com.cn/n1/2017/0105/c1002-29000803.html" target="_blank" style="font-size:13px">巴西越狱犯网络发文挑衅 警方矢言本周抓回</a><nobr><span style="padding-left:10px;font-size:12px;color:#666666">人民网</font></nobr><br>
//        <a href="http://international.dbw.cn/system/2017/01/05/057499597.shtml" target="_blank" style="font-size:13px">17小时暴动60囚犯丧生 巴西或改革监狱系统 </a><nobr><span style="padding-left:10px;font-size:12px;color:#666666">东北网</font></nobr><br>
//        <a href="http://www.chinanews.com/gj/2017/01-04/8114004.shtml" target="_blank" style="font-size:13px">菲南部武装劫狱事件5名逃犯被打死 </a><nobr><span style="padding-left:10px;font-size:12px;color:#666666">中国新闻网</font></nobr><br>
//        <a href="http://news.xinhuanet.com/world/2017-01/04/c_129432342.htm" target="_blank" style="font-size:13px">菲律宾一监狱遭武装分子袭击 上百囚犯趁乱越狱（..</a><nobr><span style="padding-left:10px;font-size:12px;color:#666666">新华网</font></nobr><br>
//        <a href="http://news.xinhuanet.com/world/2017-01/04/c_129432337.htm" target="_blank" style="font-size:13px">巴西监狱暴动致数十人死亡 大卡车搬运尸体（组图..</a><nobr><span style="padding-left:10px;font-size:12px;color:#666666">新华网</font></nobr><br>
//        <a href="http://www.chinanews.com/gj/2017/01-04/8113873.shtml" target="_blank" style="font-size:13px">菲律宾南部监狱158名囚犯逃脱 8人归案6人死亡 </a><nobr><span style="padding-left:10px;font-size:12px;color:#666666">中国新闻网</font></nobr><br>
//                - <a href="http://news.baidu.com/n?cmd=2&page=http%3A%2F%2Fworld.people.com.cn%2Fn1%2F2017%2F0105%2Fc1002-29000803.html&pn=1&clk=crel&cls=hqsy&where=focuspage&class=hqsy"><font color="#008000">100条相关&gt;&gt;</font></a><br>]]></description>
//        </item>
        List<News> newses = new ArrayList<>();
        News item = null;
        X:
        while (type != XmlPullParser.END_DOCUMENT) {
            // Log.i("HAPPY",parser.getName());
            switch (type) {
                case XmlPullParser.START_TAG:
                    if (parser.getName().equals("item")) {
                        item = new News();
                    }
                    if (parser.getName().equals("title")) {
                        item.title = parser.nextText();
                        System.out.print(item.title);
                    }
                    if (parser.getName().equals("link")) {
                        item.link = parser.nextText();
                        System.out.print(item.link);
                    }
                    if (parser.getName().equals("description")) {
                        String cdata = parser.nextText();
                        int start = cdata.indexOf("<img border=") + 21;
                        int end = cdata.indexOf(".jpg&fm=30\">") + 10;
                        if (start <= 20 || end <= 9) {
                            item.img_url="https://www.baidu.com/img/bd_logo1.png";
                            break;
                        }
                        item.img_url = cdata.substring(start, end);
                        System.out.print(item.img_url);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (parser.getName().equals("item")) {
                        newses.add(item);

                    }
                    break;
                case XmlPullParser.END_DOCUMENT:
                    break X;
            }
            type = parser.next();
        }
        is.close();
        return newses;
    }


}
