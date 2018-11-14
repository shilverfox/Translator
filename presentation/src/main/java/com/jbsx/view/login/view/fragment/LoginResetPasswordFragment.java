package com.jbsx.view.login.view.fragment;

import com.app.data.net.repository.TaskManager;
import com.app.domain.net.interactor.LoginViewUserCase;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.view.login.view.presenter.LoginResetPasswordPresenter;
import com.jbsx.view.login.view.presenter.LoginSimpleInputSmsPresenter;

public class LoginResetPasswordFragment extends LoginSimpleInputSmsFragment {
    @Override
    public void createPresenter() {
        LoginViewUserCase userCase = new LoginViewUserCase(TaskManager.getTaskManager(),
                MainApplicationLike.getUiThread());
        mPresenter = new LoginResetPasswordPresenter(this, userCase);
    }

    @Override
    public String getSuccessToast() {
        return "恭喜您，密码重置成功！";
    }

    @Override
    public String getSubmitButtonName() {
        return "重置密码";
    }

    @Override
    public boolean needShowUserAgreement() {
        return false;
    }
}
