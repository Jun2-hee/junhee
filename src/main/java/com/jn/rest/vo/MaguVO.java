package com.jn.rest.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
@Setter
@Getter
public class MaguVO {
	private String name ;
	private int age;
	private List<String> hobby;
}
