package com.xidian.model.product;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 产品类型实体类
 * @author gwh
 *
 */

public class Product {
	private final IntegerProperty id;
	private final StringProperty producttype;
	private final StringProperty oldproducttype;

	public Product() {
		this.id = new SimpleIntegerProperty();
		this.producttype = new SimpleStringProperty();
		this.oldproducttype = new SimpleStringProperty();
	}

	public int getId() {
		return id.get();
	}
	public IntegerProperty idProperty(){
		return id;
	}
	public void setId(int id) {
		this.id.set(id);
	}

	public String getProducttype() {
		return producttype.get();
	}
	public StringProperty producttypeProperty(){
		return producttype;
	}
	public void setProducttype(String producttype) {
		this.producttype.set(producttype);
	}

	public String getOldproducttype() {
		return oldproducttype.get();
	}
	public StringProperty oldproducttypeProperty(){
		return oldproducttype;
	}
	public void setOldproducttype(String oldproducttype) {
		this.oldproducttype.set(oldproducttype);
	}

}
