package com.xmasq.core.common;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import lombok.Getter;
import lombok.Setter;

/**
 * 因为前端翻页是从1开始，Pageable是从0开始，所以通过该类进行转化
 * 
 * @author guoyu.huang
 */
@Setter
@Getter
public class PageableBuilder {

	/**
	 * 因为前端翻页是从1开始，Pageable是从0开始，所以通过该类进行转化
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public static Pageable build(int page, int size) {
		return PageRequest.of(--page, size);
	}
}
