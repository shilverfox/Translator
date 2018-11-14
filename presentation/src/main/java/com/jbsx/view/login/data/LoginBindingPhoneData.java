package com.jbsx.view.login.data;
/**
 * 绑定手机号数据
 *
 */
public class LoginBindingPhoneData {

	private String code;
	private String msg;
	private Result result;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public class Result{
		private String pin;
		 private String mobile;//电话号
		 private int flg;//标识(标识2 成功 3 验证失败4验证码过期)
		 private String msg;//消息
		 
		
		public int getFlg() {
			return flg;
		}
		public void setFlg(int flg) {
			this.flg = flg;
		}
		public String getPin() {
			return pin;
		}
		public void setPin(String pin) {
			this.pin = pin;
		}
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
}
