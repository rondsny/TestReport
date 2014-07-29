package ron.bus;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import ron.bean.BeanVersion;


public class BusPic {
	
	
	/*
	 * 生产图片的一个例子
	 * */
	public String getSample(HttpSession session,List<String> list) throws Exception{
		
		String title="The Main title";

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(212, "Classes", "JDK 1.0");
        dataset.addValue(504, "Classes", "JDK 1.1");
        dataset.addValue(250, "Classes2", "JDK 1.0");
        dataset.addValue(604, "Classes2", "JDK 1.1");

		JFreeChart chart = ChartFactory.createLineChart(title, "X", "Y", dataset);

//		XYPlot xyplot = (XYPlot) chart.getPlot();
//		xyplot.getDomainAxis().setLabelFont(new Font("微软雅黑", Font.PLAIN, 14)); // X轴 
//		xyplot.getRangeAxis().setLabelFont(new Font("微软雅黑", Font.PLAIN, 14)); // Y轴  
//		chart.getLegend().setItemFont(new Font("微软雅黑", Font.PLAIN, 14)); // 底部 
//		xyplot.setBackgroundPaint(Color.white);
//		xyplot.setDomainGridlinePaint(Color.pink);
//		xyplot.setRangeGridlinePaint(Color.pink);

		//设置主标题 
		chart.setTitle(new TextTitle(title, new Font("宋体", Font.ITALIC,15)));
		chart.setAntiAlias(true);

		//设置时间格式，同时也解决了乱码问题  
//		DateAxis dateaxis = (DateAxis) xyplot.getDomainAxis();
//		SimpleDateFormat sfd = new SimpleDateFormat("mm:ss");
//		dateaxis.setDateFormatOverride(sfd);
//		xyplot.setDomainAxis(dateaxis);
//
//		NumberAxis dateaxisY = (NumberAxis) xyplot.getRangeAxis();
//		DecimalFormat df = new DecimalFormat("######0.00"); 
//		dateaxisY.setNumberFormatOverride(df);
//		xyplot.setRangeAxis(dateaxisY);

		String filename = ServletUtilities.saveChartAsPNG(chart, 800, 400,null, session);
		String graphURL = "/DisplayChart?filename=" + filename;
		
		return graphURL;
	}
	
	

	/*
	 * map 为每个版本对应问题
	 * */
	public String getUrlByVersionMap(HttpSession session,Map<String,List<BeanVersion>> map,String outputPath) throws Exception{
		
		String title="The Main title";

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        Iterator iter = map.entrySet().iterator(); 
        while (iter.hasNext()) { 
            Map.Entry entry = (Map.Entry) iter.next(); 
            Object version = entry.getKey();
            List<BeanVersion> list = (List<BeanVersion>)entry.getValue();
            for(BeanVersion bean : list){
                dataset.addValue(bean.getOpen(), "open", version.toString());
                dataset.addValue(bean.getCurrentCount(), "current", version.toString());
                dataset.addValue(bean.getSumCount(), "sum", version.toString());
            }
        }

		JFreeChart chart = ChartFactory.createLineChart(title, "Bug Count", "Version", dataset);
		
		CategoryPlot plot=chart.getCategoryPlot(); 
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.BLUE);//纵坐标格线颜色
        plot.setDomainGridlinePaint(Color.BLACK);//横坐标格线颜色
        plot.setDomainGridlinesVisible(true);//显示横坐标格线
        plot.setRangeGridlinesVisible(true);//显示纵坐标格线
        
        LineAndShapeRenderer renderer = (LineAndShapeRenderer)plot.getRenderer();
        renderer.setItemLabelsVisible(true);//设置项标签显示
        renderer.setBaseItemLabelsVisible(true);//基本项标签显示
        renderer.setShapesFilled(Boolean.TRUE);//在数据点显示实心的小图标
        renderer.setShapesVisible(true);//设置显示小图标

		//设置主标题 
		chart.setTitle(new TextTitle(title, new Font("微软雅黑", Font.ITALIC,15)));
		chart.setAntiAlias(true);

		//保存为图片
		DateFormat format=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String name= "pic/"+format.format(new Date())+"t.png";
		String path=outputPath+name;
		
