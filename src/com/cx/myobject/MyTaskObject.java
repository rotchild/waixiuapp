package com.cx.myobject;

import java.io.Serializable;

public class MyTaskObject implements Serializable {
	private String case_No,case_id,car_No,brand_name,is_target,target_No,isvip,parters_name,
	parter_manager,parter_mobile,dingsuner_name,dingsuner_mobile,yard_time,waixiu_time,
	waixiu_depart,waixu_peijian,peijian_price,waixiu_price,about_price;
	public MyTaskObject(String case_No,String case_id, String car_No, String brand_name,
			String is_target, String target_No,String isvip,
			String parters_name, String parter_manager, String parter_mobile,
			String dingsuner_name, String dingsuner_mobile,String yard_time,
			String waixiu_time,String waixiu_depart,String waixu_peijian,String peijian_price,
			String waixiu_price,String about_price
			) {
		super();
		this.case_No = case_No;
		this.case_id=case_id;
		this.car_No = car_No;
		this.brand_name = brand_name;
		this.is_target = is_target;
		this.target_No = target_No;
		this.isvip = isvip;
	//	this.parter_id = parter_id;
		this.parters_name = parters_name;
		this.parter_manager = parter_manager;
		this.parter_mobile = parter_mobile;
		this.dingsuner_name=dingsuner_name;
		this.dingsuner_mobile=dingsuner_mobile;
		//this.delivery_name = delivery_name;
		//this.delivery_mobile = delivery_mobile;
		//this.loss_price = loss_price;
		//this.case_state=case_state;
		this.yard_time = yard_time;
		//this.repair_state = repair_state;
		//this.audit_state = audit_state;
		//this.loss_state = loss_state;
		this.waixiu_time=waixiu_time;
		this.waixiu_depart=waixiu_depart;
		this.waixu_peijian=waixu_peijian;
		this.peijian_price=peijian_price;
		this.waixiu_price=waixiu_price;
		this.about_price=about_price;
		
	}
	public String getCase_No() {
		return case_No;
	}
	public void setCase_No(String case_No) {
		this.case_No = case_No;
	}
	
	
	
	public String getCase_id() {
		return case_id;
	}
	public void setCase_id(String case_id) {
		this.case_id = case_id;
	}
	public String getCar_No() {
		return car_No;
	}
	public void setCar_No(String car_No) {
		this.car_No = car_No;
	}
	public String getBrand_name() {
		return brand_name;
	}
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}
	public String getIs_target() {
		return is_target;
	}
	public void setIs_target(String is_target) {
		this.is_target = is_target;
	}
	public String getTarget_No() {
		return target_No;
	}
	public void setTarget_No(String target_No) {
		this.target_No = target_No;
	}


	public String getParters_name() {
		return parters_name;
	}
	public void setParters_name(String parters_name) {
		this.parters_name = parters_name;
	}
	public String getParter_manager() {
		return parter_manager;
	}
	public void setParter_manager(String parter_manager) {
		this.parter_manager = parter_manager;
	}
	public String getParter_mobile() {
		return parter_mobile;
	}
	public void setParter_mobile(String parter_mobile) {
		this.parter_mobile = parter_mobile;
	}


	public String getYard_time() {
		return yard_time;
	}
	public void setYard_time(String yard_time) {
		this.yard_time = yard_time;
	}
	public String getDingsuner_name() {
		return dingsuner_name;
	}
	public void setDingsuner_name(String dingsuner_name) {
		this.dingsuner_name = dingsuner_name;
	}
	public String getDingsuner_mobile() {
		return dingsuner_mobile;
	}
	public void setDingsuner_mobile(String dingsuner_mobile) {
		this.dingsuner_mobile = dingsuner_mobile;
	}
	public String getWaixiu_time() {
		return waixiu_time;
	}
	public void setWaixiu_time(String waixiu_time) {
		this.waixiu_time = waixiu_time;
	}
	public String getWaixiu_depart() {
		return waixiu_depart;
	}
	public void setWaixiu_depart(String waixiu_depart) {
		this.waixiu_depart = waixiu_depart;
	}
	public String getWaixu_peijian() {
		return waixu_peijian;
	}
	public void setWaixu_peijian(String waixu_peijian) {
		this.waixu_peijian = waixu_peijian;
	}
	public String getPeijian_price() {
		return peijian_price;
	}
	public void setPeijian_price(String peijian_price) {
		this.peijian_price = peijian_price;
	}
	public String getWaixiu_price() {
		return waixiu_price;
	}
	public void setWaixiu_price(String waixiu_price) {
		this.waixiu_price = waixiu_price;
	}
	public String getAbout_price() {
		return about_price;
	}
	public void setAbout_price(String about_price) {
		this.about_price = about_price;
	}
	public String getIsvip() {
		return isvip;
	}
	public void setIsvip(String isvip) {
		this.isvip = isvip;
	}



}
