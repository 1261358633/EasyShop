package com.itheima.store.web.servlet;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.itheima.store.domain.PageModel;
import com.itheima.store.domain.Product;
import com.itheima.store.service.ProductService;
import com.itheima.store.service.impl.ProductServiceImp;
import com.itheima.store.utils.DateJsonValueProcessor;
import com.itheima.store.utils.MyBeanUtils;
import com.itheima.store.utils.UUIDUtils;
import com.itheima.store.web.base.BaseServlet;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class AdminProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String  findAllPros(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/json;charset=utf-8");
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		ProductService productServiceImp = new ProductServiceImp();
		PageModel pm = productServiceImp.findProPageRows(page,rows);
		HashMap<Object,Object> map = new HashMap<Object,Object>();
		map.put("total", pm.getTotalRecords());
		List<Product> list = pm.getList();
		map.put("rows", list);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor());
		String jsonStr=JSONObject.fromObject(map, jsonConfig).toString();
		response.getWriter().print(jsonStr);
		return null;
	}
	public String  addPro(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//接收数据
		//String pname = request.getParameter("pname");
		/*ServletInputStream in = request.getInputStream();
		int i = -1;
		while((i=in.read())!=-1){
			System.out.print((char)i);
		}
		return null;*/
//		*_创建Map
		Map map = new HashMap();
//		*_创建Product
		Product product = new Product();
		try {
//		*_调用commons-fileupload中的API,对request对象进行解析,
			DiskFileItemFactory fac = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(fac);
			List<FileItem> list = upload.parseRequest(request);
//		  获取到集合<FileItem> FileItem:每对分割线中的内容
//		*_遍历集合
		for (FileItem fileItem : list) {
			
		}
//		*_判断FileItem是普通项, 获取到表单中name属性值,用户录入数据存入MAP
//		        {“pname”:”键盘”,”market_price”:1000}
//		*_判断FileItem是上传项
//		        获取到products/3,绝对路径, 创建文件 xxx.bmp ,将二进制数据写入文件
//		*_利用BeanUtils将MAP中的数据填充到product对象上
//		*_调用servcie,dao将product上数据保存在DB中 
//		*_向客户端响应addProOk
			for (FileItem fileItem : list) {
				if(fileItem.isFormField()){//判断是普通项还是上传相
//				fileItem.getFieldName()// 获取 panme
//				fileItem.getName() //普通项为null  图片为 图片名
//				fileItem.getString("utf-8")  值	解决中文乱码问题
					map.put(fileItem.getFieldName(), fileItem.getString("utf-8"));
					
				}else{
					String realPath = getServletContext().getRealPath("/products/3");
					File file = new File(realPath, fileItem.getName());
					if(file.exists()){
						file.createNewFile();
					}
//					InputStream in = fileItem.getInputStream();
					fileItem.write(file);
					map.put("pimage", "products/3/"+fileItem.getName());
				}
				
			}
			MyBeanUtils.populate(product, map);
			product.setPid(UUIDUtils.getId());
			product.setPdate(new Date());
			product.setPflag(0);
			ProductService productService = new ProductServiceImp();
			productService.addPro(product);
			response.getWriter().print("addProOk");
		} catch (Exception e) {
			e.printStackTrace();
	
	}
		return null;

	}
}