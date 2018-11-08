package com.translatmaster.view.login.data;

public class LoginEvent {
	
	public Action action;

	public enum Action {
		CLOSE,
		FAIL,
		SUCCESS
		
	}
	
	public LoginEvent(Action action) {
		this.action = action;
	}

	public static class AccountSafeInfo{
		public boolean isSuc;
	}


}
