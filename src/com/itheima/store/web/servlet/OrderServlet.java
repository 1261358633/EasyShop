package com.itheima.store.web.servlet;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.store.domain.Cart;
import com.itheima.store.domain.CartItem;
import com.itheima.store.domain.Orderitem;
import com.itheima.store.domain.Orders;
import com.itheima.store.domain.PageModel;
import com.itheima.store.domain.User;
import com.itheima.store.service.OrderService;
import com.itheima.store.service.impl.OrderServiceImp;
import com.itheima.store.utils.PaymentUtil;
import com.itheima.store.utils.UUIDUtils;
import com.itheima.store.web.base.BaseServlet;

public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	public String saveOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User loginUser = (User) request.getSession().getAttribute("loginUser");
		if(null == loginUser){
			request.setAttribute("msg", "请先登录!");
			return "/jsp/info.jsp";
		}
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		Orders orders = new Orders();
		String oid = UUIDUtils.getId();
		orders.setOid(oid);
		orders.setState(1);//未付款
		Date date = new Date(System.currentTimeMillis());
		orders.setOrdertime(date);
		//设置总金额
		orders.setTotal(cart.getTotal());
		//设置所属用户
		orders.setUser(loginUser);
		//设置订单项集合
		for (CartItem cartItem : cart.getCardItems()) {
			//将购物项转为订单项
			Orderitem orderitem = new Orderitem();
			orderitem.setItemid(UUIDUtils.getId());
			orderitem.setQuantity(cartItem.getCount());
			orderitem.setTotal(cartItem.getSubtotal());
			orderitem.setProduct(cartItem.getProduct());
			orderitem.setOrders(orders);
			
			orders.getList().add(orderitem);
			
			
		}
		//调用业务层,保存到仓库
		OrderService orderService =new OrderServiceImp();
		orderService.saveOrder(orders);
		
		//清空购物车
		cart.clearCart();
		//页面跳转
		request.setAttribute("orders", orders);
		return "/jsp/order_info.jsp";
		
		
	}
	
	
	
	public String findOrderByOid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String oid = request.getParameter("oid");
		OrderService orderService =new OrderServiceImp();
		Orders orders = orderService.findOrderByOid(oid);
		List<Orderitem> list = orders.getList();
		orders.setList(list);
		request.setAttribute("orders", orders);
		return "/jsp/order_info.jsp";
		
	}
	
	public String findMyOrderWithPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = (User) request.getSession().getAttribute("loginUser");
		if(null == user){
			request.setAttribute("msg", "请先登录!");
			return "/jsp/info.jsp";
		}
		String num1 = request.getParameter("num");
		int num = Integer.parseInt(num1);
		OrderService orderService =new OrderServiceImp();
		PageModel pm =  orderService.findMyOrderWithPage(num,user);
		request.setAttribute("page", pm);
		return "/jsp/order_list.jsp";
		
	}
	
	public String payOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String oid = request.getParameter("oid");
		String address = request.getParameter("address");
		String name = request.getParameter("name");
		String telephone = request.getParameter("telephone");
		String pd_FrpId = request.getParameter("pd_FrpId");
		//获取金额
		OrderService orderService = new OrderServiceImp();
		Orders order = orderService.findOrderByOid(oid);
		
		order.setAddress(address);
		order.setName(name);
		order.setTelephone(telephone);
		order.setOrdertime(new Date(System.currentTimeMillis()));
		orderService.updateOrder(order);
		
		// 把付款所需要的参数准备好:
				String p0_Cmd = "Buy";
				//商户编号
				String p1_MerId = "10001126856";
				//订单编号
				String p2_Order = oid;
				//金额
				String p3_Amt = "0.01";
				String p4_Cur = "CNY";
				String p5_Pid = "";
				String p6_Pcat = "";
				String p7_Pdesc = "";
				//接受响应参数的Servlet
				String p8_Url = "http://localhost:8080/store_v4.0/OrderServlet?method=callBack";
				String p9_SAF = "";
				String pa_MP = "";
				String pr_NeedResponse = "1";
				//公司的秘钥
				String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";
					
				//调用易宝的加密算法,对所有数据进行加密,返回电子签名
				String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);
						
				StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
				sb.append("p0_Cmd=").append(p0_Cmd).append("&");
				sb.append("p1_MerId=").append(p1_MerId).append("&");
				sb.append("p2_Order=").append(p2_Order).append("&");
				sb.append("p3_Amt=").append(p3_Amt).append("&");
				sb.append("p4_Cur=").append(p4_Cur).append("&");
				sb.append("p5_Pid=").append(p5_Pid).append("&");
				sb.append("p6_Pcat=").append(p6_Pcat).append("&");
				sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
				sb.append("p8_Url=").append(p8_Url).append("&");
				sb.append("p9_SAF=").append(p9_SAF).append("&");
				sb.append("pa_MP=").append(pa_MP).append("&");
				sb.append("pd_FrpId=").append(pd_FrpId).append("&");
				sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
				sb.append("hmac=").append(hmac);

				System.out.println(sb.toString());
				// 使用重定向：
				response.sendRedirect(sb.toString());

		
		return null;
		
		
		
	}
	
	public String callBackr(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur = request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		String r9_BType = request.getParameter("r9_BType");
		String rb_BankId = request.getParameter("rb_BankId");
		String ro_BankOrderId = request.getParameter("ro_BankOrderId");
		String rp_PayDate = request.getParameter("rp_PayDate");
		String rq_CardNo = request.getParameter("rq_CardNo");
		String ru_Trxtime = request.getParameter("ru_Trxtime");

		// hmac
		String hmac = request.getParameter("hmac");
		// 利用本地密钥和加密算法 加密数据
		String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";
		boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
				r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
				r8_MP, r9_BType, keyValue);
		if (isValid) {
			// 有效
			if (r9_BType.equals("1")) {
				// 浏览器重定向
				response.setContentType("text/html;charset=utf-8");
				//response.getWriter().println("支付成功！订单号：" + r6_Order + "金额：" + r3_Amt);
				OrderService orderService = new OrderServiceImp();
				Orders order = orderService.findOrderByOid(r6_Order);
				order.setState(2);
				orderService.updateOrder(order);
				request.setAttribute("msg", "支付成功！订单号：" + r6_Order + "金额：" + r3_Amt);
				return "/jsp/info.jsp";	
			} 

		} else {
			//throw new RuntimeException("数据被篡改！");
			request.setAttribute("msg", "数据被篡改！");
			return "/jsp/info.jsp";
			}
		return null;
	}
}