		FileOutputStream out = null;  
        File outFile = new File(path);  
        if (!outFile.getParentFile().exists()) {  
            outFile.getParentFile().mkdirs();  
        }  
        out = new FileOutputStream(path);
        ChartUtilities.writeChartAsPNG(out, chart, 800, 400);  
        out.flush();  
        if (out != null) {
        	out.close();
        }
		return name;
	}
	

	/*
	 * list 为所有版本对应问题
	 * */
	public String getUrlByVersionList(HttpSession session,List<BeanVersion> list,String outputPath) throws Exception{
		
		String title="";

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        for(BeanVersion bean : list){
            dataset.addValue(bean.getOpen(), "open", bean.getVersion());
            dataset.addValue(bean.getCurrentCount(), "current", bean.getVersion());
            dataset.addValue(bean.getSumCount(), "sum", bean.getVersion());
        }

		JFreeChart chart = ChartFactory.createLineChart(title,"Version", "Bug Count",  dataset);

		CategoryPlot plot=chart.getCategoryPlot(); 
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.BLUE);//纵坐标格线颜色
        plot.setDomainGridlinePaint(Color.BLACK);//横坐标格线颜色
        plot.setDomainGridlinesVisible(true);//显示横坐标格线
        plot.setRangeGridlinesVisible(true);//显示纵坐标格线
        
        LineAndShapeRenderer renderer = (LineAndShapeRenderer)plot.getRenderer();
        renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", new DecimalFormat("##")));
        renderer.setItemLabelsVisible(true);//设置项标签显示
        renderer.setBaseItemLabelsVisible(true);//基本项标签显示
        renderer.setShapesFilled(Boolean.TRUE);//在数据点显示实心的小图标
        renderer.setShapesVisible(true);//设置显示小图标

		//设置主标题 
		chart.setTitle(new TextTitle(title, new Font("宋体", Font.ITALIC,15)));
		chart.setAntiAlias(true);

		//保存为图片
		DateFormat format=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String name= "pic/"+format.format(new Date())+"t.png";
		String path=outputPath+name;
		
		FileOutputStream out = null;  
        File outFile = new File(path);  
        if (!outFile.getParentFile().exists()) {  
            outFile.getParentFile().mkdirs();  
        }  
        out = new FileOutputStream(path);
        ChartUtilities.writeChartAsPNG(out, chart, 800, 400);  
        out.flush();  
        if (out != null) {
        	out.close();
        }
		return name;
	}
	

	/*
	 * bug 分类
	 * map 为所有版本对应问题
	 * */
	public String getUrlByCatMap(HttpSession session,Map<String,Integer> map,String outputPath) throws Exception{
		
		String title="";

		DefaultPieDataset dataset = new DefaultPieDataset();
        
        Iterator iter = map.entrySet().iterator(); 
        while (iter.hasNext()) { 
            Map.Entry entry = (Map.Entry) iter.next(); 
            dataset.setValue(entry.getKey().toString(),(Integer)entry.getValue());
        }

        //创建主题样式  
        StandardChartTheme standardChartTheme=new StandardChartTheme("CN");  
        //设置标题字体  
        standardChartTheme.setExtraLargeFont(new Font("宋体",Font.BOLD,20));  
        //设置图例的字体  
        standardChartTheme.setRegularFont(new Font("宋体",Font.PLAIN,15));  
        //设置轴向的字体  
        standardChartTheme.setLargeFont(new Font("宋体",Font.PLAIN,15));  
        //应用主题样式  
        ChartFactory.setChartTheme(standardChartTheme); 
		JFreeChart chart = ChartFactory.createPieChart(title, dataset, true, true, false);  

		//设置主标题 
		//chart.setTitle(new TextTitle(title, new Font("宋体", Font.ITALIC,15)));
		
		PiePlot pieplot = (PiePlot) chart.getPlot();

		pieplot.setBackgroundPaint(Color.WHITE);
		pieplot.setLabelFont(new Font("宋体", 0, 11));  
		//设置饼图是圆的（true），还是椭圆的（false）；默认为true  
		pieplot.setCircular(true);  
		StandardPieSectionLabelGenerator standarPieIG = new StandardPieSectionLabelGenerator("{0} {1}个, {2}", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance());  
		pieplot.setLabelGenerator(standarPieIG);  
		  
		//没有数据的时候显示的内容  
		pieplot.setNoDataMessage("no data");  
		pieplot.setLabelGap(0.02D);  

		//保存为图片
		DateFormat format=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String name= "pic/cat_"+format.format(new Date())+"_t.png";
		String path=outputPath+name;
		
		FileOutputStream out = null;  
        File outFile = new File(path);  
        if (!outFile.getParentFile().exists()) {  
            outFile.getParentFile().mkdirs();  
        }  
        out = new FileOutputStream(path);
        ChartUtilities.writeChartAsPNG(out, chart, 400, 400);  
        out.flush();  
        if (out != null) {
        	out.close();
        }
		return name;
	}
}
