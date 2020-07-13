package com.qa.ims.persistence.domain;

/*Customer my_customer = new Customer("tom", "123", "zzz");
System.out.println(my_customer.toString());*/

public class Customer {

	private Long id;
	private String name;
	private String address;
	private String postcode;

	public Customer(String name, String address, String postcode) {
		this.name = name;
		this.address = address;
		this.postcode = postcode;
	}

	public Customer(Long id, String name, String address, String postcode) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.postcode = postcode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String toString() {
		return "id:" + id + " name:" + name + " address: " + address + " postcode: " + postcode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((postcode == null) ? 0 : postcode.hashCode());
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name)) {
			return false;
		}
		
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id)) {
			return false;
		}

		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address)) {
			return false;
		}

		if (postcode == null) {
			if (other.postcode != null)
				return false;
		} else if (!postcode.equals(other.postcode)) {
			return false;
		}
		
		return true;
	}

}
