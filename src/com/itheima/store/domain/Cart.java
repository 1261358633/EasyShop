package com.itheima.store.domain;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 购物车
 * 
 * @author 令琦
 *
 */
public class Cart {

	/*
	 * #1. 定义一个购物项的集合的属性,用于维护所有的购物项,集合采用 Map<String,CardItem> map.key__>商品的id
	 * map.value__>商品信息
	 */
	private Map<String, CartItem> map = new LinkedHashMap<String, CartItem>();

	// 定义购物车中的总计,添加删除等操作不进行计算,直接获得时一并计算
	private double total;

	// 3 . 提供购物项集合属性,方便页面获取属性

	public Collection<CartItem> getCardItems() {
		return map.values();
	}

	// 4 .计算获得总计
	public double getTotal() {
		total = 0;
		for (Map.Entry<String, CartItem> entry : map.entrySet()) {
			CartItem carIterm = entry.getValue();
			total += carIterm.getSubtotal();
		}
		return total;
	}

	// 5 .1 添加商品到购物车
	public void addCart(Product product, int count) {		
		if (product == null) {
			return;
		}
		// 通过商品 id 来获得购物项
		CartItem cartItem = map.get(product.getPid());

		// 第一次购买
		if (cartItem == null) {
			cartItem = new CartItem();
			cartItem.setProduct(product);
			cartItem.setCount(count);
			// 将购物项添加到购物车
			map.put(product.getPid(), cartItem);
		} else {
			cartItem.setCount(cartItem.getCount() + count);
		}
	}

	// 5.2 从购物车中移除选项
	public void removeCart(String id) {
		// map 中移除元素
		CartItem cartItem = map.remove(id);
		// 将总计 - 移除购物的小计
		// total-=cartItem.getSubtotal();
	}

	// 5.3清空购物车
	public void clearCart() {
		// 将 map 集合清空
		map.clear();
		// 将总设置为0
		// total=0;
	}
}
